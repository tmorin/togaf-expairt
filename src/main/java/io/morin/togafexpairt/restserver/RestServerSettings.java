package io.morin.togafexpairt.restserver;

import io.morin.togafexpairt.fwk.SettingReader;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * This class represents the settings of the REST server.
 */
@Value
@Builder(toBuilder = true)
class RestServerSettings {

    /**
     * The host of the REST server.
     */
    @NonNull
    @Builder.Default
    String host = SettingReader.readString("togafexpairt.restserver.host", "localhost");

    /**
     * The port of the REST server.
     */
    @Builder.Default
    int port = SettingReader.readInt("togafexpairt.restserver.port", 9090);
}
