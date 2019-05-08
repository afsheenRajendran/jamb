package com.ithellam.common.siren;

import java.util.UUID;
import javax.xml.bind.annotation.XmlElement;

@com.fasterxml.jackson.annotation.JsonTypeInfo(use = com.fasterxml.jackson.annotation.JsonTypeInfo.Id.CLASS, include = com.fasterxml.jackson.annotation.JsonTypeInfo.As.EXTERNAL_PROPERTY)
public abstract class SirenEntityPropertiesBase {
    @XmlElement(name = "_id")
    protected final UUID id;

    public SirenEntityPropertiesBase(final UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
