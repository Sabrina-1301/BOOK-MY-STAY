import java.util.HashMap;
import java.util.Map;

// Room class with details (extends previous UC2 idea)
class Room {
    private String roomType;
    private double pricePerNight;

    public Room(String roomType, double pricePerNight) {
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    // Display room info
    public void displayRoomInfo(int availableCount) {
        System.out.printf("%s - $%.2f per night - Available: %d\n",
                roomType, pricePerNight, availableCount);
    }
}

// Centralized inventory (from UC3)
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public Map<String, Integer> getInventorySnapshot() {
        return new HashMap<>(inventory); // Return copy for read-only access
    }
}

// RoomSearchService handles guest search requests
class RoomSearchService {
    private RoomInventory inventory;
    private Map<String, Room> rooms;

    public RoomSearchService(RoomInventory inventory, Map<String, Room> rooms) {
        this.inventory = inventory;
        this.rooms = rooms;
    }

    // Search and display only available rooms
    public void displayAvailableRooms() {
        System.out.println("===== Available Rooms =====");
        Map<String, Integer> snapshot = inventory.getInventorySnapshot();

        for (Map.Entry<String, Room> entry : rooms.entrySet()) {
            String type = entry.getKey();
            Room room = entry.getValue();
            int availableCount = snapshot.getOrDefault(type, 0);

            if (availableCount > 0) {
                room.displayRoomInfo(availableCount);
            }
        }
        System.out.println("===========================");
    }
}

// Main application for UC4
public class UC4{

    public static void main(String[] args) {

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", 10);
        inventory.addRoomType("Double Room", 5);
        inventory.addRoomType("Suite Room", 0); // unavailable

        // Initialize room objects with pricing
        Map<String, Room> rooms = new HashMap<>();
        rooms.put("Single Room", new Room("Single Room", 100.0));
        rooms.put("Double Room", new Room("Double Room", 180.0));
        rooms.put("Suite Room", new Room("Suite Room", 350.0));

        // Create search service
        RoomSearchService searchService = new RoomSearchService(inventory, rooms);

        // Guest searches for rooms (read-only)
        searchService.displayAvailableRooms();

        // Note: inventory state remains unchanged
    }
}
