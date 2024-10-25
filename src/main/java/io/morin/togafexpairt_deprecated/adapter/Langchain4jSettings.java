package io.morin.togafexpairt_deprecated.adapter;

import io.morin.togafexpairt_deprecated.fwk.SettingReader;
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
     * The integration to use.
     */
    @NonNull
    @Builder.Default
    Langchain4jSettings.ChatModel chatModel = ChatModel.valueOf(
        SettingReader.readString("togafexpairt_deprecated.langchain4j.chat_model", ChatModel.OLLAMA.name())
    );

    /**
     * The integration to use.
     */
    @NonNull
    @Builder.Default
    Langchain4jSettings.EmbeddingModel embeddingModel = EmbeddingModel.valueOf(
        SettingReader.readString("togafexpairt_deprecated.langchain4j.embedding_model", EmbeddingModel.OLLAMA.name())
    );

    /**
     * The dimension of the embeddings.
     */
    @Builder.Default
    int dimension = SettingReader.readInt("togafexpairt_deprecated.langchain4j.dimension", 768);

    /**
     * The maximum number of messages to keep in memory.
     */
    @Builder.Default
    int maxMemoryMessages = SettingReader.readInt("togafexpairt_deprecated.langchain4j.max_memory_messages", 20);

    /**
     * The maximum segment size in characters.
     */
    @Builder.Default
    int maxSegmentSizeInChars = SettingReader.readInt(
        "togafexpairt_deprecated.langchain4j.max_segment_size_in_chars",
        500
    );

    /**
     * The maximum overlap size in characters.
     */
    @Builder.Default
    int maxOverlapSizeInChars = SettingReader.readInt(
        "togafexpairt_deprecated.langchain4j.max_overlap_size_in_chars",
        60
    );

    /**
     * The maximum number when searching matching embedded content.
     */
    @Builder.Default
    int maxEmbeddedContentResult = SettingReader.readInt(
        "togafexpairt_deprecated.langchain4j.max_embedded_content_result",
        30
    );

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
        OLLAMA
    }
}
