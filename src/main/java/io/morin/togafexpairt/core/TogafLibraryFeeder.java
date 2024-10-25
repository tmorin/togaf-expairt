package io.morin.togafexpairt.core;

import io.morin.togafexpairt.api.TogafExpairt;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

/**
 * Feeds the TOGAF library document repository.
 */
@Slf4j
@RequiredArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class TogafLibraryFeeder {

    @NonNull
    TogafExpairt.Settings settings;

    @NonNull
    TogafLibraryRepository togafLibraryRepository;

    /**
     * Feeds the TOGAF library document repository.
     * <p>
     * If the {@link io.morin.togafexpairt.core.TogafLibraryRepository} is empty, it will feed it with the documents from the {@link io.morin.togafexpairt.core.TogafLibraryRegistry}.
     * If the {@link TogafLibraryRepository} is not empty, it will clean it if the {@link TogafExpairt.Settings#isForceFeeding()} is set to true.
     */
    void feed() {
        if (settings.isForceFeeding()) {
            log.info("cleaning the TOGAF library document repository");
            togafLibraryRepository.reset();
        }

        if (togafLibraryRepository.isEmpty()) {
            log.info("feeding the TOGAF library document repository");
            for (io.morin.togafexpairt.core.TogafLibraryRegistry togafLibraryRegistry : TogafLibraryRegistry.values()) {
                togafLibraryRepository.feed(togafLibraryRegistry);
            }
        }
    }
}
