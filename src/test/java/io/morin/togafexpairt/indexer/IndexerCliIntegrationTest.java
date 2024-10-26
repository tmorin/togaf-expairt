package io.morin.togafexpairt.indexer;

import io.morin.togafexpairt.BaseIntegrationTest;
import io.morin.togafexpairt.cli.IndexerCli;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class IndexerCliIntegrationTest extends BaseIntegrationTest {

    @Test
    void shouldIndexWithoutRouting() {
        System.setProperty("togafexpairt.langchain4j.query_routing_enabled", "false");
        Assertions.assertDoesNotThrow(() -> IndexerCli.main(new String[0]));
    }

    @Test
    void shouldIndexWithRouting() {
        System.setProperty("togafexpairt.langchain4j.query_routing_enabled", "true");
        Assertions.assertDoesNotThrow(() -> IndexerCli.main(new String[0]));
    }
}
