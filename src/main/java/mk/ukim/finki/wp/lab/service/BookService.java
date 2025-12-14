package mk.ukim.finki.wp.lab.service;
import mk.ukim.finki.wp.lab.model.Genre;
import mk.ukim.finki.wp.lab.model.Book;

import java.util.List;

public interface BookService {
    List<Book> listAll();
    List<Book> searchBooks(Genre genre);

    Book findBook(Long id);
    List<Book> findBooksByAuthorId(Long authorId);
    Book add(String title, Genre genre, Double averageRating, Long authorId);
    Book update(Long id, String title, Genre genre, Double averageRating, Long authorId);

    void delete(Long id);

    void updateLikes(Book book);

    List<Book> listByGenre(Genre genre);
}
