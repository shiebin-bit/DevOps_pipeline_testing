package de.bach.spring.crud.repository;

import de.bach.spring.crud.model.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {

}
