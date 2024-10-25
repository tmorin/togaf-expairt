package io.morin.togafexpairt.core;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

/**
 * Factory for creating a new instance of {@link TogafExpairtCore}.
 */
@Slf4j
@RequiredArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class TogafExpairtCoreFactory {

    @NonNull
    Chat chat;

    @NonNull
    TogafLibraryFeeder togafLibraryFeeder;

    /**
     * Creates a new instance of {@link TogafExpairtCore}.
     *
     * @return a new instance of {@link TogafExpairtCore}
     */
    @NonNull
    TogafExpairtCore create() {
        return TogafExpairtCore.builder().chat(chat).togafLibraryFeeder(togafLibraryFeeder).build();
    }
}
