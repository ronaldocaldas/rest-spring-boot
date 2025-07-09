package br.com.ronaldo.service;

import br.com.ronaldo.exception.ResourceNotFoundException;
import br.com.ronaldo.model.Person;
import br.com.ronaldo.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


@Service
public class PersonService {

    private final AtomicLong counter = new AtomicLong();

    @Autowired
    PersonRepository personRepository;

    private Logger logger = LoggerFactory.getLogger(PersonService.class.getName());

    public Person findById(Long id) {
        logger.info("Finding one Person");
        return personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No record found for this  ID= " + id));
    }

    public List<Person> findAll() {
        logger.info("Finding all Person");
        return personRepository.findAll();
    }

    public Person create(Person person) {
        logger.info("Creating one Person");
        return personRepository.save(person);
    }

    public Person update(Person person) {
        logger.info("Updating one Person");
        Person entity = findById(person.getId());

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return personRepository.save(person);
    }

    public void delete(Long id) {
        logger.info("Deleting one Person");
        personRepository.deleteById(id);
    }
}
