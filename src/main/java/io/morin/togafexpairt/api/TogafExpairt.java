package io.morin.togafexpairt.api;

import io.morin.togafexpairt.fwk.SettingReader;
import lombok.Builder;
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
     * @param sendMessageCommand the send message command
     */
    void execute(StreamMessageCommand sendMessageCommand);

    void feed();

    /**
     * The settings.
     */
    @Value
    @Builder(toBuilder = true)
    class Settings {

        /**
         * Force the feeding of TOGAF Library based on the TOGAF Standard: https://pubs.opengroup.org/togaf-standard.
         */
        boolean forceFeeding = SettingReader.readBoolean("togafexpairt.feeding.force", false);
    }
}
