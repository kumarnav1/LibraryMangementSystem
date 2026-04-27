package service;

import observer.ReservationEvent;
import observer.ReservationObserver;
import model.Book;
import model.BorrowRecord;
import model.Patron;

import java.time.LocalDate;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class LibraryService {
    private static final Logger logger = Logger.getLogger(LibraryService.class.getName());

    private final Map<String, Book> booksByIsbn = new HashMap<>();
    private final Map<String, Patron> patronsById = new HashMap<>();
    private final Map<String, Integer> availableCopies = new HashMap<>();
    private final ReservationService reservationService;
    private final List<ReservationObserver> observers = new ArrayList<>();

    public LibraryService() {
        NotificationService notificationService = new ConsoleNotificationService();
        this.reservationService = new ReservationService(patronsById, notificationService);
        observers.add(reservationService);
    }

    public void addBook(Book book , int copies) {
        booksByIsbn.put(book.getIsbn(), book);
        availableCopies.put(book.getIsbn(), availableCopies.getOrDefault(book.getIsbn(), 0) + copies);
        logger.info("Book added - " + book + ", copies=" + copies);
    }

    public boolean removeBook(String isbn) {
        availableCopies.remove(isbn);
        Book removed = booksByIsbn.remove(isbn);
        logger.info("Book removed: " + removed);
        return removed != null;
    }

    public boolean updateBook(String isbn, String title, String author, int publicationYear, String genre) {
        Book book = booksByIsbn.get(isbn);
        if (book == null) return false;
        book.updateDetails(title, author, publicationYear);
        logger.info("Book updated: " + book);
        return true;
    }

    public Book findByIsbn(String isbn) {
        return booksByIsbn.get(isbn);
    }

    public List<Book> searchByTitle(String title) {
        String query = title.toLowerCase();
        return booksByIsbn.values().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(query))
                .sorted(Comparator.comparing(Book::getTitle)).collect(Collectors.toList());
    }

    public List<Book> searchByAuthor(String author) {
        String query = author.toLowerCase();
        return booksByIsbn.values().stream()
                .filter(book -> book.getAuthor().toLowerCase().contains(query))
                .sorted(Comparator.comparing(Book::getAuthor).thenComparing(Book::getTitle))
                .collect(Collectors.toList());
    }

    public void addPatron(Patron patron) {
        patronsById.put(patron.getPatronId(), patron);
        logger.info("Patron added: " + patron);
    }

    public boolean updatePatron(String patronId, String name, String email, String phone) {
        Patron patron = patronsById.get(patronId);
        if (patron == null) return false;
        patron.updateInfo(name, email, phone);
        logger.info("Patron updated: " + patron);
        return true;
    }

    public Optional<Patron> findPatron(String patronId) {
        return Optional.ofNullable(patronsById.get(patronId));
    }

    public boolean checkoutBook(String patronId, String isbn){
        Patron patron = patronsById.get(patronId);
        Book book = booksByIsbn.get(isbn);

        if (patron == null || book == null){
            logger.warning("Patron or book not found for checkout");
            return false;
        }

        int available = availableCopies.getOrDefault(isbn, 0);
        logger.warning("Checkout failed: No copies available for ISBN - " + isbn);
        if (available <= 0) return false;

        availableCopies.put(isbn, available - 1);

        BorrowRecord record = new BorrowRecord(
                isbn,
                book.getTitle(),
                LocalDate.now(),
                LocalDate.now().plusDays(14)
        );

        patron.addBorrowRecord(record);
        logger.info("Book checked out: ISBN=" + isbn + " to Patron=" + patronId);
        return true;
    }

    public boolean returnBook(String patronId, String isbn) {

        Patron patron = patronsById.get(patronId);
        if (patron == null) {
            logger.warning("Return failed: Patron not found - " + patronId);
            return false;
        }

        Book book = booksByIsbn.get(isbn);
        if (book == null) {
            logger.warning("Return failed: Book not found - " + isbn);
            return false;
        }

        int available = availableCopies.getOrDefault(isbn, 0);
        availableCopies.put(isbn, available + 1);

        List<BorrowRecord> history = patron.getBorrowingHistory();

        boolean updated = false;

        for (int i = history.size() - 1; i >= 0; i--) {
            BorrowRecord record = history.get(i);

            if (record.getIsbn().equals(isbn) && !record.isReturned()) {
                record.markReturned(LocalDate.now()); // sets returnDate = now
                updated = true;
                break;
            }
        }

        if (!updated) {
            logger.warning("Return failed: No active borrow record found for ISBN - " + isbn);
            return false;
        }

        logger.info("Book returned: ISBN=" + isbn + " from Patron=" + patronId);

        ReservationEvent event = new ReservationEvent(isbn, book);

        for (ReservationObserver observer : observers) {
            observer.onBookAvailable(event);
        }
        return true;
    }

    public List<BorrowRecord> getBorrowingHistory(String patronId) {
        Patron patron = patronsById.get(patronId);
        if (patron == null) {
            logger.warning("Patron not found: " + patronId);
            return Collections.emptyList();
        }
        return patron.getBorrowingHistory();
    }

    public boolean reserveBook(String patronId, String isbn) {
        if (!patronsById.containsKey(patronId) || !booksByIsbn.containsKey(isbn)) {
            return false;
        }

        int available = availableCopies.getOrDefault(isbn, 0);

        if (available > 0) {
            logger.info("Book is available, no need to reserve.");
            return false;
        }

        reservationService.reserve(patronId, isbn);
        return true;
    }
}
