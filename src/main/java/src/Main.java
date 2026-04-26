import model.Book;
import model.Patron;
import service.LibraryService;

import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger(service.LibraryService.class.getName());
        LibraryService library = new LibraryService();
        Book book1 = new Book("101", 2008, "Robert Martin", "Clean Code");
        Book book2 = new Book("102", 2008, "Joshua Bloch", "Effective Java");
        library.addBook(book1, 3);
        library.addBook(book2, 2);
        Patron p1 = new Patron("P1", "Navneet", "navneetkroy@gmail.com", "9999999999");
        library.addPatron(p1);
        logger.info("Attempting checkout for ISBN 101...");
        boolean checkout = library.checkoutBook("P1", "101");
        logger.info("Checkout success: " + checkout);
        logger.info("Attempting second checkout for ISBN 101");
        library.checkoutBook("P1", "101");
        logger.info("Searching for books with title 'Clean'");
        System.out.println(library.searchByTitle("Clean"));
        logger.info("Returning book ISBN 101...");
        boolean returned = library.returnBook("P1", "101");
        logger.info("Return success: " + returned);
        logger.info("Attempting duplicate return...");
        boolean returnedAgain = library.returnBook("P1", "101");
        logger.info("Return success: " + returnedAgain);

        logger.info("System execution completed.");
    }
}
