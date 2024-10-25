package io.morin.togafexpairt_deprecated.adapter;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.service.UserMessage;

/**
 * A Language Chain 4J assistant.
 */
@SystemMessage(
    {
        "You are an TOGAF expert.",
        "Your goal is to help architect to better perform architecture work.",
        "Your guidance must be based only on provided information",
        "Your guidance must be based only on facts",
        "If you don't know the answer, you can say 'I don't know'.",
        "You can also say 'I don't understand' if you don't understand the question."
    }
)
public interface Assistant {
    /**
     * Chat with the assistant.
     *
     * @param chat the chat message.
     * @return the response.
     */
    String chat(@UserMessage String chat);

    /**
     * Stream with the assistant.
     *
     * @param chat the chat message.
     * @return the response.
     */
    TokenStream stream(@UserMessage String chat);
}