package br.com.ronaldo.service;

import br.com.ronaldo.model.Person;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonService {

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = Logger.getLogger(PersonService.class.getName());

    public Person findById(String id) {
        logger.info("Finding one Person");
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Ronaldo");
        person.setLastName("Caldas");
        person.setAddress("Florianópolis");
        person.setGender("Male");
        return person;
    }
}
