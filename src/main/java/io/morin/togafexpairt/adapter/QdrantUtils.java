package io.morin.togafexpairt.adapter;

import io.qdrant.client.QdrantClient;
import io.qdrant.client.QdrantGrpcClient;
import io.qdrant.client.grpc.Collections;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.val;

/**
 * This utility class is used to create a Qdrant client and ensure the collection exists.
 */
@UtilityClass
class QdrantUtils {

    /**
     * Creates a Qdrant client.
     *
     * @param qdrantSettings the Qdrant settings
     * @return the Qdrant client
     */
    @NonNull
    QdrantClient createClient(@NonNull QdrantSettings qdrantSettings) {
        return new QdrantClient(
            QdrantGrpcClient.newBuilder(qdrantSettings.getHost(), qdrantSettings.getPort(), false).build()
        );
    }

    /**
     * Ensures the collection exists.
     *
     * @param qdrantClient the Qdrant client
     * @param qdrantSettings the Qdrant settings
     * @param langchain4jSettings the Langchain4j settings
     */
    @SneakyThrows
    void ensureCollection(
        @NonNull QdrantClient qdrantClient,
        @NonNull QdrantSettings qdrantSettings,
        @NonNull Langchain4jSettings langchain4jSettings
    ) {
        val isCollectionExist = qdrantClient.collectionExistsAsync(qdrantSettings.getCollectionName()).get();
        if (!isCollectionExist) {
            qdrantClient
                .createCollectionAsync(
                    qdrantSettings.getCollectionName(),
                    Collections.VectorParams.newBuilder()
                        .setDistance(Collections.Distance.Cosine)
                        .setSize(langchain4jSettings.getDimension())
                        .build()
                )
                .get();
        }
    }
}
