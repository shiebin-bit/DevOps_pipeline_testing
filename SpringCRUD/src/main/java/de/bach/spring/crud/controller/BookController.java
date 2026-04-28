package de.bach.spring.crud.controller;

import de.bach.spring.crud.model.Book;
import de.bach.spring.crud.service.BookService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class BookController {

    @SuppressFBWarnings("EI_EXPOSE_REP2")
    private final BookService service;

    @GetMapping("/allBooks")
    public Iterable<Book> getBooks() {
        return this.service.findAllBooks();
    }

    @PostMapping("/addBook")
    public Book addBook(final @RequestBody Book book) {
        return this.service.saveBook(book);
    }

    @PutMapping("/updateBook/{bookId}")
    public Book updateBook(@PathVariable("bookId") final long bookId, final @RequestBody Book book) {
        return this.service.updateBook(book, bookId);
    }

    @DeleteMapping("/deleteBook/{bookId}")
    public void deleteBook(@PathVariable("bookId") final long bookId) {
        this.service.deleteBook(bookId);
    }

}
