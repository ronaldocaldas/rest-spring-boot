package br.com.ronaldo.services;

import br.com.ronaldo.controllers.PersonController;
import br.com.ronaldo.data.dto.PersonDTO;
import br.com.ronaldo.exception.RequiredObjectIsNullException;
import br.com.ronaldo.exception.ResourceNotFoundException;
import br.com.ronaldo.model.Person;
import br.com.ronaldo.repository.PersonRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import static br.com.ronaldo.mapper.ObjectMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Service
public class PersonService {

    public static final String NO_RECORD_FOUND_FOR_THIS_ID = "No record found for this  ID= ";
    @Autowired
    PersonRepository personRepository;

    @Autowired
    PagedResourcesAssembler<PersonDTO> assembler;

    private final Logger logger = LoggerFactory.getLogger(PersonService.class.getName());

    public PersonDTO findById(Long id) {
        logger.info("Finding one Person");
        var entity = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(NO_RECORD_FOUND_FOR_THIS_ID + id));
        var dto = parseObject(entity, PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }



    public PagedModel<EntityModel<PersonDTO>> findAll(Pageable pageable) {
        logger.info("Finding all Person");

        var people = personRepository.findAll(pageable);
        Page<PersonDTO> peopleWithLinks = people.map(person -> {
            var dto =  parseObject(person, PersonDTO.class);
            addHateoasLinks(dto);
            return dto;
        });

        Link findAllLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonController.class)
                .findAll(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        String.valueOf(pageable.getSort())))
                .withSelfRel();

       return assembler.toModel(peopleWithLinks, findAllLink);
    }

    public PersonDTO create(PersonDTO person) {
        logger.info("Creating one Person");

        if(person == null) throw new RequiredObjectIsNullException();

        var entity = parseObject(person, Person.class);
        var dto =  parseObject(personRepository.save(entity), PersonDTO.class);
        addHateoasLinks(dto);
        return dto;

    }

    public PersonDTO update(PersonDTO person) {
        logger.info("Updating one Person");

        if(person == null) throw new RequiredObjectIsNullException();

        Person entity = personRepository.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundException(NO_RECORD_FOUND_FOR_THIS_ID + person.getId()));
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
        Person entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NO_RECORD_FOUND_FOR_THIS_ID + id));
        personRepository.delete(entity);
    }

    @Transactional
    public PersonDTO disablePerson(Long id) {
        logger.info("Deleting one Person");
        personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NO_RECORD_FOUND_FOR_THIS_ID + id));

        personRepository.disablePerson(id);
        var entity = personRepository.findById(id).get();
        var dto =  parseObject(entity, PersonDTO.class);
        addHateoasLinks(dto);

      return dto;
    }


    private void addHateoasLinks(PersonDTO dto) {
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
        dto.add(linkTo(methodOn(PersonController.class).findAll(1, 12, "asc")).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(PersonController.class).update(dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(PersonController.class).disablePerson(dto.getId())).withRel("disable").withType("PATH"));

    }
}
