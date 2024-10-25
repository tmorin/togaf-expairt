package io.morin.togafexpairt_deprecated.adapter;

import io.morin.togafexpairt_deprecated.fwk.SettingReader;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * The settings for the Mistral integration.
 */
@Value
@Builder(toBuilder = true)
class MistralSettings {

    /**
     * The model name to use.
     */
    @NonNull
    @Builder.Default
    String modelName = SettingReader.readString("togafexpairt_deprecated.mistral.model_name", "mistral-medium");

    /**
     * The API key to use.
     */
    @NonNull
    @Builder.Default
    String apiKey = SettingReader.readString(
        "togafexpairt_deprecated.mistral.api_key",
        "set the togafexpairt_deprecated.mistral.api_key setting"
    );
}
