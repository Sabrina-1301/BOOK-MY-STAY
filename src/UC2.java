// Abstract class representing a generic Room
abstract class Room {
    protected String roomType;
    protected int availableRooms;

    // Constructor
    public Room(String roomType, int availableRooms) {
        this.roomType = roomType;
        this.availableRooms = availableRooms;
    }

    // Abstract method to display room details
    public abstract void displayRoomInfo();
}

// Subclass representing a Single Room
class SingleRoom extends Room {

    public SingleRoom(int availableRooms) {
        super("Single Room", availableRooms);
    }

    @Override
    public void displayRoomInfo() {
        System.out.println(roomType + " - Available: " + availableRooms);
    }
}

// Subclass representing a Double Room
class DoubleRoom extends Room {

    public DoubleRoom(int availableRooms) {
        super("Double Room", availableRooms);
    }

    @Override
    public void displayRoomInfo() {
        System.out.println(roomType + " - Available: " + availableRooms);
    }
}

// Subclass representing a Suite
class SuiteRoom extends Room {

    public SuiteRoom(int availableRooms) {
        super("Suite Room", availableRooms);
    }

    @Override
    public void displayRoomInfo() {
        System.out.println(roomType + " - Available: " + availableRooms);
    }
}

// Main application for UC2
public class UC2 {
    public static void main(String[] args) {

        System.out.println("===== Hotel Room Availability =====");

        // Create room objects with static availability
        Room single = new SingleRoom(10);
        Room doubleRoom = new DoubleRoom(5);
        Room suite = new SuiteRoom(2);

        // Display room details
        single.displayRoomInfo();
        doubleRoom.displayRoomInfo();
        suite.displayRoomInfo();

        System.out.println("===== End of Room List =====");
    }
}