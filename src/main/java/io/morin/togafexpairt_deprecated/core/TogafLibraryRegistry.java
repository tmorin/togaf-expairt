package io.morin.togafexpairt_deprecated.core;

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
    ),
    /**
     * TOGAF® Fundamental Content - ADM Techniques.
     */
    TOGAF_FUNDAMENTAL_ADM_TECHNIQUES(
        "TOGAF® Fundamental Content - ADM Techniques",
        List.of(
            URI.create("https://pubs.opengroup.org/togaf-standard/adm-techniques/index.html"),
            URI.create("https://pubs.opengroup.org/togaf-standard/adm-techniques/chap01.html"),
            URI.create("https://pubs.opengroup.org/togaf-standard/adm-techniques/chap02.html"),
            URI.create("https://pubs.opengroup.org/togaf-standard/adm-techniques/chap03.html"),
            URI.create("https://pubs.opengroup.org/togaf-standard/adm-techniques/chap04.html"),
            URI.create("https://pubs.opengroup.org/togaf-standard/adm-techniques/chap05.html"),
            URI.create("https://pubs.opengroup.org/togaf-standard/adm-techniques/chap06.html"),
            URI.create("https://pubs.opengroup.org/togaf-standard/adm-techniques/chap07.html"),
            URI.create("https://pubs.opengroup.org/togaf-standard/adm-techniques/chap08.html"),
            URI.create("https://pubs.opengroup.org/togaf-standard/adm-techniques/chap09.html"),
            URI.create("https://pubs.opengroup.org/togaf-standard/adm-techniques/chap10.html")
        )
    ),
    /**
     * TOGAF® Fundamental Content - ADM Techniques.
     */
    TOGAF_FUNDAMENTAL_APPLYING_ADM(
        "TOGAF® Fundamental Content - Applying the ADM",
        List.of(
            URI.create("https://pubs.opengroup.org/togaf-standard/applying-the-adm/index.html"),
            URI.create("https://pubs.opengroup.org/togaf-standard/applying-the-adm/chap01.html"),
            URI.create("https://pubs.opengroup.org/togaf-standard/applying-the-adm/chap02.html"),
            URI.create("https://pubs.opengroup.org/togaf-standard/applying-the-adm/chap03.html"),
            URI.create("https://pubs.opengroup.org/togaf-standard/applying-the-adm/chap04.html")
        )
    ),
    /**
     * TOGAF® Fundamental Content - ADM Techniques.
     */
    TOGAF_FUNDAMENTAL_ARCHITECTURE_CONTENT(
        "TOGAF® Fundamental Content - Architecture Content",
        List.of(
            URI.create("https://pubs.opengroup.org/togaf-standard/architecture-content/index.html"),
            URI.create("https://pubs.opengroup.org/togaf-standard/architecture-content/chap01.html"),
            URI.create("https://pubs.opengroup.org/togaf-standard/architecture-content/chap02.html"),
            URI.create("https://pubs.opengroup.org/togaf-standard/architecture-content/chap03.html"),
            URI.create("https://pubs.opengroup.org/togaf-standard/architecture-content/chap04.html"),
            URI.create("https://pubs.opengroup.org/togaf-standard/architecture-content/chap05.html"),
            URI.create("https://pubs.opengroup.org/togaf-standard/architecture-content/chap06.html"),
            URI.create("https://pubs.opengroup.org/togaf-standard/architecture-content/chap07.html")
        )
    ),
    /**
     * TOGAF® Fundamental Content - ADM Techniques.
     */
    TOGAF_FUNDAMENTAL_CAPABILITY_GOVERNANCE(
        "TOGAF® Fundamental Content - Enterprise Architecture Capability and Governance",
        List.of(
            URI.create("https://pubs.opengroup.org/togaf-standard/ea-capability-and-governance/index.html"),
            URI.create("https://pubs.opengroup.org/togaf-standard/ea-capability-and-governance/chap01.html"),
            URI.create("https://pubs.opengroup.org/togaf-standard/ea-capability-and-governance/chap02.html"),
            URI.create("https://pubs.opengroup.org/togaf-standard/ea-capability-and-governance/chap03.html"),
            URI.create("https://pubs.opengroup.org/togaf-standard/ea-capability-and-governance/chap04.html"),
            URI.create("https://pubs.opengroup.org/togaf-standard/ea-capability-and-governance/chap05.html"),
            URI.create("https://pubs.opengroup.org/togaf-standard/ea-capability-and-governance/chap06.html")
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
