package io.morin.togafexpairt.api;

import io.morin.togafexpairt.BaseIntegrationTest;
import java.io.ByteArrayOutputStream;
import java.util.ServiceLoader;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.*;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TogafExpairtIntegrationTest extends BaseIntegrationTest {

    @Test
    @Order(1)
    void shouldIndexWithoutRouting() {
        System.setProperty("togafexpairt.langchain4j.query_routing_enabled", "false");
        val togafExpairt = ServiceLoader.load(TogafExpairtFactory.class)
            .findFirst()
            .orElseThrow()
            .create(TogafExpairt.Settings.builder().build());
        Assertions.assertDoesNotThrow(() -> togafExpairt.execute(FeedCommand.builder().build()));
    }

    @Test
    @Order(2)
    void shouldIndexWithRouting() {
        System.setProperty("togafexpairt.langchain4j.query_routing_enabled", "true");
        val togafExpairt = ServiceLoader.load(TogafExpairtFactory.class)
            .findFirst()
            .orElseThrow()
            .create(TogafExpairt.Settings.builder().build());
        val feedCommand = FeedCommand.builder().build();
        Assertions.assertDoesNotThrow(() -> togafExpairt.execute(feedCommand));
    }

    @Test
    @Order(3)
    @SneakyThrows
    void shouldReply() {
        System.setProperty("togafexpairt.langchain4j.query_routing_enabled", "false");
        try (val byteArrayOutputStream = new ByteArrayOutputStream()) {
            val streamMessageCommand = StreamMessageCommand.builder()
                .prompt("Provide a simple definition of architecture.")
                .outputStream(byteArrayOutputStream)
                .build();
            val togafExpairt = ServiceLoader.load(TogafExpairtFactory.class)
                .findFirst()
                .orElseThrow()
                .create(TogafExpairt.Settings.builder().build());
            togafExpairt.execute(streamMessageCommand);
            val result = byteArrayOutputStream.toString();
            Assertions.assertNotNull(result);
        }
    }
}
