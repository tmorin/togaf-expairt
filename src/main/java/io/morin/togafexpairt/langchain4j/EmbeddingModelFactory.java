package io.morin.togafexpairt.langchain4j;

import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.allminilml6v2.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.allminilml6v2q.AllMiniLmL6V2QuantizedEmbeddingModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class EmbeddingModelFactory {

    Langchain4jSettings langchain4jSettings;

    EmbeddingModel create() {
        log.info("create the EmbeddingModel: {}", langchain4jSettings.getEmbeddingModel());
        return switch (langchain4jSettings.getEmbeddingModel()) {
            case MINI_LM -> new AllMiniLmL6V2EmbeddingModel();
            case MINI_LM_Q -> new AllMiniLmL6V2QuantizedEmbeddingModel();
            case OLLAMA -> OllamaEmbeddingModel.builder()
                .baseUrl(langchain4jSettings.getOllamaSettings().getBaseUrl())
                .modelName(langchain4jSettings.getOllamaSettings().getEmbeddingModelName())
                .logRequests(true)
                .logResponses(false)
                .build();
        };
    }
}
