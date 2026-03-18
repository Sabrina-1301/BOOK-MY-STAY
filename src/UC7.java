import java.util.*;

// Model for an individual Add-On Service
class AddOnService {
    private String serviceName;
    private double price;

    public AddOnService(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    public String getServiceName() { return serviceName; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return serviceName + " ($" + price + ")";
    }
}

// Manager to handle the One-to-Many relationship
class AddOnServiceManager {
    // Mapping Reservation ID -> List of Services
    private Map<String, List<AddOnService>> reservationToServicesMap = new HashMap<>();

    // Add a service to a specific reservation
    public void addService(String reservationId, AddOnService service) {
        reservationToServicesMap
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);
    }

    // Calculate total cost for a specific reservation's add-ons
    public double calculateTotalExtraCost(String reservationId) {
        List<AddOnService> services = reservationToServicesMap.getOrDefault(reservationId, Collections.emptyList());
        return services.stream()
                .mapToDouble(AddOnService::getPrice)
                .sum();
    }

    // Retrieve all services for a reservation
    public List<AddOnService> getServicesForReservation(String reservationId) {
        return reservationToServicesMap.getOrDefault(reservationId, Collections.emptyList());
    }
}

public class UC7 {
    public static void main(String[] args) {
        AddOnServiceManager serviceManager = new AddOnServiceManager();

        // Sample Reservation IDs
        String resId1 = "RES-101";
        String resId2 = "RES-102";

        // Define some services
        AddOnService breakfast = new AddOnService("Buffet Breakfast", 25.0);
        AddOnService wifi = new AddOnService("Premium WiFi", 10.0);
        AddOnService spa = new AddOnService("Spa Treatment", 80.0);

        // --- Flow Execution ---

        // 1. Guest for RES-101 selects Breakfast and WiFi
        serviceManager.addService(resId1, breakfast);
        serviceManager.addService(resId1, wifi);

        // 2. Guest for RES-102 selects Spa only
        serviceManager.addService(resId2, spa);

        // --- Output Results ---
        printReservationSummary(resId1, serviceManager);
        printReservationSummary(resId2, serviceManager);
    }

    private static void printReservationSummary(String resId, AddOnServiceManager manager) {
        System.out.println("Summary for Reservation: " + resId);
        List<AddOnService> services = manager.getServicesForReservation(resId);

        if (services.isEmpty()) {
            System.out.println(" - No add-on services selected.");
        } else {
            services.forEach(s -> System.out.println(" - " + s));
            double totalExtra = manager.calculateTotalExtraCost(resId);
            System.out.println("** Total Additional Cost: $" + totalExtra + " **");
        }
        System.out.println("-------------------------------------------");
    }
}