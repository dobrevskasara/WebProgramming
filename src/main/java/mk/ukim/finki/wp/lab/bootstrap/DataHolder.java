package mk.ukim.finki.wp.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.lab.model.Book;
import mk.ukim.finki.wp.lab.model.BookReservation;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DataHolder {
    public static List<Book> books = new ArrayList<>();
    public static List<BookReservation> reservations = new ArrayList<>();

    @PostConstruct
    public void init() {
        books.add(new Book("Harry Potter", "Fantasy", 4.9));
        books.add(new Book("Lord of the Rings", "Fantasy", 4.8));
        books.add(new Book("Clean Code", "Programming", 4.7));
        books.add(new Book("Effective Java", "Programming", 4.6));
        books.add(new Book("Game of Thrones", "Fantasy", 4.5));
        books.add(new Book("Spring in Action", "Programming", 4.4));
        books.add(new Book("The Hobbit", "Fantasy", 4.3));
        books.add(new Book("Java Concurrency", "Programming", 4.2));
        books.add(new Book("Design Patterns", "Programming", 4.1));
        books.add(new Book("Head First Java", "Programming", 4.0));
    }
}
