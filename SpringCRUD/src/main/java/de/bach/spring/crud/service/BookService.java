package de.bach.spring.crud.service;

import de.bach.spring.crud.exception.BookNotFoundException;
import de.bach.spring.crud.model.Book;
import de.bach.spring.crud.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;

    public Iterable<Book> findAllBooks() {
        return repository.findAll();
    }

    public Book findBookById(final long bookId) {
        return repository.findById(bookId). //NOPMD
                orElseThrow(() -> new BookNotFoundException("Book by id " + bookId + " was not found"));
    }

    public Book saveBook(final Book book) {
        return repository.save(book);
    }

    @Transactional
    public Book updateBook(final Book book, final long bookId) {
        book.setBookId(bookId);
        return this.saveBook(book);
    }

    public void deleteBook(final long bookId) {
        repository.deleteById(bookId);
    }

}
