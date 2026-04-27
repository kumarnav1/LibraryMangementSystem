package service;

import observer.ReservationEvent;
import model.Book;
import model.Patron;
import observer.ReservationObserver;
import service.NotificationService;

import java.util.*;
import java.util.logging.Logger;

public class ReservationService implements ReservationObserver {
    private static final Logger logger = Logger.getLogger(ReservationService.class.getName());

    private final Map<String, Queue<String>> reservations = new HashMap<>();
    private final Map<String, Patron> patrons;
    private final NotificationService notificationService;

    public ReservationService(Map<String, Patron> patrons,
                              NotificationService notificationService) {
        this.patrons = patrons;
        this.notificationService = notificationService;
    }

    public void reserve(String patronId, String isbn) {
        reservations
                .computeIfAbsent(isbn, k -> new LinkedList<>())
                .offer(patronId);

        logger.info("Reservation added: " + patronId + " for ISBN " + isbn);
    }

    @Override
    public void onBookAvailable(ReservationEvent event) {
        String isbn = event.getIsbn();

        Queue<String> queue = reservations.get(isbn);

        if (queue == null || queue.isEmpty()) {
            logger.info("No reservations for ISBN " + isbn);
            return;
        }

        String nextPatronId = queue.poll();
        Patron patron = patrons.get(nextPatronId);

        if (patron != null) {
            notificationService.notify(
                    patron,
                    "Book available: " + event.getBook().getTitle()
            );
        }
    }
}
