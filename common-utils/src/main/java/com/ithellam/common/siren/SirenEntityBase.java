package com.ithellam.common.siren;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.EXTERNAL_PROPERTY)
public abstract class SirenEntityBase {
    protected String sirenClass;
    @XmlElement
    private List<String> rel = Lists.newArrayList();

    protected String computeSirenClass(final Class<? extends SirenEntityBase> sirenClass) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, sirenClass.getSimpleName()).replace("-siren-dto", "");
    }

    public List<String> getRel() {
        return rel;
    }

    @XmlElement(name = "class")
    @JsonProperty("class")
    public String getSirenClass() {
        return sirenClass != null ? sirenClass : computeSirenClass(this.getClass());
    }

    public SirenEntityBase withRel(final List<String> rel) {
        this.rel = rel;
        return this;
    }

    public SirenEntityBase withRel(final String rel) {
        this.rel.add(rel);
        return this;
    }
}
