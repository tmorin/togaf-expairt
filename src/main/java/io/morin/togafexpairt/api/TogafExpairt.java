package io.morin.togafexpairt.api;

import io.morin.togafexpairt.fwk.SettingReader;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * The TogafExpairt interface is the facade for the TOGAF Expairt API.
 */
public interface TogafExpairt {
    /**
     * Execute the Stream Message Command.
     * <p>
     * The command handling is asynchronous and will return a StreamMessageResult immediately.
     * The provided OutputStream will be used to stream the result of the command.
     *
     * @param streamMessageCommand the send message command
     */
    void execute(@NonNull StreamMessageCommand streamMessageCommand);

    /**
     * Execute the Feed Command.
     */
    void execute(@NonNull FeedCommand feedCommand);

    /**
     * The settings.
     */
    @Value
    @Builder(toBuilder = true)
    class Settings {

        /**
         * Force the feeding of TOGAF Library based on the TOGAF Standard: https://pubs.opengroup.org/togaf-standard.
         */
        @Builder.Default
        boolean forceFeeding = SettingReader.readBoolean("togafexpairt.feeding.force", false);
    }
}
