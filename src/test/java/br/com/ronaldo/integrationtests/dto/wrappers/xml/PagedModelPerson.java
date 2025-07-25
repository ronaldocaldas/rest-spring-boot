package br.com.ronaldo.integrationtests.dto.wrappers.xml;

import br.com.ronaldo.integrationtests.dto.PersonDTO;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;

@XmlRootElement
public class PagedModelPerson implements Serializable {

    @XmlElement(name = "content")
    public List<PersonDTO> content;

    public PagedModelPerson() {
    }

    ;

    public List<PersonDTO> getContent() {
        return content;
    }

    public void setContent(List<PersonDTO> content) {
        this.content = content;
    }
}
