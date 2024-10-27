package io.morin.togafexpairt.core;

import io.morin.togafexpairt.api.FeedCommand;
import io.morin.togafexpairt.api.StreamMessageCommand;
import io.morin.togafexpairt.api.TogafExpairt;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

/**
 * The core implementation of the TogafExpairt.
 */
@Slf4j
@RequiredArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class TogafExpairtCore implements TogafExpairt {

    @NonNull
    Chat chat;

    @NonNull
    TogafLibraryFeeder togafLibraryFeeder;

    @Override
    public void execute(@NonNull StreamMessageCommand streamMessageCommand) {
        chat.ask(streamMessageCommand.getPrompt(), streamMessageCommand.getOutputStream());
    }

    @Override
    public void execute(@NonNull FeedCommand feedCommand) {
        togafLibraryFeeder.feed();
    }
}
