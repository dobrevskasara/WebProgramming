package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Author;
import mk.ukim.finki.wp.lab.model.Book;
import mk.ukim.finki.wp.lab.model.Genre;
import mk.ukim.finki.wp.lab.repository.jpa.AuthorRepository;
import mk.ukim.finki.wp.lab.repository.jpa.BookRepository;
import mk.ukim.finki.wp.lab.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import mk.ukim.finki.wp.lab.model.Genre;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Book> listAll() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> searchBooks(Genre genre) {
        return bookRepository.findAllByGenre(genre);
    }

    @Override
    public Book findBook(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public List<Book> findBooksByAuthorId(Long authorId) {
        return bookRepository.findAllByAuthor_Id(authorId);
    }

    @Override
    public Book add(String title, Genre genre, Double averageRating, Long authorId) {
        Author author = authorRepository.findById(authorId).orElse(null);
        Book book = new Book(title, genre, averageRating);
        book.setAuthor(author);
        return bookRepository.save(book);
    }

    @Override
    public Book update(Long id, String title, Genre genre, Double averageRating, Long authorId) {
        Book book = findBook(id);
        Author author = authorRepository.findById(authorId).orElse(null);
        book.setTitle(title);
        book.setGenre(genre);
        book.setAverageRating(averageRating);
        book.setAuthor(author);
        return bookRepository.save(book);
    }

//    @Override
//    public Book update(Long id, String title, genre genre, Double averageRating, Long authorId) {
//        Book book = findBook(id);
//        Author author = authorRepository.findById(authorId).orElse(null);
//        book.setTitle(title);
//        book.setGenre(genre);
//        book.setAverageRating(averageRating);
//        book.setAuthor(author);
//        return bookRepository.save(book);
//    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateLikes(Book book) {
            bookRepository.save(book);
        }

    @Override
    public List<Book> listByGenre(Genre genre) {
       return bookRepository.findAllByGenre(genre);
    }
}
