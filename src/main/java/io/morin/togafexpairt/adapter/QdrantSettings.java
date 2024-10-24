package io.morin.togafexpairt.adapter;

import io.morin.togafexpairt.fwk.SettingReader;
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
