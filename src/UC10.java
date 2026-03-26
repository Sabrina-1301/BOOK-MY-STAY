
import java.util.*;

// Booking Class
class Booking {
    int bookingId;
    String guestName;
    String roomType;
    int roomId;
    boolean isActive;

    Booking(int bookingId, String guestName, String roomType, int roomId) {
        this.bookingId = bookingId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.isActive = true;
    }
}

// Inventory Class
class Inventory {
    static Map<String, Integer> rooms = new HashMap<>();

    static {
        rooms.put("Single", 5);
        rooms.put("Double", 3);
    }

    static void increment(String roomType) {
        rooms.put(roomType, rooms.getOrDefault(roomType, 0) + 1);
    }
}

// Custom Exception
class CancellationException extends Exception {
    CancellationException(String msg) {
        super(msg);
    }
}

// Cancellation Service
class CancellationService {

    static List<Integer> rollbackLog = new ArrayList<>();

    static void cancelBooking(Booking booking) throws CancellationException {

        // Validate booking
        if (booking == null || !booking.isActive) {
            throw new CancellationException("Booking not found or already cancelled.");
        }

        // Record rollback data
        rollbackLog.add(booking.roomId);

        // Restore inventory
        Inventory.increment(booking.roomType);

        // Update booking status
        booking.isActive = false;

        // Update history (simple print simulation)
        System.out.println("Booking ID " + booking.bookingId + " cancelled successfully.");
    }
}

// Main Class
public class UC10 {

    public static void main(String[] args) {

        // Existing booking
        Booking booking = new Booking(101, "John", "Single", 1);

        try {
            // Guest initiates cancellation
            CancellationService.cancelBooking(booking);

            // Show updated inventory
            System.out.println("Updated Inventory: " + Inventory.rooms);

        } catch (CancellationException e) {
            System.out.println("Cancellation Failed: " + e.getMessage());
        }

        // System continues safely
        System.out.println("System running normally...");
    }
}