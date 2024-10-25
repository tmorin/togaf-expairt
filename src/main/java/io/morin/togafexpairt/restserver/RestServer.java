package io.morin.togafexpairt.restserver;

import io.morin.togafexpairt.api.TogafExpairt;
import io.morin.togafexpairt.api.TogafExpairtFactory;
import io.morin.togafexpairt.fwk.SettingReader;
import java.util.Optional;
import java.util.ServiceLoader;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

/**
 * An HTTP server exposing a REST API of the AI chat.
 */
@Slf4j
public class RestServer {

    @SneakyThrows
    public static void main(String[] args) {
        System.setProperty("org.apache.lucene.store.MMapDirectory.enableMemorySegments", "false");

        val togafExpairtSettings = TogafExpairt.Settings.builder().build();

        log.info("creating the TOGAF Expairt instance");
        val togafExpairt = ServiceLoader.load(TogafExpairtFactory.class)
            .findFirst()
            .map(factory -> factory.create(togafExpairtSettings))
            .orElseThrow();

        log.info("feeding the TOGAF library repository");
        togafExpairt.feed();

        log.info("starting REST server");
        Optional.of(PromptHandler.builder().togafExpairt(togafExpairt).build())
            .map(promptHandler -> HttpServerFactory.builder().promptHandler(promptHandler).build().create())
            .orElseThrow(() -> new IllegalStateException("No TogafExpairtFactory found"))
            .start();
    }

    /**
     * This class represents the settings of the REST server.
     */
    @Value
    @Builder(toBuilder = true)
    static class RestServerSettings {

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
}
