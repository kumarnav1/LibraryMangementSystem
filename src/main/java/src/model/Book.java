package model;

public class Book {
    private String title;
    private String author;
    private final String isbn;
    private int publicationYear;

    public Book(String isbn, int publicationYear, String author, String title) {
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.author = author;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public String getAuthor() {
        return author;
    }

    public void updateDetails(String title, String author, int publicationYear) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
    }
}
