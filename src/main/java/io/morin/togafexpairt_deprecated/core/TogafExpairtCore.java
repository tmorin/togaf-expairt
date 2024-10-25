package io.morin.togafexpairt_deprecated.core;

import io.morin.togafexpairt_deprecated.api.StreamMessageCommand;
import io.morin.togafexpairt_deprecated.api.TogafExpairt;
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
    public void execute(@NonNull StreamMessageCommand sendMessageCommand) {
        chat.ask(sendMessageCommand.getPrompt(), sendMessageCommand.getOutputStream());
    }

    @Override
    public void feed() {
        togafLibraryFeeder.feed();
    }
}
