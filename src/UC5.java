import java.util.LinkedList;
import java.util.Queue;

// Class representing a guest's reservation request
class Reservation {
    private String guestName;
    private String roomType;
    private int nights;

    public Reservation(String guestName, String roomType, int nights) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.nights = nights;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getNights() {
        return nights;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "guestName='" + guestName + '\'' +
                ", roomType='" + roomType + '\'' +
                ", nights=" + nights +
                '}';
    }
}

// BookingRequestQueue manages incoming reservation requests in arrival order
class BookingRequestQueue {
    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    // Add a reservation request to the queue
    public void addRequest(Reservation reservation) {
        queue.add(reservation);
        System.out.println("Booking request added: " + reservation.getGuestName() +
                " for " + reservation.getRoomType());
    }

    // View queued requests without processing
    public void viewQueuedRequests() {
        System.out.println("\n===== Current Booking Queue =====");
        if (queue.isEmpty()) {
            System.out.println("No booking requests in the queue.");
        } else {
            for (Reservation r : queue) {
                System.out.println(r);
            }
        }
        System.out.println("=================================");
    }

    // Pop requests for processing (optional for UC6)
    public Reservation pollRequest() {
        return queue.poll();
    }
}

// Main application for UC5
public class UC5 {

    public static void main(String[] args) {

        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Simulate incoming booking requests
        Reservation r1 = new Reservation("Alice", "Single Room", 3);
        Reservation r2 = new Reservation("Bob", "Double Room", 2);
        Reservation r3 = new Reservation("Charlie", "Suite Room", 1);

        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        // Display current queued requests
        bookingQueue.viewQueuedRequests();

        // Note: Inventory is not updated at this stage (read-only queue)
    }
}