package io.morin.togafexpairt.langchain4j;

import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.document.transformer.jsoup.HtmlToTextDocumentTransformer;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.rag.DefaultRetrievalAugmentor;
import dev.langchain4j.rag.content.injector.DefaultContentInjector;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.rag.query.router.LanguageModelQueryRouter;
import dev.langchain4j.rag.query.transformer.CompressingQueryTransformer;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.qdrant.QdrantEmbeddingStore;
import io.morin.togafexpairt.api.TogafExpairt;
import io.morin.togafexpairt.core.AdapterConfiguration;
import io.morin.togafexpairt.core.AdapterConfigurationFactory;
import io.morin.togafexpairt.core.TogafLibraryRegistry;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

/**
 * An adapter for the {@link AdapterConfigurationFactory} interface.
 */
@Slf4j
@RequiredArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdapterConfigurationFactoryProvider implements AdapterConfigurationFactory {

    @Override
    public AdapterConfiguration create(TogafExpairt.Settings settings) {
        val langchain4jSettings = Langchain4jSettings.builder().build();

        log.info("create the chat language model");
        val chatLanguageModel = ChatLanguageModelFactory.builder()
            .langchain4jSettings(langchain4jSettings)
            .build()
            .create();

        log.info("create the streaming chat language model");
        val streamingChatLanguageModel = StreamingChatLanguageModelFactory.builder()
            .langchain4jSettings(langchain4jSettings)
            .build()
            .create();

        log.info("create the embedding configuration");
        val embeddingModel = EmbeddingModelFactory.builder().langchain4jSettings(langchain4jSettings).build().create();

        val ingestorConfiguration = createIngestorConfiguration(langchain4jSettings, embeddingModel);

        log.info("create the query transformer");
        val queryTransformer = new CompressingQueryTransformer(chatLanguageModel);

        log.info("create the content injector");
        val contentInjectorBuilder = DefaultContentInjector.builder();
        /*
        Presently, the metadata keys are polluting the interpretation of the query content by the language model.
        TODO: An improvement of the prompt should be made to drive the language model to use the metadata content just as a reference.
        if (ingestorConfiguration.getContentRetriever().isPresent()) {
            contentInjectorBuilder.metadataKeysToInclude(
                Arrays.asList("document_group_name" ,"document_group_description")
            );
        }
        */
        val contentInjector = contentInjectorBuilder.build();

        log.info("create the retrieval augmentor");
        val retrievalAugmentorBuilder = DefaultRetrievalAugmentor.builder()
            .contentInjector(contentInjector)
            .queryTransformer(queryTransformer);

        if (ingestorConfiguration.getRetrieverToDescription().isPresent()) {
            log.info("create the query router");
            val queryRouter = LanguageModelQueryRouter.builder()
                .promptTemplate(
                    PromptTemplate.from(
                        """
                        You must help the selection of an embedding store.
                        You must select the most suitable store based on a use query.
                        You must only reply with a single number, i.e. the number of the matching embedding store.
                        You must return the number before the `:` character.
                        You must not provide explanations, notes or any other textual content.
                        If there is no match then reply `no match`.
                        **The embedding store**:
                        {{options}}
                        **User query**:
                        {{query}}
                        """
                    )
                )
                .chatLanguageModel(chatLanguageModel)
                .retrieverToDescription(ingestorConfiguration.getRetrieverToDescription().get())
                .fallbackStrategy(LanguageModelQueryRouter.FallbackStrategy.FAIL)
                .build();
            retrievalAugmentorBuilder.queryRouter(queryRouter);
        }

        if (ingestorConfiguration.getContentRetriever().isPresent()) {
            retrievalAugmentorBuilder.contentRetriever(ingestorConfiguration.getContentRetriever().get());
        }

        val retrievalAugmentor = retrievalAugmentorBuilder.build();

        log.info("create the langchain4jAssistant service");
        val langchain4jAssistant = AiServices.builder(Langchain4jAssistant.class)
            .retrievalAugmentor(retrievalAugmentor)
            .streamingChatLanguageModel(streamingChatLanguageModel)
            .chatLanguageModel(chatLanguageModel)
            .chatMemoryProvider(memoryId ->
                MessageWindowChatMemory.withMaxMessages(langchain4jSettings.getMaxMemoryMessages())
            )
            .build();

        log.info("create the chat");
        val chat = Langchain4jChat.builder().langchain4jAssistant(langchain4jAssistant).build();

        log.info("create the TOGAF library repository");
        val togafLibraryDocumentRepository = QdrantTogafLibraryRepository.builder()
            .ingestors(ingestorConfiguration.getIngestors())
            .langchain4jSettings(langchain4jSettings)
            .build();

        log.info("create and return the adapter configuration");
        return AdapterConfiguration.builder().chat(chat).togafLibraryRepository(togafLibraryDocumentRepository).build();
    }

    @Builder
    @AllArgsConstructor
    static class IngestorConfiguration {

        @Getter
        @Singular
        private Map<TogafLibraryRegistry, EmbeddingStoreIngestor> ingestors;

        private Map<ContentRetriever, String> retrieverToDescription;

        private ContentRetriever contentRetriever;

        Optional<Map<ContentRetriever, String>> getRetrieverToDescription() {
            return Optional.ofNullable(retrieverToDescription);
        }

        Optional<ContentRetriever> getContentRetriever() {
            return Optional.ofNullable(contentRetriever);
        }
    }

    private IngestorConfiguration createIngestorConfiguration(
        @NonNull Langchain4jSettings langchain4jSettings,
        @NonNull EmbeddingModel embeddingModel
    ) {
        val builder = IngestorConfiguration.builder();

        if (langchain4jSettings.isQueryRoutingEnabled()) {
            log.info("create the ingestor configuration with query routing");
            val retrieverToDescription = new HashMap<ContentRetriever, String>();
            for (TogafLibraryRegistry togafLibraryRegistry : TogafLibraryRegistry.values()) {
                log.info("{} - ensure the Qdrant collection exists", togafLibraryRegistry);
                QdrantUtils.ensureCollectionsExist(langchain4jSettings, togafLibraryRegistry);

                log.info("{} - create the ingestor", togafLibraryRegistry);
                val qdrantEmbeddingStore = QdrantEmbeddingStore.builder()
                    .host(langchain4jSettings.getQdrantSettings().getHost())
                    .port(langchain4jSettings.getQdrantSettings().getPort())
                    .collectionName(QdrantUtils.getCollectionName(langchain4jSettings, togafLibraryRegistry))
                    .build();

                log.info("{} - create the embedding store ingestor", togafLibraryRegistry);
                val ingestor = EmbeddingStoreIngestor.builder()
                    .documentTransformer(new HtmlToTextDocumentTransformer("#content"))
                    .documentSplitter(DocumentSplitters.recursive(300, 60))
                    .embeddingModel(embeddingModel)
                    .embeddingStore(qdrantEmbeddingStore)
                    .build();
                builder.ingestor(togafLibraryRegistry, ingestor);

                log.info("{} - create the content retriever", togafLibraryRegistry);
                val contentRetriever = EmbeddingStoreContentRetriever.builder()
                    .embeddingModel(embeddingModel)
                    .embeddingStore(qdrantEmbeddingStore)
                    .maxResults(langchain4jSettings.getMaxEmbeddedContentResult())
                    .minScore(langchain4jSettings.getMinEmbeddedContentScore())
                    .build();
                retrieverToDescription.put(contentRetriever, togafLibraryRegistry.getTitle());
            }
            builder.retrieverToDescription(retrieverToDescription);
        } else {
            log.info("create the ingestor configuration without query routing");

            log.info("ensure the Qdrant collection exists");
            QdrantUtils.ensureCollectionsExist(langchain4jSettings);

            log.info("create the ingestor");
            val qdrantEmbeddingStore = QdrantEmbeddingStore.builder()
                .host(langchain4jSettings.getQdrantSettings().getHost())
                .port(langchain4jSettings.getQdrantSettings().getPort())
                .collectionName(QdrantUtils.getCollectionName(langchain4jSettings))
                .build();

            log.info("create the embedding store ingestor");
            val ingestor = EmbeddingStoreIngestor.builder()
                .documentTransformer(new HtmlToTextDocumentTransformer("#content"))
                .documentSplitter(DocumentSplitters.recursive(300, 60))
                .embeddingModel(embeddingModel)
                .embeddingStore(qdrantEmbeddingStore)
                .build();
            Arrays.stream(TogafLibraryRegistry.values()).forEach(togafLibraryRegistry ->
                builder.ingestor(togafLibraryRegistry, ingestor)
            );

            log.info("create the content retriever");
            val contentRetriever = EmbeddingStoreContentRetriever.builder()
                .embeddingModel(embeddingModel)
                .embeddingStore(qdrantEmbeddingStore)
                .maxResults(langchain4jSettings.getMaxEmbeddedContentResult())
                .minScore(langchain4jSettings.getMinEmbeddedContentScore())
                .build();
            builder.contentRetriever(contentRetriever);
        }

        return builder.build();
    }
}
