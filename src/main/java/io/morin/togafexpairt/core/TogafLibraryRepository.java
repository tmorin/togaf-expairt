package io.morin.togafexpairt.core;

import lombok.NonNull;

/**
 * The TogafLibraryRepository interface provides a way to interact with the TOGAF library repository.
 */
public interface TogafLibraryRepository {
    /**
     * Checks if the TOGAF library repository is empty.
     *
     * @return true if the TOGAF library repository is empty, false otherwise
     */
    boolean isEmpty();

    /**
     * Cleans the TOGAF library repository.
     */
    void clean();

    /**
     * Feeds the TOGAF library repository with the documents from the {@link TogafLibraryRegistry}.
     *
     * @param togafLibraryRegistry the TOGAF library registry
     */
    void feed(@NonNull TogafLibraryRegistry togafLibraryRegistry);
}
