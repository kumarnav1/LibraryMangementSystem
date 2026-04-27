package observer;

import model.Book;

public class ReservationEvent {
    private final String isbn;
    private final Book book;

    public ReservationEvent(String isbn, Book book) {
        this.isbn = isbn;
        this.book = book;
    }

    public String getIsbn() { return isbn; }
    public Book getBook() { return book; }
}
