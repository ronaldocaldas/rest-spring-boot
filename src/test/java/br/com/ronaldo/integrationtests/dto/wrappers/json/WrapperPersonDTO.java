package br.com.ronaldo.integrationtests.dto.wrappers.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class WrapperPersonDTO implements Serializable {

    @JsonProperty("_embedded")
    private PersonEmbeddedDTO embeded;

    public WrapperPersonDTO(){}

    public PersonEmbeddedDTO getEmbeded() {
        return embeded;
    }

    public void setEmbeded(PersonEmbeddedDTO embeded) {
        this.embeded = embeded;
    }
}
