import java.util.*;

// Booking Request
class BookingRequest {
    String guestName;

    BookingRequest(String guestName) {
        this.guestName = guestName;
    }
}

// Shared Inventory
class Inventory {
    private int availableRooms;

    Inventory(int rooms) {
        this.availableRooms = rooms;
    }

    // Critical Section (Synchronized)
    public synchronized boolean bookRoom(String guestName) {
        if (availableRooms > 0) {
            System.out.println(guestName + " is booking a room...");
            availableRooms--;

            System.out.println("Booking confirmed for " + guestName +
                    ". Rooms left: " + availableRooms);
            return true;
        } else {
            System.out.println("No rooms available for " + guestName);
            return false;
        }
    }
}

// Booking Processor (Thread)
class BookingProcessor extends Thread {
    private Queue<BookingRequest> queue;
    private Inventory inventory;

    BookingProcessor(Queue<BookingRequest> queue, Inventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    public void run() {
        while (true) {
            BookingRequest request;

            // Synchronize queue access
            synchronized (queue) {
                if (queue.isEmpty()) break;
                request = queue.poll();
            }

            // Process booking
            inventory.bookRoom(request.guestName);
        }
    }
}

// Main Class
public class UC11 {

    public static void main(String[] args) {

        // Shared queue
        Queue<BookingRequest> queue = new LinkedList<>();

        // Multiple guests submitting requests
        queue.add(new BookingRequest("Alice"));
        queue.add(new BookingRequest("Bob"));
        queue.add(new BookingRequest("Charlie"));
        queue.add(new BookingRequest("David"));

        // Only 2 rooms available
        Inventory inventory = new Inventory(2);

        // Multiple threads (simulating concurrent users)
        Thread t1 = new BookingProcessor(queue, inventory);
        Thread t2 = new BookingProcessor(queue, inventory);

        t1.start();
        t2.start();
    }
}