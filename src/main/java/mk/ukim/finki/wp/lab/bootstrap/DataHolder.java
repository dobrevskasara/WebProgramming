package mk.ukim.finki.wp.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.lab.model.Author;
import mk.ukim.finki.wp.lab.model.Book;
import mk.ukim.finki.wp.lab.model.BookReservation;
import mk.ukim.finki.wp.lab.model.Genre;
import mk.ukim.finki.wp.lab.repository.jpa.AuthorRepository;
import mk.ukim.finki.wp.lab.repository.jpa.BookRepository;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class DataHolder {
    public static List<Book> books = new ArrayList<>();
    public static List<BookReservation> reservations = new ArrayList<>();

    private final BookRepository bookRepository;

    public DataHolder(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PostConstruct
    public void init() {
        if(bookRepository.findAll().isEmpty()) {
            books = new ArrayList<>();
            books.add(new Book("Harry Potter", Genre.Fantasy, 5.0));
            books.add(new Book("Lord of the Rings", Genre.Fantasy, 3.2));
            books.add(new Book("Clean Code", Genre.Programming, 4.5));
            books.add(new Book("Effective Java", Genre.Programming, 4.8));
            books.add(new Book("Game of Thrones", Genre.Dystopian, 4.2));
            books.add(new Book("Spring in Action", Genre.Programming, 3.7));
            books.add(new Book("The Hobbit", Genre.Fantasy, 4.6));
            books.add(new Book("Dune", Genre.ScienceFiction, 4.3));
            books.add(new Book("The Hunger Games", Genre.Dystopian, 4.9));
            books.add(new Book("Head First Java", Genre.Programming, 4.5));
            bookRepository.saveAll(books);
       }
    }
}
