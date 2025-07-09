package br.com.ronaldo.service;

import br.com.ronaldo.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;
import java.util.stream.IntStream;

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

    public List<Person> findAll() {
        List<Person> people = new ArrayList<Person>();

        IntStream.rangeClosed(1, 5).forEach(i -> {
            Person person = new Person();
            person.setId(counter.incrementAndGet());
            person.setFirstName(List.of("Ronaldo", "Maria", "Carlos", "Ana", "João").get(i-1));
            person.setLastName(List.of("Caldas", "Silva", "Souza", "Ferreira", "Santos").get(i-1));
            person.setAddress(List.of("Florianópolis", "São Paulo", "Rio de Janeiro", "Belo Horizonte", "Curitiba").get(i-1));
            person.setGender(List.of("Male", "Female", "Male", "Female", "Male").get(i-1));
            people.add(person);
        });

        return people;
    }
}
