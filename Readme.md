# Library Management System 

Core Features
- Add, remove, and update books
- Search books by title, author, and ISBN
- Add and update patrons
- Checkout books
- Return books
- Track available copies (inventory)
- Maintain borrowing history per patron

+----------------------+
|        Book          |
+----------------------+
| - isbn               |
| - title              |
| - author             |
| - publicationYear    |
+----------------------+

+------------------------------+
|        BorrowRecord          |
+------------------------------+
| - isbn                       |
| - bookTitle                  |
| - checkoutDate               |
| - dueDate                    |
| - returnDate                 |
+------------------------------+
| + markReturned()             |
+------------------------------+

+------------------------------+
|           Patron             |
+------------------------------+
| - patronId                   |
| - name                       |
| - email                      |
| - phone                      |
| - borrowingHistory           |
+------------------------------+
| + addBorrowRecord()          |
| + markReturned()             |
+------------------------------+

              1
Patron -----------------------> BorrowRecord
(has many)

+--------------------------------------+
|         LibraryService               |
+--------------------------------------+
| - booksByIsbn                        |
| - patronsById                        |
| - availableCopies                    |
| - reservationService                 |
| - observers                          |
+--------------------------------------+
| + addBook()                          |
| + removeBook()                       |
| + searchByTitle()                    |
| + checkoutBook()                     |
| + returnBook()                       |
| + reserveBook()                      |
+--------------------------------------+

LibraryService -----> Book        (manages)
LibraryService -----> Patron      (manages)
LibraryService -----> ReservationService (uses)

+--------------------------------------+
|       ReservationObserver            |
+--------------------------------------+
| <<interface>>                        |
| + onBookAvailable()                  |
+--------------------------------------+

+--------------------------------------+
|        ReservationService            |
+--------------------------------------+
| - reservations (Map<ISBN, Queue>)    |
| - notificationService                |
+--------------------------------------+
| + reserve()                          |
| + onBookAvailable()                  |
+--------------------------------------+

ReservationService ----|> ReservationObserver

+--------------------------------------+
|        ReservationEvent              |
+--------------------------------------+
| - isbn                               |
| - book                               |
+--------------------------------------+

+--------------------------------------+
|       NotificationService            |
+--------------------------------------+
| <<interface>>                        |
| + notify()                           |
+--------------------------------------+

+--------------------------------------+
|   ConsoleNotificationService         |
+--------------------------------------+
| + notify()                           |
+--------------------------------------+

ConsoleNotificationService ----|> NotificationService

ReservationService -----> NotificationService (uses)
ReservationEvent -----> Book


Reservation System
- Reserve books when they are unavailable
- FIFO queue for reservations (first come, first served)
- Automatic notification when a reserved book becomes available


Notification System
- Uses **Observer Pattern**
- Decouples return logic from notification logic
- Easily extendable (email, SMS, push notifications)

---

### Observer Pattern
**Problem:** Notify users when reserved book becomes available

**Solution:**
- `LibraryService` → publishes event
- `ReservationService` → listens
- `NotificationService` → sends notification  
