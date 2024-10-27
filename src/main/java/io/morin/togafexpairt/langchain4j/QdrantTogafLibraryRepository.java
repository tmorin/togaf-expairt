package io.morin.togafexpairt.langchain4j;

import dev.langchain4j.data.document.loader.UrlDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import io.morin.togafexpairt.core.TogafLibraryRegistry;
import io.morin.togafexpairt.core.TogafLibraryRepository;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class QdrantTogafLibraryRepository implements TogafLibraryRepository {

    @NonNull
    @Builder.Default
    TextDocumentParser textDocumentParser = new TextDocumentParser();

    @NonNull
    Map<TogafLibraryRegistry, EmbeddingStoreIngestor> ingestors;

    @NonNull
    Langchain4jSettings langchain4jSettings;

    @Override
    @SneakyThrows
    public boolean isEmpty() {
        log.info("checking if the TOGAF library repository is empty");
        try (val qdrantClient = QdrantUtils.createClient(langchain4jSettings)) {
            final long size = Arrays.stream(TogafLibraryRegistry.values())
                .map(togafLibraryRegistry -> QdrantUtils.getCollectionName(langchain4jSettings, togafLibraryRegistry))
                .map(collectionName -> {
                    try {
                        return qdrantClient.countAsync(collectionName).get();
                    } catch (InterruptedException e) {
                        log.error("interrupted while counting the collection", e);
                        Thread.currentThread().interrupt();
                        return 0L;
                    } catch (ExecutionException e) {
                        throw new IllegalStateException("Failed to count the collection", e);
                    }
                })
                .reduce(Long::sum)
                .orElse(0L);
            log.info("the TOGAF library repository is empty: {}", size == 0);
            return size == 0;
        }
    }

    @Override
    @SneakyThrows
    public void reset() {
        log.info("resetting the TOGAF library repository");
        Arrays.stream(TogafLibraryRegistry.values()).forEach(togafLibraryRegistry ->
            QdrantUtils.resetCollections(langchain4jSettings, togafLibraryRegistry)
        );
    }

    @Override
    public void feed(@NonNull TogafLibraryRegistry togafLibraryRegistry) {
        val documents = togafLibraryRegistry
            .getUrls()
            .stream()
            .parallel()
            .map(url -> {
                log.info("loading document from {}", url);
                val document = UrlDocumentLoader.load(url, textDocumentParser);
                document.metadata().put("document_group_name", togafLibraryRegistry.getTitle());
                document.metadata().put("document_group_description", togafLibraryRegistry.getDescription());
                return document;
            })
            .toList();

        log.info("ingesting {} documents", documents.size());
        documents.forEach(ingestors.get(togafLibraryRegistry)::ingest);
    }
}
