package br.com.ronaldo.integrationtests.dto.wrappers.json;

import br.com.ronaldo.integrationtests.dto.wrappers.json.BookEmbeddedDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class WrapperBookDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @JsonProperty("_embedded")
    private BookEmbeddedDTO embedded;

    public WrapperBookDTO() {}

    public BookEmbeddedDTO getEmbedded() {
        return embedded;
    }

    public void setEmbedded(BookEmbeddedDTO embedded) {
        this.embedded = embedded;
    }
}
