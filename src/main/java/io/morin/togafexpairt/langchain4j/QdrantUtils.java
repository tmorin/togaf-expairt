package io.morin.togafexpairt.langchain4j;

import io.morin.togafexpairt.core.TogafLibraryRegistry;
import io.qdrant.client.QdrantClient;
import io.qdrant.client.QdrantGrpcClient;
import io.qdrant.client.grpc.Collections;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.val;

/**
 * This utility class is used to interact with Qdrant.
 */
@UtilityClass
class QdrantUtils {

    /**
     * Creates a Qdrant client.
     *
     * @param langchain4jSettings the Langchain4j settings
     * @return the Qdrant client
     */
    @NonNull
    QdrantClient createClient(@NonNull Langchain4jSettings langchain4jSettings) {
        return new QdrantClient(
            QdrantGrpcClient.newBuilder(
                langchain4jSettings.getQdrantSettings().getHost(),
                langchain4jSettings.getQdrantSettings().getPort(),
                false
            ).build()
        );
    }

    /**
     * Ensures the collection exists.
     *
     * @param langchain4jSettings the Langchain4j settings
     * @param togafLibraryRegistry the TOGAF library registry
     */
    @SneakyThrows
    void resetCollections(
        @NonNull Langchain4jSettings langchain4jSettings,
        @NonNull TogafLibraryRegistry togafLibraryRegistry
    ) {
        try (val qdrantClient = createClient(langchain4jSettings)) {
            val collectionName = getCollectionName(langchain4jSettings, togafLibraryRegistry);
            qdrantClient.deleteCollectionAsync(collectionName).get();
            qdrantClient
                .createCollectionAsync(
                    collectionName,
                    Collections.VectorParams.newBuilder()
                        .setDistance(Collections.Distance.Cosine)
                        .setSize(langchain4jSettings.getDimension())
                        .build()
                )
                .get();
        }
    }

    /**
     * Gets the collection name.
     *
     * @param langchain4jSettings the Langchain4j settings
     * @param togafLibraryRegistry the TOGAF library registry
     * @return the collection name
     */
    String getCollectionName(
        @NonNull Langchain4jSettings langchain4jSettings,
        @NonNull TogafLibraryRegistry togafLibraryRegistry
    ) {
        return String.format(
            "%s_%s_%s",
            langchain4jSettings.getQdrantSettings().getCollectionName(),
            langchain4jSettings.getDimension(),
            togafLibraryRegistry.name()
        );
    }

    /**
     * Ensures the collection exists.
     *
     * @param langchain4jSettings the Langchain4j settings
     * @param togafLibraryRegistry the TOGAF library registry
     */
    @SneakyThrows
    void ensureCollectionsExist(
        @NonNull Langchain4jSettings langchain4jSettings,
        @NonNull TogafLibraryRegistry togafLibraryRegistry
    ) {
        try (val qdrantClient = createClient(langchain4jSettings)) {
            val collectionName = getCollectionName(langchain4jSettings, togafLibraryRegistry);
            val isCollectionExist = qdrantClient.collectionExistsAsync(collectionName).get();
            if (!isCollectionExist) {
                qdrantClient
                    .createCollectionAsync(
                        collectionName,
                        Collections.VectorParams.newBuilder()
                            .setDistance(Collections.Distance.Cosine)
                            .setSize(langchain4jSettings.getDimension())
                            .build()
                    )
                    .get();
            }
        }
    }

    /**
     * Ensures the collection exists.
     *
     * @param langchain4jSettings the Langchain4j settings
     */
    @SneakyThrows
    void ensureCollectionsExist(@NonNull Langchain4jSettings langchain4jSettings) {
        try (val qdrantClient = createClient(langchain4jSettings)) {
            val collectionName = QdrantUtils.getCollectionName(langchain4jSettings);
            val isCollectionExist = qdrantClient.collectionExistsAsync(collectionName).get();
            if (!isCollectionExist) {
                qdrantClient
                    .createCollectionAsync(
                        collectionName,
                        Collections.VectorParams.newBuilder()
                            .setDistance(Collections.Distance.Cosine)
                            .setSize(langchain4jSettings.getDimension())
                            .build()
                    )
                    .get();
            }
        }
    }

    /**
     * Gets the collection name.
     *
     * @param langchain4jSettings the Langchain4j settings
     * @return the collection name
     */
    String getCollectionName(@NonNull Langchain4jSettings langchain4jSettings) {
        return String.format(
            "%s_%s",
            langchain4jSettings.getQdrantSettings().getCollectionName(),
            langchain4jSettings.getDimension()
        );
    }

    /**
     * Resets the collection.
     *
     * @param langchain4jSettings the Langchain4j settings
     */
    @SneakyThrows
    void resetCollection(@NonNull Langchain4jSettings langchain4jSettings) {
        try (val qdrantClient = createClient(langchain4jSettings)) {
            val collectionName = getCollectionName(langchain4jSettings);
            qdrantClient.deleteCollectionAsync(collectionName).get();
            qdrantClient
                .createCollectionAsync(
                    collectionName,
                    Collections.VectorParams.newBuilder()
                        .setDistance(Collections.Distance.Cosine)
                        .setSize(langchain4jSettings.getDimension())
                        .build()
                )
                .get();
        }
    }
}
