package io.morin.togafexpairt_deprecated.indexer;

import io.morin.togafexpairt_deprecated.BaseIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class IndexerCliIntegrationTest extends BaseIntegrationTest {

    @Test
    void shouldIndex() {
        Assertions.assertDoesNotThrow(() -> IndexerCli.main(new String[0]));
    }
}
