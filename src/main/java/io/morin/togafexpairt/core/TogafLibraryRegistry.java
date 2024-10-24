package io.morin.togafexpairt.core;

import java.net.URI;
import java.net.URL;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * The registry for the TOGAF library documents.
 */
@RequiredArgsConstructor
public enum TogafLibraryRegistry {
    /**
     * TOGAF® Fundamental Content - Introduction and Core Concepts.
     */
    TOGAF_FUNDAMENTAL_INTRODUCTION(
        "TOGAF® Fundamental Content - Introduction and Core Concepts",
        List.of(
            URI.create("https://pubs.opengroup.org/togaf-standard/introduction/index.html"),
            URI.create("https://pubs.opengroup.org/togaf-standard/introduction/chap01.html"),
            URI.create("https://pubs.opengroup.org/togaf-standard/introduction/chap02.html"),
            URI.create("https://pubs.opengroup.org/togaf-standard/introduction/chap03.html"),
            URI.create("https://pubs.opengroup.org/togaf-standard/introduction/chap04.html"),
            URI.create("https://pubs.opengroup.org/togaf-standard/introduction/apdxa.html"),
            URI.create("https://pubs.opengroup.org/togaf-standard/introduction/apdxb.html"),
            URI.create("https://pubs.opengroup.org/togaf-standard/introduction/apdxc.html")
        )
    ),
    /**
     * TOGAF® Fundamental Content - Architecture Development Method.
     */
    TOGAF_FUNDAMENTAL_ADM(
        "TOGAF® Fundamental Content - Architecture Development Method",
        List.of(
            URI.create("https://pubs.opengroup.org/togaf-standard/adm/index.html"),
            URI.create("https://pubs.opengroup.org/togaf-standard/adm/chap01.html"),
            URI.create("https://pubs.opengroup.org/togaf-standard/adm/chap02.html"),
            URI.create("https://pubs.opengroup.org/togaf-standard/adm/chap03.html"),
            URI.create("https://pubs.opengroup.org/togaf-standard/adm/chap04.html"),
            URI.create("https://pubs.opengroup.org/togaf-standard/adm/chap05.html"),
            URI.create("https://pubs.opengroup.org/togaf-standard/adm/chap06.html"),
            URI.create("https://pubs.opengroup.org/togaf-standard/adm/chap07.html"),
            URI.create("https://pubs.opengroup.org/togaf-standard/adm/chap08.html"),
            URI.create("https://pubs.opengroup.org/togaf-standard/adm/chap09.html"),
            URI.create("https://pubs.opengroup.org/togaf-standard/adm/chap10.html"),
            URI.create("https://pubs.opengroup.org/togaf-standard/adm/chap11.html"),
            URI.create("https://pubs.opengroup.org/togaf-standard/adm/chap12.html"),
            URI.create("https://pubs.opengroup.org/togaf-standard/adm/chap13.html")
        )
    );

    /**
     * The title.
     */
    @Getter
    private final String title;

    /**
     * The URIs.
     */
    private final List<URI> uris;

    /**
     * Gets the URLs.
     *
     * @return the URLs
     */
    public List<URL> getUrls() {
        return uris
            .stream()
            .map(uri -> {
                try {
                    return uri.toURL();
                } catch (Exception e) {
                    throw new IllegalStateException("Failed to convert URI to URL", e);
                }
            })
            .toList();
    }
}
