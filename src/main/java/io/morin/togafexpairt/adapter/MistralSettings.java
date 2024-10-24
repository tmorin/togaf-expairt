package io.morin.togafexpairt.adapter;

import io.morin.togafexpairt.fwk.SettingReader;
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
    String modelName = SettingReader.readString("togafexpairt.mistral.model_name", "mistral-medium");

    /**
     * The API key to use.
     */
    @NonNull
    @Builder.Default
    String apiKey = SettingReader.readString(
        "togafexpairt.mistral.api_key",
        "set the togafexpairt.mistral.api_key setting"
    );
}
