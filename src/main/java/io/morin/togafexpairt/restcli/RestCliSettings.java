package io.morin.togafexpairt.restcli;

import io.morin.togafexpairt.fwk.SettingReader;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * This class represents the settings of the REST CLI.
 */
@Value
@Builder(toBuilder = true)
class RestCliSettings {

    /**
     * The host of the REST CLI.
     */
    @NonNull
    @Builder.Default
    String promptUrl = SettingReader.readString("togafexpairt.rest_cli.prompt_url", "http://localhost:9090/prompt");
}
