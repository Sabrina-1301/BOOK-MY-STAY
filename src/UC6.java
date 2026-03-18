import java.util.*;

// Reservation class encapsulates guest booking request
class Reservation {
    private String guestName;
    private String roomType;
    private int nights;
    private String assignedRoomId; // Assigned after allocation

    public Reservation(String guestName, String roomType, int nights) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.nights = nights;
        this.assignedRoomId = null;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
    public int getNights() { return nights; }
    public String getAssignedRoomId() { return assignedRoomId; }

    public void assignRoom(String roomId) { this.assignedRoomId = roomId; }

    @Override
    public String toString() {
        return "Reservation{" +
                "guestName='" + guestName + '\'' +
                ", roomType='" + roomType + '\'' +
                ", nights=" + nights +
                ", assignedRoomId='" + assignedRoomId + '\'' +
                '}';
    }
}

// Centralized Room Inventory
class RoomInventory {
    private Map<String, Integer> inventory;
    private Map<String, Set<String>> assignedRooms; // Track assigned room IDs

    public RoomInventory() {
        inventory = new HashMap<>();
        assignedRooms = new HashMap<>();
    }

    public void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
        assignedRooms.put(roomType, new HashSet<>());
    }

    // Check availability
    public boolean isAvailable(String roomType) {
        return inventory.getOrDefault(roomType, 0) > 0;
    }

    // Allocate room and generate unique room ID
    public String allocateRoom(String roomType) {
        if (!isAvailable(roomType)) return null;

        int remaining = inventory.get(roomType) - 1;
        inventory.put(roomType, remaining);

        // Generate a unique room ID
        String roomId = roomType.substring(0, 2).toUpperCase() + "-" + (assignedRooms.get(roomType).size() + 1);
        assignedRooms.get(roomType).add(roomId);
        return roomId;
    }

    // Display current inventory
    public void displayInventory() {
        System.out.println("===== Current Inventory =====");
        for (String type : inventory.keySet()) {
            System.out.println(type + " - Available: " + inventory.get(type));
        }
        System.out.println("=============================");
    }
}

// BookingService processes reservation requests
class BookingService {
    private Queue<Reservation> bookingQueue;
    private RoomInventory inventory;

    public BookingService(Queue<Reservation> bookingQueue, RoomInventory inventory) {
        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
    }

    // Process all queued requests
    public void processBookings() {
        System.out.println("\nProcessing Booking Requests...\n");

        while (!bookingQueue.isEmpty()) {
            Reservation reservation = bookingQueue.poll();
            String roomType = reservation.getRoomType();

            if (inventory.isAvailable(roomType)) {
                String roomId = inventory.allocateRoom(roomType);
                reservation.assignRoom(roomId);
                System.out.println("Reservation confirmed: " + reservation.getGuestName() +
                        " -> Room ID: " + roomId + " (" + roomType + ")");
            } else {
                System.out.println("Reservation failed (no availability): " + reservation.getGuestName() +
                        " requested " + roomType);
            }
        }

        System.out.println("\nAll booking requests processed.");
    }
}

// Main Application for UC6
public class UC6 {

    public static void main(String[] args) {

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", 2);
        inventory.addRoomType("Double Room", 1);
        inventory.addRoomType("Suite Room", 1);

        inventory.displayInventory();

        // Initialize booking queue
        Queue<Reservation> bookingQueue = new LinkedList<>();
        bookingQueue.add(new Reservation("Alice", "Single Room", 2));
        bookingQueue.add(new Reservation("Bob", "Double Room", 3));
        bookingQueue.add(new Reservation("Charlie", "Single Room", 1));
        bookingQueue.add(new Reservation("Diana", "Suite Room", 2));
        bookingQueue.add(new Reservation("Eve", "Double Room", 1)); // Exceeds availability

        // Process bookings
        BookingService bookingService = new BookingService(bookingQueue, inventory);
        bookingService.processBookings();

        // Display inventory after processing
        inventory.displayInventory();
    }
}