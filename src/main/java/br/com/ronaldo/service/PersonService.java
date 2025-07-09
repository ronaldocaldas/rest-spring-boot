package br.com.ronaldo.service;

import br.com.ronaldo.data.dto.PersonDTO;
import br.com.ronaldo.exception.ResourceNotFoundException;
import br.com.ronaldo.model.Person;
import br.com.ronaldo.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static br.com.ronaldo.mapper.ObjectMapper.parseListObjects;
import static br.com.ronaldo.mapper.ObjectMapper.parseObject;


@Service
public class PersonService {

    private final AtomicLong counter = new AtomicLong();

    @Autowired
    PersonRepository personRepository;

    private Logger logger = LoggerFactory.getLogger(PersonService.class.getName());

    public PersonDTO findById(Long id) {
        logger.info("Finding one Person");
        var entity = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No record found for this  ID= " + id));
        return parseObject(entity, PersonDTO.class);

    }

    public List<PersonDTO> findAll() {
        logger.info("Finding all Person");
        return parseListObjects(personRepository.findAll(), PersonDTO.class);
    }

    public PersonDTO create(PersonDTO person) {
        logger.info("Creating one Person");
        var entity = parseObject(person, Person.class);
        return parseObject(personRepository.save(entity), PersonDTO.class);
    }

    public PersonDTO update(PersonDTO person) {
        logger.info("Updating one Person");
        Person entity = personRepository.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundException("No record found for this  ID= " + person.getId()));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return parseObject(personRepository.save(entity), PersonDTO.class);
    }

    public void delete(Long id) {
        logger.info("Deleting one Person");
        personRepository.deleteById(id);
    }
}
