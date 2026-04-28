package de.bach.spring.crud.exception;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import de.bach.spring.crud.repository.BookRepository;
import de.bach.spring.crud.service.BookService;
import org.junit.jupiter.api.Test;

class ExceptionTest {

    @Test
    void bookNotFound() {
        BookRepository repository = mock(BookRepository.class);
        BookService service = new BookService(repository);
        long bookId = 2L;

        assertThrows(BookNotFoundException.class,
                () -> service.findBookById(bookId)
        );
    }
}