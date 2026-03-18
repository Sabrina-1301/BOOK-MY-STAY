import java.util.*;
import java.util.stream.Collectors;

// Represents the core Reservation (Simplified for UC8)
class Reservation {
    private String reservationId;
    private String guestName;
    private double totalAmount;

    public Reservation(String reservationId, String guestName, double totalAmount) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.totalAmount = totalAmount;
    }

    public String getReservationId() { return reservationId; }
    public String getGuestName() { return guestName; }
    public double getTotalAmount() { return totalAmount; }

    @Override
    public String toString() {
        return String.format("ID: %s | Guest: %s | Paid: $%.2f",
                reservationId, guestName, totalAmount);
    }
}

// Manages the historical records (The "Persistence Layer")
class BookingHistory {
    private List<Reservation> history = new ArrayList<>();

    public void recordBooking(Reservation res) {
        history.add(res); // Maintains insertion order
    }

    public List<Reservation> getAllBookings() {
        return new ArrayList<>(history); // Return copy to protect internal state
    }
}

// Handles data analysis (The "Business Intelligence Layer")
class BookingReportService {
    public void generateSummary(BookingHistory history) {
        List<Reservation> records = history.getAllBookings();

        double totalRevenue = records.stream()
                .mapToDouble(Reservation::getTotalAmount)
                .sum();

        System.out.println("======= OPERATIONAL REPORT =======");
        System.out.println("Total Bookings Processed: " + records.size());
        System.out.println("Total Revenue Generated: $" + totalRevenue);
        System.out.println("==================================");
    }

    public void listDetailedHistory(BookingHistory history) {
        System.out.println("Full Audit Trail (Chronological):");
        history.getAllBookings().forEach(System.out::println);
    }
}

public class UC8 {
    public static void main(String[] args) {
        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Simulate Flow: 1. Bookings are confirmed and added to history
        history.recordBooking(new Reservation("R001", "Alice Smith", 250.00));
        history.recordBooking(new Reservation("R002", "Bob Jones", 120.50));
        history.recordBooking(new Reservation("R003", "Charlie Brown", 400.00));

        // Simulate Flow: 2. Admin requests reports
        reportService.listDetailedHistory(history);
        System.out.println();
        reportService.generateSummary(history);
    }
}