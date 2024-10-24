package io.morin.togafexpairt.core;

import java.io.OutputStream;

/**
 * The chat interface.
 */
public interface Chat {
    /**
     * Asks the user a question.
     *
     * @param message the message
     * @return the answer
     */
    String ask(String message);

    /**
     * Asks the user a question.
     *
     * @param message      the message
     * @param outputStream the output stream
     */
    void ask(String message, OutputStream outputStream);
}
