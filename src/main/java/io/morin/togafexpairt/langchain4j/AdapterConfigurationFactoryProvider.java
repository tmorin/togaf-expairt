package io.morin.togafexpairt.langchain4j;

import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.document.transformer.jsoup.HtmlToTextDocumentTransformer;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.qdrant.QdrantEmbeddingStore;
import io.morin.togafexpairt.api.TogafExpairt;
import io.morin.togafexpairt.core.AdapterConfiguration;
import io.morin.togafexpairt.core.AdapterConfigurationFactory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

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

        log.info("create the embedding configuration");
        val embeddingModel = EmbeddingModelFactory.builder().langchain4jSettings(langchain4jSettings).build().create();

        log.info("ensure the Qdrant collection exists");
        QdrantUtils.ensureCollectionExist(langchain4jSettings);

        log.info("create the Qdrant embedding store");
        val qdrantEmbeddingStore = QdrantEmbeddingStore.builder()
            .host(langchain4jSettings.getQdrantSettings().getHost())
            .port(langchain4jSettings.getQdrantSettings().getPort())
            .collectionName(langchain4jSettings.getQdrantSettings().getCollectionName())
            .build();

        log.info("create the ingestor");
        val ingestor = EmbeddingStoreIngestor.builder()
            .documentTransformer(new HtmlToTextDocumentTransformer("#content"))
            .documentSplitter(DocumentSplitters.recursive(300, 60))
            .embeddingModel(embeddingModel)
            .embeddingStore(qdrantEmbeddingStore)
            .build();

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

        log.info("create the langchain4jAssistant service");
        val langchain4jAssistant = AiServices.builder(Langchain4jAssistant.class)
            .contentRetriever(
                EmbeddingStoreContentRetriever.builder()
                    .embeddingModel(embeddingModel)
                    .embeddingStore(qdrantEmbeddingStore)
                    .maxResults(langchain4jSettings.getMaxEmbeddedContentResult())
                    .minScore(langchain4jSettings.getMinEmbeddedContentScore())
                    .build()
            )
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
            .ingestor(ingestor)
            .langchain4jSettings(langchain4jSettings)
            .build();

        log.info("create and return the adapter configuration");
        return AdapterConfiguration.builder().chat(chat).togafLibraryRepository(togafLibraryDocumentRepository).build();
    }
}
