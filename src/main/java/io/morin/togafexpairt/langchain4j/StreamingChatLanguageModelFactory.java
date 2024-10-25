package io.morin.togafexpairt.langchain4j;

import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.mistralai.MistralAiStreamingChatModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class StreamingChatLanguageModelFactory {

    Langchain4jSettings langchain4jSettings;

    StreamingChatLanguageModel create() {
        log.info("create the StreamingChatLanguageModel: {}", langchain4jSettings.getChatModel());
        return switch (langchain4jSettings.getChatModel()) {
            case MISTRAL -> MistralAiStreamingChatModel.builder()
                .apiKey(langchain4jSettings.getMistralSettings().getApiKey())
                .modelName(langchain4jSettings.getMistralSettings().getModelName())
                .logRequests(true)
                .logResponses(false)
                .build();
            case OLLAMA -> OllamaStreamingChatModel.builder()
                .baseUrl(langchain4jSettings.getOllamaSettings().getBaseUrl())
                .modelName(langchain4jSettings.getOllamaSettings().getChatModelName())
                .logRequests(true)
                .logResponses(false)
                .build();
        };
    }
}
