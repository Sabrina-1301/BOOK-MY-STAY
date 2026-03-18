import java.util.HashMap;
import java.util.Map;

// RoomInventory class manages centralized availability
class RoomInventory {

    // HashMap to store room type -> available count
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    // Register a room type with initial availability
    public void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // Get available rooms for a type
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability after booking or cancellation
    public void updateAvailability(String roomType, int newCount) {
        if (inventory.containsKey(roomType)) {
            inventory.put(roomType, newCount);
        } else {
            System.out.println("Room type not found: " + roomType);
        }
    }

    // Display current inventory
    public void displayInventory() {
        System.out.println("===== Current Room Inventory =====");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " - Available: " + entry.getValue());
        }
        System.out.println("=================================");
    }
}

// Main application for UC3
public class UC3 {

    public static void main(String[] args) {

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Register room types
        inventory.addRoomType("Single Room", 10);
        inventory.addRoomType("Double Room", 5);
        inventory.addRoomType("Suite Room", 2);

        // Display initial inventory
        inventory.displayInventory();

        // Simulate booking a room
        System.out.println("\nBooking 2 Single Rooms...");
        int singleAvailable = inventory.getAvailability("Single Room");
        inventory.updateAvailability("Single Room", singleAvailable - 2);

        // Display updated inventory
        inventory.displayInventory();

        // Simulate cancellation
        System.out.println("\nCancelling 1 Suite Room booking...");
        int suiteAvailable = inventory.getAvailability("Suite Room");
        inventory.updateAvailability("Suite Room", suiteAvailable + 1);

        // Display final inventory
        inventory.displayInventory();
    }
}