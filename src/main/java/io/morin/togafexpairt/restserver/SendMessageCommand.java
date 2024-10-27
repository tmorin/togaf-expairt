package io.morin.togafexpairt.restserver;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * The SendMessageCommand class is the command to send a message.
 */
@Value
@Builder(toBuilder = true)
class SendMessageCommand {

    /**
     * The prompt.
     */
    @NonNull
    String prompt;
}
