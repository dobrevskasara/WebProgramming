package mk.ukim.finki.wp.lab.web.controller;

import jakarta.servlet.http.HttpSession;
import mk.ukim.finki.wp.lab.model.Book;
import mk.ukim.finki.wp.lab.model.Genre;
import mk.ukim.finki.wp.lab.repository.jpa.BookRepository;
import mk.ukim.finki.wp.lab.service.AuthorService;
import mk.ukim.finki.wp.lab.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping
    public String listBooks(@RequestParam(required = false) Genre genre,
                            Model model,
                            HttpSession session) {

        List<Book> books = (genre != null) ? bookService.listByGenre(genre) : bookService.listAll();

        List<Book> recentlyViewed = (List<Book>) session.getAttribute("recentlyViewed");
        if (recentlyViewed == null) {
            recentlyViewed = new ArrayList<>();
        }

        model.addAttribute("books", books);
        model.addAttribute("recentlyViewed", recentlyViewed);
        model.addAttribute("genres", Genre.values());
        model.addAttribute("selectedGenre", genre);

        return "listBooks";
    }

    @GetMapping("/view/{bookId}")
    public String viewBook(@PathVariable Long bookId,
                           HttpSession session,
                           Model model) {

        Book book = bookService.findBook(bookId);
        if (book == null) {
            return "redirect:/books?error=BookNotFound";
        }

        List<Book> recentlyViewed = (List<Book>) session.getAttribute("recentlyViewed");
        if (recentlyViewed == null) {
            recentlyViewed = new ArrayList<>();
        }

        // Отстрани ако веќе постои и додади на почеток
        recentlyViewed.removeIf(b -> b.getId().equals(bookId));
        recentlyViewed.add(0, book);

        // Ограничување на 5 најнови
        if (recentlyViewed.size() > 5) {
            recentlyViewed = recentlyViewed.subList(0, 5);
        }

        session.setAttribute("recentlyViewed", recentlyViewed);

        model.addAttribute("book", book);
        return "book-view"; // Thymeleaf страница за приказ на книга
    }

    // ADD BOOK FORM
    @GetMapping("/book-form")
    public String getAddBookForm(Model model) {
        model.addAttribute("bookId", null);
        model.addAttribute("title", "");
        model.addAttribute("genre", "");
        model.addAttribute("averageRating", 0.0);
        model.addAttribute("authorId", -1L);

        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("genres", Genre.values());

        return "book-form";
    }

    @GetMapping("/book-form/{bookId}")
    public String getEditBookForm(@PathVariable Long bookId, Model model) {
        Book book = bookService.findBook(bookId);
        if (book == null) {
            return "redirect:/books?error=BookNotFound";
        }

        model.addAttribute("bookId", book.getId());
        model.addAttribute("title", book.getTitle());
        model.addAttribute("genre", book.getGenre());
        model.addAttribute("averageRating", book.getAverageRating());
        model.addAttribute("authorId", book.getAuthor() != null ? book.getAuthor().getId() : -1);

        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("genres", Genre.values());

        return "book-form";
    }

    @PostMapping("/add")
    public String addBook(@RequestParam String title,
                          @RequestParam Genre genre,
                          @RequestParam Double averageRating,
                          @RequestParam Long authorId) {
        bookService.add(title, genre, averageRating, authorId);
        return "redirect:/books";
    }

    @PostMapping("/edit/{bookId}")
    public String editBook(@PathVariable Long bookId,
                           @RequestParam String title,
                           @RequestParam Genre genre,
                           @RequestParam Double averageRating,
                           @RequestParam Long authorId) {
        bookService.update(bookId, title, genre, averageRating, authorId);
        return "redirect:/books";
    }

    @PostMapping("/delete/{bookId}")
    public String deleteBook(@PathVariable Long bookId) {
        bookService.delete(bookId);
        return "redirect:/books";
    }


    @PostMapping("/reserve")
    public String reserveBook(@RequestParam String bookTitle,
                              @RequestParam String readerName,
                              @RequestParam String readerAddress,
                              @RequestParam int numCopies,
                              HttpSession session,
                              Model model) {

        Book book = bookService.listAll().stream()
                .filter(b -> b.getTitle().equals(bookTitle))
                .findFirst()
                .orElse(null);

        if (book != null) {
            List<Book> recentlyViewed = (List<Book>) session.getAttribute("recentlyViewed");
            if (recentlyViewed == null) recentlyViewed = new ArrayList<>();

            recentlyViewed.removeIf(b -> b.getId().equals(book.getId()));
            recentlyViewed.add(0, book);

            if (recentlyViewed.size() > 5) {
                recentlyViewed = recentlyViewed.subList(0, 5);
            }

            session.setAttribute("recentlyViewed", recentlyViewed);

            model.addAttribute("readerName", readerName);
            model.addAttribute("bookTitle", book.getTitle());
            model.addAttribute("numberOfCopies", numCopies);

            String fakeIp = (int)(Math.random()*256) + "." +
                    (int)(Math.random()*256) + "." +
                    (int)(Math.random()*256) + "." +
                    (int)(Math.random()*256);
            model.addAttribute("ipAddress", fakeIp);
        }

        return "reservationConfirmation";
    }
}
