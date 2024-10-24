package io.morin.togafexpairt.adapter;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.output.Response;
import io.morin.togafexpairt.core.Chat;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

/**
 * An adapter for the {@link Chat} interface that leverage on the {@link Assistant} class.
 */
@Slf4j
@RequiredArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class Langchain4jChat implements Chat {

    @NonNull
    Assistant assistant;

    private static void waitForStreamingToComplete(@NonNull AtomicBoolean streamingDone) {
        log.info("waiting for streaming to complete");
        while (!streamingDone.get()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                log.debug("interrupted", e);
                Thread.currentThread().interrupt();
            }
        }
        log.info("streaming completed");
    }

    @Override
    public @NonNull String ask(@NonNull String message) {
        return assistant.chat(message);
    }

    @Override
    @SneakyThrows
    @SuppressWarnings("java:S112")
    public void ask(@NonNull String message, @NonNull OutputStream outputStream) {
        log.info("ask message {} with streaming", message);
        CompletableFuture.supplyAsync(() -> {
            log.info("start streaming");
            val streamingDone = new AtomicBoolean(false);
            assistant
                .stream(message)
                .onRetrieved(contents ->
                    contents.forEach(content -> log.info("text segment {}", content.textSegment().text()))
                )
                .onNext(OnNextConsumer.builder().outputStream(outputStream).build())
                .onComplete(
                    OnCompletedConsumer.builder().outputStream(outputStream).streamingDone(streamingDone).build()
                )
                .onError(OnErrorConsumer.builder().outputStream(outputStream).streamingDone(streamingDone).build())
                .start();

            waitForStreamingToComplete(streamingDone);

            return null;
        }).get();
        log.info("leave method");
    }

    @Slf4j
    @RequiredArgsConstructor
    @Builder(toBuilder = true)
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    static class OnNextConsumer implements Consumer<String> {

        @NonNull
        OutputStream outputStream;

        @Override
        public void accept(@NonNull String token) {
            try {
                log.debug("onNext {}", token);
                outputStream.write(token.getBytes());
                outputStream.flush();
            } catch (IOException e) {
                log.debug("failed to write to output stream: {}", e.getMessage());
                throw new IllegalStateException(e);
            }
        }
    }

    @Slf4j
    @RequiredArgsConstructor
    @Builder(toBuilder = true)
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    static class OnCompletedConsumer implements Consumer<Response<AiMessage>> {

        @NonNull
        OutputStream outputStream;

        @NonNull
        AtomicBoolean streamingDone;

        @Override
        public void accept(@NonNull Response<AiMessage> response) {
            log.info("onComplete {}", response);
            try {
                outputStream.close();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            } finally {
                streamingDone.set(true);
            }
        }
    }

    @Slf4j
    @RequiredArgsConstructor
    @Builder(toBuilder = true)
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    static class OnErrorConsumer implements Consumer<Throwable> {

        @NonNull
        OutputStream outputStream;

        @NonNull
        AtomicBoolean streamingDone;

        @Override
        public void accept(@NonNull Throwable error) {
            log.warn("onError", error);
            try {
                outputStream.close();
            } catch (IOException e) {
                log.warn("failed to close output stream", e);
            } finally {
                streamingDone.set(true);
            }
        }
    }
}
