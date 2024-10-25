package io.morin.togafexpairt.restserver;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

/**
 * This class is used to create an HTTP server.
 */
@Slf4j
@RequiredArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class HttpServerFactory {

    @NonNull
    @Builder.Default
    RestServer.RestServerSettings restServerSettings = RestServer.RestServerSettings.builder().build();

    @NonNull
    PromptHandler promptHandler;

    /**
     * Creates an HTTP server.
     *
     * @return the HTTP server
     */
    @SneakyThrows
    HttpServer create() {
        log.info("Creating REST server on {}:{}", restServerSettings.getHost(), restServerSettings.getPort());

        val httpServer = HttpServer.create();

        httpServer.createContext("/prompt", promptHandler);

        httpServer.bind(new InetSocketAddress(restServerSettings.getHost(), restServerSettings.getPort()), 0);

        return httpServer;
    }
}
