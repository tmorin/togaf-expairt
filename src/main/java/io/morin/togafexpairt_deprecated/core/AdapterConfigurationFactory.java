package io.morin.togafexpairt_deprecated.core;

import io.morin.togafexpairt_deprecated.api.TogafExpairt;

/**
 * Factory for creating the adapter configuration.
 * <p>
 * This factory is used to create an adapter configuration from the settings.
 * An instance of this interface can be obtained from the ServiceLoader by calling {@code ServiceLoader.load(AdapterConfigurationFactory.class)}.
 */
public interface AdapterConfigurationFactory {
    /**
     * Creates an adapter configuration from the settings.
     *
     * @param settings the settings
     * @return the adapter configuration
     */
    AdapterConfiguration create(TogafExpairt.Settings settings);
}
