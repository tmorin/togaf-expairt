package io.morin.togafexpairt_deprecated.indexer;

import io.morin.togafexpairt_deprecated.api.TogafExpairt;
import io.morin.togafexpairt_deprecated.api.TogafExpairtFactory;
import java.util.ServiceLoader;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

/**
 * A command line interface to feed the TOGAF library repository.
 */
@Slf4j
public class IndexerCli {

    public static void main(String[] args) {
        val togafExpairtSettings = TogafExpairt.Settings.builder().forceFeeding(true).build();

        log.info("creating the TOGAF Expairt instance");
        val togafExpairt = ServiceLoader.load(TogafExpairtFactory.class)
            .findFirst()
            .map(factory -> factory.create(togafExpairtSettings))
            .orElseThrow();

        log.info("feeding the TOGAF library repository");
        togafExpairt.feed();
    }
}
