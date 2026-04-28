package de.bach.spring.crud.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.bach.spring.crud.model.Book;
import de.bach.spring.crud.repository.BookRepository;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    private Book book;

    @Mock
    private BookRepository repository;

    @InjectMocks
    private BookService service;

    @BeforeEach
    void setUp() {
        book = new Book(null, "Spring Start Here", "9781617298691", "Java", "1/10/2021",
                "finished reading", "5/5", BigDecimal.valueOf(28.39));
    }

    @Test
    void findAllBooks() {
        service.findAllBooks();

        verify(repository).findAll();
    }

    @Test
    void saveBook() {
        when(repository.save(book)).thenReturn(book);

        Book savedBook = service.saveBook(book);

        assertThat(savedBook).isEqualTo(book);
        verify(repository).save(book);
    }

    @Test
    void updateBook() {
        long bookId = 2L;
        when(repository.save(book)).thenReturn(book);
        Book updatedBook = service.updateBook(book, bookId);

        assertThat(updatedBook.getBookId()).isEqualTo(bookId);
        verify(repository).save(book);
    }

    @Test
    void deleteBook() {
        long bookId = 2L;

        service.deleteBook(bookId);

        verify(repository).deleteById(bookId);
    }
}