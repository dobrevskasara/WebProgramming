package mk.ukim.finki.wp.lab.web;

import mk.ukim.finki.wp.lab.model.BookReservation;
import mk.ukim.finki.wp.lab.service.BookReservationService;


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

@WebServlet(name = "BookReservationServlet", urlPatterns = "/bookReservation")
public class BookReservationServlet extends HttpServlet {

    private final BookReservationService reservationService;

    public BookReservationServlet(BookReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String bookTitle = req.getParameter("bookTitle");
        String readerName = req.getParameter("readerName");
        String readerAddress = req.getParameter("readerAddress");
        int numCopies = Integer.parseInt(req.getParameter("numCopies"));
        String clientIp = req.getRemoteAddr();

        BookReservation reservation = reservationService.placeReservation(bookTitle, readerName, readerAddress, numCopies);

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html><head><meta charset='UTF-8'><title>Reservation Confirmation</title></head><body>");
        out.println("<h1>Book Reservation - Confirmation</h1>");
        out.println("<table border='1'>");
        out.println("<tr><th colspan='2'>Your Reservation Status</th></tr>");
        out.println("<tr><td><b>Reader Name</b></td><td>" + reservation.getReaderName() + "</td></tr>");
        out.println("<tr><td><b>Client IP Address</b></td><td>" + clientIp + "</td></tr>");
        out.println("<tr><td><b>Reservation for Book</b></td><td>" + reservation.getBookTitle() + "</td></tr>");
        out.println("<tr><td><b>Number of copies</b></td><td>" + reservation.getNumberOfCopies() + "</td></tr>");
        out.println("</table>");
        out.println("</body></html>");
    }
}
