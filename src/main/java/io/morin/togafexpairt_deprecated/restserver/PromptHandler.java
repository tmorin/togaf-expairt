package io.morin.togafexpairt_deprecated.restserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import io.morin.togafexpairt_deprecated.api.SendMessageCommand;
import io.morin.togafexpairt_deprecated.api.StreamMessageCommand;
import io.morin.togafexpairt_deprecated.api.TogafExpairt;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class PromptHandler implements HttpHandler {

    @NonNull
    TogafExpairt togafExpairt;

    @NonNull
    ContentTypeParser contentTypeParser = ContentTypeParser.builder().build();

    @Override
    public void handle(HttpExchange exchange) {
        try {
            log.info("Handling request {}", exchange.getRequestURI());

            if (!"POST".equals(exchange.getRequestMethod())) {
                log.warn("Unsupported method {}", exchange.getRequestMethod());
                exchange.sendResponseHeaders(405, 0);
                return;
            }

            val contentType = contentTypeParser.parse(exchange.getRequestHeaders().getFirst("Content-Type"));
            log.debug("Content type is {}", contentType);

            if (!"text/plain".equals(contentType.getValue())) {
                log.warn("Unsupported content type {}", contentType);
                exchange.sendResponseHeaders(415, 0);
                try (
                    OutputStreamWriter writer = new OutputStreamWriter(
                        exchange.getResponseBody(),
                        contentType.getCharset()
                    )
                ) {
                    writer.write("Unsupported content type\n");
                }
                return;
            }

            val prompt = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            log.info("Prompt is {}", prompt);

            val command = SendMessageCommand.builder().prompt(prompt).build();
            log.debug("Command is {}", command);

            log.info("Sending response");
            exchange.sendResponseHeaders(200, 0);

            log.info("Streaming response");
            togafExpairt.execute(
                StreamMessageCommand.builder().prompt(prompt).outputStream(exchange.getResponseBody()).build()
            );

            log.info("Closing response");
            exchange.close();
        } catch (IOException e) {
            log.error("Failed to handle request", e);
        }
    }
}
