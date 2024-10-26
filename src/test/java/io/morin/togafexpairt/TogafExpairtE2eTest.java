package io.morin.togafexpairt;

import io.morin.togafexpairt.cli.IndexerCli;
import io.morin.togafexpairt.cli.RestClientCli;
import io.morin.togafexpairt.restserver.RestServer;
import java.io.ByteArrayInputStream;
import java.net.URI;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.*;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TogafExpairtE2eTest extends BaseIntegrationTest {

    @Test
    @Order(1)
    void shouldIndexAndQueryWithoutRouting() {
        System.setProperty("togafexpairt.restserver.port", "7001");
        System.setProperty("togafexpairt.rest_cli.prompt_url", "http://localhost:7001/prompt");
        System.setProperty("togafexpairt.langchain4j.query_routing_enabled", "false");

        startRestServer();
        waitForRestServer();
        startIndexerCli();
        startRestCli();
    }

    @Test
    @Order(2)
    void shouldIndexAndQueryWithRouting() {
        System.setProperty("togafexpairt.restserver.port", "7002");
        System.setProperty("togafexpairt.rest_cli.prompt_url", "http://localhost:7002/prompt");
        System.setProperty("togafexpairt.langchain4j.query_routing_enabled", "true");

        startRestServer();
        waitForRestServer();
        startIndexerCli();
        startRestCli();
    }

    private static void startRestCli() {
        val simulatedInput = "What is enterprise architecture?\nexit\n";
        val inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        RestClientCli.main(new String[0]);
    }

    private static void startIndexerCli() {
        Assertions.assertDoesNotThrow(() -> IndexerCli.main(new String[0]));
    }

    private static void waitForRestServer() {
        Awaitility.await()
            .until(() -> {
                try {
                    URI.create("http://localhost:7001/prompt").toURL().openConnection().connect();
                    return true;
                } catch (Exception e) {
                    log.error("Failed to check if the server is running", e);
                    return false;
                }
            });
    }

    private static void startRestServer() {
        try (val executorService = Executors.newSingleThreadExecutor()) {
            executorService.submit(() -> RestServer.main(new String[0]));
        }
    }
}
