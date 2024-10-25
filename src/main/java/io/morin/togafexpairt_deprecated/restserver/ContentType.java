package io.morin.togafexpairt_deprecated.restserver;

import java.nio.charset.Charset;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * This class represents the content type of request or response.
 */
@Value
@Builder(toBuilder = true)
class ContentType {

    /**
     * The value of the content type.
     */
    @NonNull
    @Builder.Default
    String value = "text/plain";

    /**
     * The charset of the content type.
     */
    @NonNull
    @Builder.Default
    String charset = Charset.defaultCharset().name();
}
