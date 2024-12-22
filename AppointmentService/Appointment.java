package cs320mod5;

import java.util.Date;
import java.util.Objects;

public class Appointment {

    private static int idCounter = 1; // Counter for generating unique IDs

    private final String id; // Unique ID, max 10 chars, non-updatable
    private Date appointmentDate; // Cannot be null or in the past
    private String description; // Max 50 chars, cannot be null

    // Constructor
    public Appointment(Date appointmentDate, String description) {
        this.id = validId(generateUniqueId()); // Nested ID gen, with validation
        this.appointmentDate = validateDate(appointmentDate);
        this.description = validateDescription(description);
    }
    
    private String validId(String id) {
        Objects.requireNonNull(id, "ID cannot be null");
        if (id.length() > 10) {
            throw new IllegalArgumentException("ID cannot be longer than 10 characters");
        }
        return id;
    }
    
    private static String generateUniqueId() {
        if (idCounter > 999999999) { // Not to exceed 10 characters
            throw new IllegalStateException("ID cannot be longer than 10 characters");
        }
        return String.format("%010d", idCounter++);
    }

    private Date validateDate(Date appointmentDate) {
        Objects.requireNonNull(appointmentDate, "Appointment date cannot be null");
        if (appointmentDate.before(new Date())) {
            throw new IllegalArgumentException("Appointment date cannot be in the past");
        }
        return appointmentDate;
    }

    private String validateDescription(String description) {
        Objects.requireNonNull(description, "Description cannot be null");
        if (description.length() > 50) {
            throw new IllegalArgumentException("Description cannot exceed 50 characters");
        }
        return description;
    }

    // Getter for ID
    public String getId() {
        return id;
    }

    // Getter and Setter for appointmentDate
    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = validateDate(appointmentDate);
    }

    // Getter and Setter for description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = validateDescription(description);
    }
    
 // Reset ID counter for testing purposes
    public static void resetIdCounter() {
        idCounter = 1; // Reset the counter
    }
    
    public static void setIdCounterForTesting(int value) {
        idCounter = value;
    }


}
