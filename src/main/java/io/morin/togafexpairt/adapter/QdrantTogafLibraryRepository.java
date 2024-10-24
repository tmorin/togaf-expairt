package io.morin.togafexpairt.adapter;

import dev.langchain4j.data.document.loader.UrlDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import io.morin.togafexpairt.core.TogafLibraryRegistry;
import io.morin.togafexpairt.core.TogafLibraryRepository;
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
    EmbeddingStoreIngestor ingestor;

    @NonNull
    QdrantSettings qdrantSettings;

    @NonNull
    Langchain4jSettings langchain4jSettings;

    @Override
    @SneakyThrows
    public boolean isEmpty() {
        log.info("checking if the TOGAF library repository is empty");
        try (val qdrantClient = QdrantUtils.createClient(qdrantSettings)) {
            val size = qdrantClient.countAsync(qdrantSettings.getCollectionName()).get();
            return size == 0;
        }
    }

    @Override
    @SneakyThrows
    public void clean() {
        log.info("cleaning the TOGAF library repository");
        try (val qdrantClient = QdrantUtils.createClient(qdrantSettings)) {
            qdrantClient.deleteCollectionAsync(qdrantSettings.getCollectionName()).get();
            QdrantUtils.ensureCollection(qdrantClient, qdrantSettings, langchain4jSettings);
        }
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
                document.metadata().put("document_group", togafLibraryRegistry.getTitle());
                return document;
            })
            .toList();

        log.info("ingesting {} documents", documents.size());
        documents.forEach(ingestor::ingest);
    }
}
