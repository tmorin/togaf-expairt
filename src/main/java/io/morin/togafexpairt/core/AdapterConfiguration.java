package io.morin.togafexpairt.core;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * The configuration provided by the adapter implementation.
 */
@Value
@Builder(toBuilder = true)
public class AdapterConfiguration {

    /**
     * The adapted Chat instance.
     */
    @NonNull
    Chat chat;

    /**
     * The adapted TogafLibraryRepository instance.
     */
    @NonNull
    TogafLibraryRepository togafLibraryRepository;
}
