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
        "The Open Group's TOGAF Standard introduction provides an overview of TOGAF as a framework for enterprise architecture, covering its purpose, scope, core principles, and structure to guide organizations in developing, managing, and governing their architecture effectively.",
        List.of(
            // URI.create("https://pubs.opengroup.org/togaf-standard/introduction/index.html"),
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
        "The TOGAF® Architecture Development Method (ADM) is a step-by-step approach to developing and managing an enterprise architecture, guiding organizations through a structured lifecycle from initial vision and architecture planning to implementation and ongoing governance.",
        List.of(
            // URI.create("https://pubs.opengroup.org/togaf-standard/adm/index.html"),
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
        "The TOGAF® ADM Techniques provide a set of methods and best practices to support and enhance each phase of the Architecture Development Method (ADM), including tools for stakeholder management, gap analysis, migration planning, and risk management to ensure robust architecture development and implementation.",
        List.of(
            // URI.create("https://pubs.opengroup.org/togaf-standard/adm-techniques/index.html"),
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
        "The \"Applying the ADM\" section of the TOGAF® Standard offers practical guidance and examples on tailoring and implementing the Architecture Development Method (ADM) to meet specific organizational needs, including adjustments for different industry sectors, architecture styles, and project requirements.",
        List.of(
            // URI.create("https://pubs.opengroup.org/togaf-standard/applying-the-adm/index.html"),
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
        "The TOGAF® Architecture Content Framework provides a structured approach to defining and organizing the outputs of the Architecture Development Method (ADM), including deliverables, artifacts, and building blocks that together form a comprehensive enterprise architecture.",
        List.of(
            // URI.create("https://pubs.opengroup.org/togaf-standard/architecture-content/index.html"),
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
        "The TOGAF® Enterprise Architecture Capability and Governance section outlines frameworks and best practices for establishing, managing, and governing an effective enterprise architecture capability, ensuring alignment with organizational strategy and supporting continuous improvement in architecture practices.",
        List.of(
            // URI.create("https://pubs.opengroup.org/togaf-standard/ea-capability-and-governance/index.html"),
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
     * The title.
     */
    @Getter
    private final String description;

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
