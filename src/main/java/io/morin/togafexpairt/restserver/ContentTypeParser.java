package io.morin.togafexpairt.restserver;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

/**
 * This class is used to parse the content type of request or response.
 */
@Slf4j
@RequiredArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class ContentTypeParser {

    /**
     * Parses the content type from a string.
     *
     * @param value the value to parse
     * @return the content type
     */
    ContentType parse(String value) {
        if (value == null || value.isEmpty()) {
            log.trace("Content type is empty, using default");
            return ContentType.builder().build();
        }

        val parts = value.split(";");

        if (parts.length == 1) {
            log.trace("Content type has no charset, using default");
            return ContentType.builder().value(parts[0].trim()).build();
        }

        val charset = parts[1].split("=")[1].trim();
        log.trace("Content type has charset {}", charset);

        return ContentType.builder().value(parts[0].trim()).charset(charset).build();
    }
}
