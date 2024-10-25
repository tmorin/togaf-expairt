package io.morin.togafexpairt.langchain4j;

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
     */
    @SneakyThrows
    void resetCollection(@NonNull Langchain4jSettings langchain4jSettings) {
        try (val qdrantClient = createClient(langchain4jSettings)) {
            qdrantClient.deleteCollectionAsync(langchain4jSettings.getQdrantSettings().getCollectionName()).get();
            qdrantClient
                .createCollectionAsync(
                    langchain4jSettings.getQdrantSettings().getCollectionName(),
                    Collections.VectorParams.newBuilder()
                        .setDistance(Collections.Distance.Cosine)
                        .setSize(langchain4jSettings.getDimension())
                        .build()
                )
                .get();
        }
    }

    /**
     * Ensures the collection exists.
     *
     * @param langchain4jSettings the Langchain4j settings
     */
    @SneakyThrows
    void ensureCollectionExist(@NonNull Langchain4jSettings langchain4jSettings) {
        try (val qdrantClient = createClient(langchain4jSettings)) {
            val isCollectionExist = qdrantClient
                .collectionExistsAsync(langchain4jSettings.getQdrantSettings().getCollectionName())
                .get();
            if (!isCollectionExist) {
                qdrantClient
                    .createCollectionAsync(
                        langchain4jSettings.getQdrantSettings().getCollectionName(),
                        Collections.VectorParams.newBuilder()
                            .setDistance(Collections.Distance.Cosine)
                            .setSize(langchain4jSettings.getDimension())
                            .build()
                    )
                    .get();
            }
        }
    }
}
