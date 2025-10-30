package mk.ukim.finki.wp.lab.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.lab.model.Book;
import mk.ukim.finki.wp.lab.service.BookService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;


@WebServlet(name = "BookListServlet", urlPatterns = "/")
public class BookListServlet extends HttpServlet {

    private final BookService bookService;

    public BookListServlet(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        List<Book> books = bookService.listAll();

        out.println("<!DOCTYPE html>");
        out.println("<html><head><meta charset='UTF-8'><title>Book Reservation</title></head><body>");
        out.println("<h1>Welcome to our Book Reservation App</h1>");
        out.println("<form action='/bookReservation' method='POST'>");

        out.println("<h2>Choose a book:</h2>");
        for (Book book : books) {
            out.println("<div class='book-option'>");
            out.println("<input type='radio' name='bookTitle' value='" + book.getTitle() + "' required>");
            out.println("Title: " + book.getTitle() + ", Genre: " + book.getGenre() + ", Rating: " + book.getAverageRating());
            out.println("</div>");
        }

        out.println("<h2>Enter your information:</h2>");
        out.println("<label>Your Name:</label>");
        out.println("<input type='text' name='readerName' required><br/>");
        out.println("<label>Your Address:</label>");
        out.println("<input type='text' name='readerAddress' required><br/>");

        out.println("<h2>Choose number of copies:</h2>");
        out.println("<input type='number' name='numCopies' min='1' max='10' required><br/><br/>");

        out.println("<input type='submit' value='Reserve Book'>");
        out.println("</form>");
        out.println("</body></html>");
    }

    // За search (точка 7) можеш да додадеш doPost за филтрирање
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String text = req.getParameter("searchText");
        String ratingParam = req.getParameter("searchRating");
        Double rating = ratingParam != null && !ratingParam.isEmpty() ? Double.parseDouble(ratingParam) : 0.0;

        List<Book> books = bookService.searchBooks(text, rating);

        req.setAttribute("books", books);
        doGet(req, resp); // Прикажи ги филтрираните книги
    }
}
