package de.bach.spring.crud;

import de.bach.spring.crud.model.Book;
import de.bach.spring.crud.repository.BookRepository;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SuppressWarnings("PMD.UseUtilityClass")
@SpringBootApplication
@RequiredArgsConstructor
public class MyBookCollectionApplication implements CommandLineRunner {

    private final BookRepository repository;

    public static void main(final String[] args) throws InterruptedException {
        Thread.sleep(100_000); // wait for db to be ready
        SpringApplication.run(MyBookCollectionApplication.class, args);
    }

    @Override
    public void run(final String... args) {
        repository.saveAll(List.of(
                new Book(null, "Spring Start Here", "9781617298691", "Java", "10/1/2021",
                        "finished reading", "5/5", BigDecimal.valueOf(28.39)),
                new Book(null, "Learning Angular", "9781839210662", "Angular", "9/1/2021",
                        "finished reading", "2/5", BigDecimal.valueOf(28.87)),
                new Book(null, "Angular", "9781617298691", "Java", "2/1/2022",
                        "not yet read", "?/5", BigDecimal.valueOf(39.90)),
                new Book(null, "Git", "9783836288453", "DevOps", "5/5/2022",
                        "not yet read", "?/5", BigDecimal.valueOf(39.90)),
                new Book(null, "Docker", "9783836286343", "DevOps", "9/1/2021",
                        "not yet read", "?/5", BigDecimal.valueOf(39.90))
        ));
    }
}
