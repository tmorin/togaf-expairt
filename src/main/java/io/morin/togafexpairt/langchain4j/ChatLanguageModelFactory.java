package io.morin.togafexpairt.langchain4j;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.mistralai.MistralAiChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class ChatLanguageModelFactory {

    Langchain4jSettings langchain4jSettings;

    ChatLanguageModel create() {
        log.info("create the ChatLanguageModel: {}", langchain4jSettings.getChatModel());
        return switch (langchain4jSettings.getChatModel()) {
            case MISTRAL -> MistralAiChatModel.builder()
                .apiKey(langchain4jSettings.getMistralSettings().getApiKey())
                .modelName(langchain4jSettings.getMistralSettings().getModelName())
                .logRequests(true)
                .logResponses(true)
                .build();
            case OLLAMA -> OllamaChatModel.builder()
                .baseUrl(langchain4jSettings.getOllamaSettings().getBaseUrl())
                .modelName(langchain4jSettings.getOllamaSettings().getChatModelName())
                .logRequests(true)
                .logResponses(true)
                .build();
        };
    }
}
