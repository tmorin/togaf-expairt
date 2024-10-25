package io.morin.togafexpairt_deprecated.adapter;

import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * The configuration provided by the langchain4j integration.
 */
@Value
@Builder(toBuilder = true)
class Langchain4jConfiguration {

    /**
     * The assistant.
     */
    @NonNull
    Assistant assistant;

    /**
     * The ingestor.
     */
    @NonNull
    EmbeddingStoreIngestor ingestor;
}
