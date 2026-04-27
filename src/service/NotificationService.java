package service;

import model.Patron;

public interface NotificationService {
    void notify(Patron patron, String message);
}
