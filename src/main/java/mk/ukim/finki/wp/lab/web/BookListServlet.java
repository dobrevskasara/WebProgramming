package mk.ukim.finki.wp.lab.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import mk.ukim.finki.wp.lab.model.Book;
import mk.ukim.finki.wp.lab.service.BookService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "bookListServlet", urlPatterns = "/")
@Component
public class BookListServlet extends HttpServlet {

    private final BookService bookService;

    public BookListServlet(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void init() throws ServletException {
        // овозможува Spring injection во servlet
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Book> books = bookService.listAll();

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html><head><meta charset='utf-8'><title>Book Reservation</title></head><body>");
        out.println("<h1>Welcome to our Book Reservation App</h1>");
        out.println("<form method='POST' action='/bookReservation'>");

        out.println("<h2>Choose a book:</h2>");
        for (Book b : books) {
            out.printf(
                    "<div class='book-option'><input type='radio' name='bookTitle' value='%s' required> Title: %s, Genre: %s, Rating: %.2f</div>",
                    b.getTitle(), b.getTitle(), b.getGenre(), b.getAverageRating()
            );
        }

        out.println("<h2>Enter your information:</h2>");
        out.println("Name: <input type='text' name='readerName' required><br/>");
        out.println("Address: <input type='text' name='readerAddress' required><br/>");

        out.println("<h2>Choose number of copies:</h2>");
        out.println("<input type='number' name='numCopies' min='1' max='10' required><br/><br/>");

        out.println("<input type='submit' value='Reserve Book'>");
        out.println("</form>");
        out.println("</body></html>");
    }
}
