package io.morin.togafexpairt.langchain4j;

import io.morin.togafexpairt.fwk.SettingReader;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * The settings for the Langchain4j integration.
 */
@Value
@Builder(toBuilder = true)
class Langchain4jSettings {

    /**
     * The settings for the Mistral integration.
     */
    MistralSettings mistralSettings = MistralSettings.builder().build();

    /**
     * The settings for the Ollama integration.
     */
    OllamaSettings ollamaSettings = OllamaSettings.builder().build();

    /**
     * The settings for the Qdrant integration.
     */
    QdrantSettings qdrantSettings = QdrantSettings.builder().build();

    /**
     * The supported Langchain4j integrations for chat.
     */
    enum ChatModel {
        MISTRAL,
        OLLAMA
    }

    /**
     * The supported Langchain4j integrations for embeddings.
     */
    enum EmbeddingModel {
        MINI_LM,
        MINI_LM_Q,
        OLLAMA
    }

    /**
     * The integration to use.
     */
    @NonNull
    @Builder.Default
    Langchain4jSettings.ChatModel chatModel = ChatModel.valueOf(
        SettingReader.readString("togafexpairt.langchain4j.chat_model", ChatModel.OLLAMA.name())
    );

    /**
     * The integration to use.
     */
    @NonNull
    @Builder.Default
    Langchain4jSettings.EmbeddingModel embeddingModel = EmbeddingModel.valueOf(
        SettingReader.readString("togafexpairt.langchain4j.embedding_model", EmbeddingModel.MINI_LM.name())
    );

    /**
     * The dimension of the embeddings.
     */
    @Builder.Default
    int dimension = SettingReader.readInt("togafexpairt.langchain4j.dimension", 384);

    /**
     * The maximum number of messages to keep in memory.
     */
    @Builder.Default
    int maxMemoryMessages = SettingReader.readInt("togafexpairt.langchain4j.max_memory_messages", 20);

    /**
     * The maximum segment size in characters.
     */
    @Builder.Default
    int maxSegmentSizeInChars = SettingReader.readInt("togafexpairt.langchain4j.max_segment_size_in_chars", 500);

    /**
     * The maximum overlap size in characters.
     */
    @Builder.Default
    int maxOverlapSizeInChars = SettingReader.readInt("togafexpairt.langchain4j.max_overlap_size_in_chars", 60);

    /**
     * The maximum number when searching matching embedded content.
     */
    @Builder.Default
    int maxEmbeddedContentResult = SettingReader.readInt("togafexpairt.langchain4j.max_embedded_content_result", 30);

    /**
     * The minimum score when searching matching embedded content.
     */
    @Builder.Default
    double minEmbeddedContentScore = SettingReader.readDouble(
        "togafexpairt.langchain4j.min_embedded_content_score",
        0.5
    );

    /**
     * The settings for the Mistral integration.
     */
    @Value
    @Builder(toBuilder = true)
    static class MistralSettings {

        /**
         * The model name to use.
         */
        @NonNull
        @Builder.Default
        String modelName = SettingReader.readString("togafexpairt.mistral.model_name", "mistral-medium");

        /**
         * The API key to use.
         */
        @NonNull
        @Builder.Default
        String apiKey = SettingReader.readString(
            "togafexpairt.mistral.api_key",
            "set the togafexpairt.mistral.api_key setting"
        );
    }

    /**
     * The settings for the Neo4j integration.
     */
    @Value
    @Builder(toBuilder = true)
    static class OllamaSettings {

        /**
         * The model name to use.
         */
        @NonNull
        @Builder.Default
        String chatModelName = SettingReader.readString("togafexpairt.ollama.chat_model_name", "llama3.2:1b");

        /**
         * The model name to use.
         */
        @NonNull
        @Builder.Default
        String embeddingModelName = SettingReader.readString(
            "togafexpairt.ollama.embedding_model_name",
            "nomic-embed-text"
        );

        /**
         * The base URL to use.
         */
        @NonNull
        @Builder.Default
        String baseUrl = SettingReader.readString("togafexpairt.ollama.base_url", "http://localhost:11434");
    }

    /**
     * The settings for the Neo4j integration.
     */
    @Value
    @Builder(toBuilder = true)
    static class QdrantSettings {

        /**
         * The host to use.
         */
        @NonNull
        @Builder.Default
        String host = SettingReader.readString("togafexpairt.qdrant.host", "localhost");

        /**
         * The port to use.
         */
        @Builder.Default
        int port = SettingReader.readInt("togafexpairt.qdrant.port", 6334);

        /**
         * The collection name to use.
         */
        @NonNull
        @Builder.Default
        String collectionName = SettingReader.readString("togafexpairt.qdrant.collection_name", "togaf-expairt");
    }
}
