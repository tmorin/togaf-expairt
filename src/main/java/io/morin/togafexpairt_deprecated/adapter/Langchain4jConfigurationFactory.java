package io.morin.togafexpairt_deprecated.adapter;

import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.document.transformer.jsoup.HtmlToTextDocumentTransformer;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.allminilml6v2q.AllMiniLmL6V2QuantizedEmbeddingModel;
import dev.langchain4j.model.mistralai.MistralAiChatModel;
import dev.langchain4j.model.mistralai.MistralAiStreamingChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.qdrant.QdrantEmbeddingStore;
import io.morin.togafexpairt_deprecated.api.TogafExpairt;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

/**
 * A factory for the Langchain4j configuration.
 */
@Slf4j
@RequiredArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class Langchain4jConfigurationFactory {

    @NonNull
    TogafExpairt.Settings settings;

    @NonNull
    @Builder.Default
    MistralSettings mistralSettings = MistralSettings.builder().build();

    @NonNull
    @Builder.Default
    OllamaSettings ollamaSettings = OllamaSettings.builder().build();

    @NonNull
    @Builder.Default
    QdrantSettings qdrantSettings = QdrantSettings.builder().build();

    @NonNull
    @Builder.Default
    Langchain4jSettings langchain4jSettings = Langchain4jSettings.builder().build();

    private ChatLanguageModel createChatLanguageModel() {
        log.info("create the ChatLanguageModel: {}", langchain4jSettings.getChatModel());
        return switch (langchain4jSettings.getChatModel()) {
            case MISTRAL -> MistralAiChatModel.builder()
                .apiKey(mistralSettings.getApiKey())
                .modelName(mistralSettings.getModelName())
                .logRequests(true)
                .logResponses(false)
                .build();
            case OLLAMA -> OllamaChatModel.builder()
                .baseUrl(ollamaSettings.getBaseUrl())
                .modelName(ollamaSettings.getChatModelName())
                .logRequests(true)
                .logResponses(false)
                .build();
        };
    }

    private EmbeddingModel createEmbeddingModel() {
        log.info("create the EmbeddingModel: {}", langchain4jSettings.getEmbeddingModel());
        return switch (langchain4jSettings.getEmbeddingModel()) {
            case MINI_LM -> new AllMiniLmL6V2QuantizedEmbeddingModel();
            case OLLAMA -> OllamaEmbeddingModel.builder()
                .baseUrl(ollamaSettings.getBaseUrl())
                .modelName(ollamaSettings.getEmbeddingModelName())
                .logRequests(true)
                .logResponses(false)
                .build();
        };
    }

    private StreamingChatLanguageModel createStreamingChatLanguageModel() {
        log.info("create the StreamingChatLanguageModel: {}", langchain4jSettings.getChatModel());
        return switch (langchain4jSettings.getChatModel()) {
            case MISTRAL -> MistralAiStreamingChatModel.builder()
                .apiKey(mistralSettings.getApiKey())
                .modelName(mistralSettings.getModelName())
                .logRequests(true)
                .logResponses(false)
                .build();
            case OLLAMA -> OllamaStreamingChatModel.builder()
                .baseUrl(ollamaSettings.getBaseUrl())
                .modelName(ollamaSettings.getChatModelName())
                .logRequests(true)
                .logResponses(false)
                .build();
        };
    }

    Langchain4jConfiguration create() {
        log.info("create the embedding configuration");
        val embeddingModel = createEmbeddingModel();

        log.info("ensure the Qdrant collection exists");
        try (val client = QdrantUtils.createClient(qdrantSettings)) {
            QdrantUtils.ensureCollection(client, qdrantSettings, langchain4jSettings);
        }

        log.info("create the Qdrant embedding store");
        val qdrantEmbeddingStore = QdrantEmbeddingStore.builder()
            .host(qdrantSettings.getHost())
            .port(qdrantSettings.getPort())
            .collectionName(qdrantSettings.getCollectionName())
            .build();

        log.info("create the ingestor");
        val ingestor = EmbeddingStoreIngestor.builder()
            .documentTransformer(new HtmlToTextDocumentTransformer("#content"))
            .documentSplitter(DocumentSplitters.recursive(300, 60))
            .embeddingModel(embeddingModel)
            .embeddingStore(qdrantEmbeddingStore)
            .build();

        log.info("create the assistant service");
        val assistant = AiServices.builder(Assistant.class)
            .contentRetriever(
                EmbeddingStoreContentRetriever.builder()
                    .embeddingModel(embeddingModel)
                    .embeddingStore(qdrantEmbeddingStore)
                    .maxResults(langchain4jSettings.getMaxEmbeddedContentResult())
                    .build()
            )
            .streamingChatLanguageModel(createStreamingChatLanguageModel())
            .chatLanguageModel(createChatLanguageModel())
            .chatMemoryProvider(memoryId ->
                MessageWindowChatMemory.withMaxMessages(langchain4jSettings.getMaxMemoryMessages())
            )
            .build();

        log.info("create and return the configuration");
        return Langchain4jConfiguration.builder().ingestor(ingestor).assistant(assistant).build();
    }
}
