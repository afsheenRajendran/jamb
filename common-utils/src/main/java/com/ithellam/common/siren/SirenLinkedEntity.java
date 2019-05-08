package com.ithellam.common.siren;

import javax.xml.bind.annotation.XmlElement;

public class SirenLinkedEntity extends SirenEntityBase {
    @XmlElement
    private final String href;

    // required for JSON deserialization in Java client
    @SuppressWarnings("unused")
    private SirenLinkedEntity() {
        href = null;
    }

    public SirenLinkedEntity(final Class<? extends SirenEntityBase> sirenDtoClass, final String href) {
        sirenClass = computeSirenClass(sirenDtoClass);
        this.href = href;
    }
}
