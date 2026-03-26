import java.io.*;
import java.util.*;

// Booking Class (Serializable)
class Booking implements Serializable {
    int id;
    String guestName;
    String roomType;

    Booking(int id, String guestName, String roomType) {
        this.id = id;
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Inventory Class (Serializable)
class Inventory implements Serializable {
    Map<String, Integer> rooms = new HashMap<>();

    Inventory() {
        rooms.put("Single", 2);
        rooms.put("Double", 1);
    }
}

// Wrapper Class to store system state
class SystemState implements Serializable {
    List<Booking> bookings;
    Inventory inventory;

    SystemState(List<Booking> bookings, Inventory inventory) {
        this.bookings = bookings;
        this.inventory = inventory;
    }
}

// Persistence Service
class PersistenceService {

    static void save(SystemState state, String fileName) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(fileName))) {

            oos.writeObject(state);
            System.out.println("System state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    static SystemState load(String fileName) {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(fileName))) {

            System.out.println("System state loaded successfully.");
            return (SystemState) ois.readObject();

        } catch (Exception e) {
            System.out.println("Error loading state: " + e.getMessage());
            return null;
        }
    }
}

// Main Class
public class UC12 {

    public static void main(String[] args) {

        String file = "system_state.dat";

        // -------- SYSTEM START --------
        SystemState state = PersistenceService.load(file);

        if (state == null) {
            // First run (no saved data)
            System.out.println("Initializing new system state...");
            Inventory inventory = new Inventory();
            List<Booking> bookings = new ArrayList<>();

            bookings.add(new Booking(1, "Alice", "Single"));
            bookings.add(new Booking(2, "Bob", "Double"));

            state = new SystemState(bookings, inventory);
        }

        // Display current state
        System.out.println("Current Bookings:");
        for (Booking b : state.bookings) {
            System.out.println(b.id + " - " + b.guestName + " - " + b.roomType);
        }

        System.out.println("Inventory: " + state.inventory.rooms);

        // -------- SYSTEM SHUTDOWN --------
        PersistenceService.save(state, file);

        System.out.println("System shutdown safely.");
    }
}
