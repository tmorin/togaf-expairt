package io.morin.togafexpairt_deprecated.adapter;

import io.morin.togafexpairt_deprecated.fwk.SettingReader;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * The settings for the Neo4j integration.
 */
@Value
@Builder(toBuilder = true)
class OllamaSettings {

    /**
     * The model name to use.
     */
    @NonNull
    @Builder.Default
    String chatModelName = SettingReader.readString("togafexpairt_deprecated.ollama.chat_model_name", "llama3.2:1b");

    /**
     * The model name to use.
     */
    @NonNull
    @Builder.Default
    String embeddingModelName = SettingReader.readString(
        "togafexpairt_deprecated.ollama.embedding_model_name",
        "nomic-embed-text"
    );

    /**
     * The base URL to use.
     */
    @NonNull
    @Builder.Default
    String baseUrl = SettingReader.readString("togafexpairt_deprecated.ollama.base_url", "http://localhost:11434");
}
