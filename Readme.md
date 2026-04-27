# Library Management System 

Core Features
- Add, remove, and update books
- Search books by title, author, and ISBN
- Add and update patrons
- Checkout books
- Return books
- Track available copies (inventory)
- Maintain borrowing history per patron


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
