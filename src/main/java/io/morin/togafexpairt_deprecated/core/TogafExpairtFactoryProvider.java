package io.morin.togafexpairt_deprecated.core;

import io.morin.togafexpairt_deprecated.api.TogafExpairt;
import io.morin.togafexpairt_deprecated.api.TogafExpairtFactory;
import java.util.ServiceLoader;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

/**
 * The provider for the {@link TogafExpairtFactory} when using the ServiceLoader API.
 */
@Slf4j
public class TogafExpairtFactoryProvider implements TogafExpairtFactory {

    @Override
    public @NonNull TogafExpairt create(@NonNull TogafExpairt.Settings settings) {
        log.debug("Loading adapter configuration factory and creating adapter configuration");

        val adapterConfiguration = ServiceLoader.load(AdapterConfigurationFactory.class)
            .findFirst()
            .map(factory -> factory.create(settings))
            .orElseThrow(() -> new IllegalStateException("No adapter configuration factory found"));

        val togafLibraryFeeder = TogafLibraryFeeder.builder()
            .settings(settings)
            .togafLibraryRepository(adapterConfiguration.getTogafLibraryRepository())
            .build();

        return TogafExpairtCoreFactory.builder()
            .chat(adapterConfiguration.getChat())
            .togafLibraryFeeder(togafLibraryFeeder)
            .build()
            .create();
    }
}
