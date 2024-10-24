package io.morin.togafexpairt.api;

import java.io.OutputStream;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * The StreamMessageCommand class is the command to stream a message.
 */
@Value
@Builder(toBuilder = true)
public class StreamMessageCommand {

    /**
     * The prompt.
     */
    @NonNull
    String prompt;

    /**
     * The output stream.
     */
    @NonNull
    OutputStream outputStream;
}
