import java.time.LocalDate;

// Booking Class
class Booking {
    String guestName;
    String email;
    int guests;
    String checkIn;
    String checkOut;

    Booking(String guestName, String email, int guests, String checkIn, String checkOut) {
        this.guestName = guestName;
        this.email = email;
        this.guests = guests;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }
}

// Custom Exception
class ValidationException extends Exception {
    ValidationException(String message) {
        super(message);
    }
}

// Validator Class
class InvalidBookingValidator {

    static void validate(Booking b) throws ValidationException {

        if (b.guestName == null || b.guestName.isEmpty()) {
            throw new ValidationException("Guest name is required.");
        }

        if (b.email == null || !b.email.contains("@")) {
            throw new ValidationException("Invalid email address.");
        }

        if (b.guests <= 0) {
            throw new ValidationException("Guests must be greater than 0.");
        }

        LocalDate checkIn, checkOut;

        try {
            checkIn = LocalDate.parse(b.checkIn);
            checkOut = LocalDate.parse(b.checkOut);
        } catch (Exception e) {
            throw new ValidationException("Invalid date format (YYYY-MM-DD required).");
        }

        if (!checkOut.isAfter(checkIn)) {
            throw new ValidationException("Check-out must be after check-in.");
        }

        if (b.guests > 4) {
            throw new ValidationException("Maximum 4 guests allowed.");
        }
    }
}

// Main Class (UC9)
public class UC9 {

    public static void main(String[] args) {

        // Guest input
        Booking booking = new Booking(
                "Alice",
                "alice@mail.com",
                2,
                "2026-05-10",
                "2026-05-08" // Invalid case
        );

        try {
            // Validation step
            InvalidBookingValidator.validate(booking);

            // Success case
            System.out.println("Booking confirmed!");

        } catch (ValidationException e) {
            // Error handling
            System.out.println("Booking Failed: " + e.getMessage());
        }

        // System continues safely
        System.out.println("System is still running...");
    }
}