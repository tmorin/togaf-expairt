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
class QdrantSettings {

    /**
     * The host to use.
     */
    @NonNull
    @Builder.Default
    String host = SettingReader.readString("togafexpairt_deprecated.qdrant.host", "localhost");

    /**
     * The port to use.
     */
    @Builder.Default
    int port = SettingReader.readInt("togafexpairt_deprecated.qdrant.port", 6334);

    /**
     * The collection name to use.
     */
    @NonNull
    @Builder.Default
    String collectionName = SettingReader.readString("togafexpairt_deprecated.qdrant.collection_name", "togaf-expairt");
}
