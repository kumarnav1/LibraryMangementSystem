

import model.Book;
import model.Patron;
import service.LibraryService;

import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger(service.LibraryService.class.getName());
        logger.info("Starting Library Management System...");
        LibraryService library = new LibraryService();
        Book book1 = new Book("101", 2008, "Robert Martin", "Clean Code");
        Book book2 = new Book("102", 2018, "Joshua Bloch", "Effective Java");
        library.addBook(book1, 2);
        library.addBook(book2, 1);
        Patron p1 = new Patron("P1", "Navneet", "navneet@mail.com", "9999999999");
        Patron p2 = new Patron("P2", "Rahul", "rahul@mail.com", "8888888888");
        library.addPatron(p1);
        library.addPatron(p2);
        logger.info("Checking out all copies of book 101...");
        library.checkoutBook("P1", "101");
        library.checkoutBook("P2", "101");
        logger.info("Attempting checkout when no copies available...");
        boolean failedCheckout = library.checkoutBook("P1", "101");
        logger.info("Checkout success: " + failedCheckout);
        logger.info("Reserving book 101 for P1...");
        library.reserveBook("P1", "101");
        logger.info("Searching for 'Clean'...");
        System.out.println(library.searchByTitle("Clean"));
        logger.info("Returning book 101...");
        library.returnBook("P2", "101");
        logger.info("Attempting duplicate return...");
        boolean returnedAgain = library.returnBook("P2", "101");
        logger.info("Return success: " + returnedAgain);
        logger.info("System execution completed.");
    }
}
