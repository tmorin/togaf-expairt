package io.morin.togafexpairt.indexer;

import io.morin.togafexpairt.BaseIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class IndexerCliIntegrationTest extends BaseIntegrationTest {

    @Test
    void shouldIndex() {
        Assertions.assertDoesNotThrow(() -> IndexerCli.main(new String[0]));
    }
}
