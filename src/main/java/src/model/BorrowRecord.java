package model;

import java.time.LocalDate;

public class BorrowRecord {
    private final String isbn;
    private final String bookTitle;
    private final LocalDate checkoutDate;
    private final LocalDate dueDate;
    private LocalDate returnDate;

    public BorrowRecord(String isbn, String bookTitle, LocalDate checkoutDate, LocalDate dueDate) {
        this.isbn = isbn;
        this.bookTitle = bookTitle;
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
    }

    public String getIsbn() { return isbn; }
    public String getBookTitle() { return bookTitle; }
    public LocalDate getCheckoutDate() { return checkoutDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }

    public boolean isReturned() {
        return returnDate != null;
    }

    public void markReturned(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "BorrowRecord{" +
                "isbn='" + isbn + '\'' +
                ", bookTitle='" + bookTitle + '\'' +
                ", checkoutDate=" + checkoutDate +
                ", dueDate=" + dueDate +
                ", returnDate=" + returnDate +
                '}';
    }

}
