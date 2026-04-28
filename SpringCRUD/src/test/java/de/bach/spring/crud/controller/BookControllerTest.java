package de.bach.spring.crud.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.bach.spring.crud.model.Book;
import de.bach.spring.crud.repository.BookRepository;
import de.bach.spring.crud.service.BookService;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = BookController.class)
class BookControllerTest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    private Book book;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BookService service;

    @MockBean
    BookRepository repository;

    @BeforeEach
    void setUp() {
        book = new Book(null, "Spring Start Here", "9781617298691", "Java", "1/10/2021",
                "finished reading", "5/5", BigDecimal.valueOf(28.39));
    }

    @Test
    void getBooks() throws Exception {
        mockMvc.perform(get("/allBooks"));

        verify(service).findAllBooks();
    }

    @Test
    void addBook() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(book);

        mockMvc.perform(post("/addBook").contentType(APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andExpect(status().isOk());

        ArgumentCaptor<Book> argumentCaptor = ArgumentCaptor.forClass(Book.class);
        verify(service).saveBook(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).isEqualTo(book);
    }

    @Test
    void updateBook() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(book);

        long bookId = 2L;
        mockMvc.perform(put("/updateBook/" + bookId).contentType(APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andExpect(status().isOk());

        ArgumentCaptor<Book> argumentCaptor = ArgumentCaptor.forClass(Book.class);
        verify(service).updateBook(argumentCaptor.capture(), eq(bookId));
        assertThat(argumentCaptor.getValue()).isEqualTo(book);
    }

    @Test
    void deleteBook() throws Exception {
        long bookId = 2L;
        mockMvc.perform(delete("/deleteBook/" + bookId));

        verify(service).deleteBook(bookId);
    }
}