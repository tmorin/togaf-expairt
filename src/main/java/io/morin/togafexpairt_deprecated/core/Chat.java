package io.morin.togafexpairt_deprecated.core;

import java.io.OutputStream;

/**
 * The chat interface.
 */
public interface Chat {
    /**
     * Asks the user a question.
     *
     * @param message      the message
     * @param outputStream the output stream
     */
    void ask(String message, OutputStream outputStream);
}
