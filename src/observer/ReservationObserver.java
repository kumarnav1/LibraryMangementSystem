package observer;

public interface ReservationObserver {
    void onBookAvailable(ReservationEvent event);
}
