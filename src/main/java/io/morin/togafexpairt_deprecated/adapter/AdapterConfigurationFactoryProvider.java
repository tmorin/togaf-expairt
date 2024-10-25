package io.morin.togafexpairt_deprecated.adapter;

import io.morin.togafexpairt_deprecated.api.TogafExpairt;
import io.morin.togafexpairt_deprecated.core.AdapterConfiguration;
import io.morin.togafexpairt_deprecated.core.AdapterConfigurationFactory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

/**
 * An adapter for the {@link AdapterConfigurationFactory} interface.
 */
@Slf4j
@RequiredArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdapterConfigurationFactoryProvider implements AdapterConfigurationFactory {

    @Override
    public AdapterConfiguration create(TogafExpairt.Settings settings) {
        val langchain4jSettings = Langchain4jSettings.builder().build();

        val qdrantSettings = QdrantSettings.builder().build();

        val langchain4jConfiguration = Langchain4jConfigurationFactory.builder()
            .settings(settings)
            .langchain4jSettings(langchain4jSettings)
            .mistralSettings(MistralSettings.builder().build())
            .ollamaSettings(OllamaSettings.builder().build())
            .qdrantSettings(qdrantSettings)
            .build()
            .create();

        val chat = Langchain4jChat.builder().assistant(langchain4jConfiguration.getAssistant()).build();

        val togafLibraryDocumentRepository = QdrantTogafLibraryRepository.builder()
            .ingestor(langchain4jConfiguration.getIngestor())
            .langchain4jSettings(langchain4jSettings)
            .qdrantSettings(qdrantSettings)
            .build();

        return AdapterConfiguration.builder().chat(chat).togafLibraryRepository(togafLibraryDocumentRepository).build();
    }
}
