package service;

import model.Patron;
import service.NotificationService;

import java.util.logging.Logger;

public class ConsoleNotificationService implements NotificationService {
    private static final Logger logger = Logger.getLogger(ConsoleNotificationService.class.getName());

    @Override
    public void notify(Patron patron, String message) {
        logger.info("Notify " + patron.getName() + " <" + patron.getEmail() + ">: " + message);
    }
}
