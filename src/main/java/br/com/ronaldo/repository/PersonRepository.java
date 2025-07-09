package br.com.ronaldo.repository;

import br.com.ronaldo.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PersonRepository extends JpaRepository<Person, Long> {
}
