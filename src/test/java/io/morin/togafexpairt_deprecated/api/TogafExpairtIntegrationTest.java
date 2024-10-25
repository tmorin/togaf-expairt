package io.morin.togafexpairt_deprecated.api;

import io.morin.togafexpairt_deprecated.BaseIntegrationTest;
import java.io.ByteArrayOutputStream;
import java.util.ServiceLoader;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
class TogafExpairtIntegrationTest extends BaseIntegrationTest {

    TogafExpairt togafExpairt;

    @BeforeEach
    public void setUp() {
        // create the TogafExpairt
        togafExpairt = ServiceLoader.load(TogafExpairtFactory.class)
            .findFirst()
            .orElseThrow()
            .create(TogafExpairt.Settings.builder().build());
    }

    @Test
    void shouldIndex() {
        Assertions.assertDoesNotThrow(() -> togafExpairt.feed());
    }

    @Test
    @SneakyThrows
    void shouldReply() {
        try (val byteArrayOutputStream = new ByteArrayOutputStream()) {
            val streamMessageCommand = StreamMessageCommand.builder()
                .prompt("Provide a simple definition of architecture.")
                .outputStream(byteArrayOutputStream)
                .build();
            togafExpairt.execute(streamMessageCommand);
            val result = byteArrayOutputStream.toString();
            Assertions.assertNotNull(result);
        }
    }
}
