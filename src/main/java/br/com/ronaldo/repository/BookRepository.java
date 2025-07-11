package br.com.ronaldo.repository;

import br.com.ronaldo.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {}