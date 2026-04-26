package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Patron {
    private final String patronId;
    private String name;
    private String email;
    private String phone;
    private final List<BorrowRecord> borrowingHistory = new ArrayList<>();

    public Patron(String patronId, String name, String email, String phone) {
        this.patronId = Objects.requireNonNull(patronId, "Patron ID cannot be null");
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        this.email = email;
        this.phone = phone;
    }

    public String getPatronId() { return patronId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }

    public void updateInfo(String name, String email, String phone) {
        if (name != null) this.name = name;
        if (email != null) this.email = email;
        if (phone != null) this.phone = phone;
    }

    public void addBorrowRecord(BorrowRecord record) {
        borrowingHistory.add(record);
    }

    public void markReturned(String isbn, String branchId) {
        for (int i = borrowingHistory.size() - 1; i >= 0; i--) {
            BorrowRecord record = borrowingHistory.get(i);
            if (record.getIsbn().equals(isbn)  && !record.isReturned()) {
                record.markReturned(java.time.LocalDate.now());
                return;
            }
        }
    }

    @Override
    public String toString() {
        return "Patron{" +
                "patronId='" + patronId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public List<BorrowRecord> getBorrowingHistory() {
            return borrowingHistory;
    }
}
