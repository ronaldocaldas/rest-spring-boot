package br.com.ronaldo.service;

import br.com.ronaldo.controllers.PersonController;
import br.com.ronaldo.data.dto.PersonDTO;
import br.com.ronaldo.exception.ResourceNotFoundException;
import br.com.ronaldo.model.Person;
import br.com.ronaldo.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.ronaldo.mapper.ObjectMapper.parseListObjects;
import static br.com.ronaldo.mapper.ObjectMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    private final Logger logger = LoggerFactory.getLogger(PersonService.class.getName());

    public PersonDTO findById(Long id) {
        logger.info("Finding one Person");
        var entity = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No record found for this  ID= " + id));
        var dto = parseObject(entity, PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }



    public List<PersonDTO> findAll() {
        logger.info("Finding all Person");
        var persons =  parseListObjects(personRepository.findAll(), PersonDTO.class);
        persons.forEach(this::addHateoasLinks);
        return persons;
    }

    public PersonDTO create(PersonDTO person) {
        logger.info("Creating one Person");
        var entity = parseObject(person, Person.class);
        var dto =  parseObject(personRepository.save(entity), PersonDTO.class);
        addHateoasLinks(dto);
        return dto;

    }

    public PersonDTO update(PersonDTO person) {
        logger.info("Updating one Person");
        Person entity = personRepository.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundException("No record found for this  ID= " + person.getId()));
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
        var dto =  parseObject(personRepository.save(entity), PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public void delete(Long id) {
        logger.info("Deleting one Person");
        personRepository.deleteById(id);
    }

    private void addHateoasLinks(PersonDTO dto) {
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
        dto.add(linkTo(methodOn(PersonController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(PersonController.class).update(dto)).withRel("update").withType("PUT"));
    }
}
