package io.morin.togafexpairt.restserver;

import io.morin.togafexpairt.api.TogafExpairt;
import io.morin.togafexpairt.api.TogafExpairtFactory;
import java.util.Optional;
import java.util.ServiceLoader;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

/**
 * This Java Main class is used to start the TOGAF Expairt REST server.
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

        log.info("starting REST server for codebase");
        Optional.of(PromptHandler.builder().togafExpairt(togafExpairt).build())
            .map(promptHandler -> HttpServerFactory.builder().promptHandler(promptHandler).build().create())
            .orElseThrow(() -> new IllegalStateException("No TogafExpairtFactory found"))
            .start();
    }
}
