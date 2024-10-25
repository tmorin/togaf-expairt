package io.morin.togafexpairt;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

@Slf4j
public abstract class BaseIntegrationTest {

    @Container
    @SuppressWarnings("rawtypes")
    protected static final GenericContainer QADRANT = new GenericContainer(
        DockerImageName.parse("qdrant/qdrant:latest")
    )
        .withExposedPorts(6334)
        .waitingFor(Wait.forLogMessage(".*Qdrant gRPC listening on.*\\n", 1))
        .withFileSystemBind("volumes/qdrant_junit", "/qdrant/storage", BindMode.READ_WRITE);

    static {
        QADRANT.start();
    }

    @Container
    @SuppressWarnings("rawtypes")
    protected static final GenericContainer OLLAMA = new GenericContainer(DockerImageName.parse("ollama/ollama:latest"))
        .withExposedPorts(11434)
        .waitingFor(Wait.forLogMessage(".*Listening on.*\\n", 1))
        .withFileSystemBind("volumes/ollama", "/root/.ollama", BindMode.READ_WRITE);

    static {
        OLLAMA.start();
        try {
            log.info("Install llama3.2:1b");
            OLLAMA.execInContainer("ollama", "run", "llama3.2:1b");
        } catch (IOException e) {
            throw new IllegalStateException(e);
        } catch (InterruptedException e) {
            log.error("Interrupted", e);
        }
    }

    @BeforeAll
    @SuppressWarnings({ "unchecked" })
    static void beforeAll() {
        QADRANT.followOutput(new Slf4jLogConsumer(log));
        OLLAMA.followOutput(new Slf4jLogConsumer(log));

        // qdrant configuration
        System.setProperty("togafexpairt.qdrant.host", QADRANT.getHost());
        System.setProperty("togafexpairt.qdrant.port", String.valueOf(QADRANT.getFirstMappedPort()));

        // ollama configuration
        System.setProperty(
            "togafexpairt.ollama.base_url",
            String.format("http://%s:%d", OLLAMA.getHost(), OLLAMA.getFirstMappedPort())
        );

        // langchain4j configuration
        System.setProperty("togafexpairt.langchain4j.embedding_model", "MINI_LM");
        System.setProperty("togafexpairt.langchain4j.dimension", "384");
    }
}
