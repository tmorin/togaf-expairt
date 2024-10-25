package io.morin.togafexpairt_deprecated.api;

import lombok.NonNull;

/**
 * The TogafExpairtFactory interface is the main entry point for the TOGAF Expairt API.
 * <p>
 * An instance of this interface can be obtained from the ServiceLoader by calling {@code ServiceLoader.load(TogafExpairtFactory.class)}.
 */
public interface TogafExpairtFactory {
    /**
     * Create a new TogafExpairt instance.
     *
     * @param settings the settings
     * @return the TogafExpairt instance
     */
    @NonNull
    TogafExpairt create(@NonNull TogafExpairt.Settings settings);
}
