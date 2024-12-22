package cs320mod5;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentTest {

    @BeforeEach
    void resetIdCounter() {
        Appointment.resetIdCounter(); // Reset the counter before each test to ensure predictable IDs
    }

    @Test
    void testAppointmentCreationWithValidData() {
        Date futureDate = new Date(System.currentTimeMillis() + 100000); // Future date
        Appointment appointment = new Appointment(futureDate, "Valid description");
        assertEquals("0000000001", appointment.getId());
        assertEquals(futureDate, appointment.getAppointmentDate());
        assertEquals("Valid description", appointment.getDescription());
    }

    @Test
    void testAppointmentCreationWithNullDate() {
        assertThrows(NullPointerException.class, () -> new Appointment(null, "Valid description"));
    }

    @Test
    void testAppointmentCreationWithPastDate() {
        Date pastDate = new Date(System.currentTimeMillis() - 100000); // Past date
        assertThrows(IllegalArgumentException.class, () -> new Appointment(pastDate, "Valid description"));
    }

    @Test
    void testAppointmentCreationWithNullDescription() {
        Date futureDate = new Date(System.currentTimeMillis() + 100000); // Future date
        assertThrows(NullPointerException.class, () -> new Appointment(futureDate, null));
    }

    @Test
    void testAppointmentCreationWithTooLongDescription() {
        Date futureDate = new Date(System.currentTimeMillis() + 100000); // Future date
        String longDescription = "This description exceeds the maximum allowed length of fifty characters.";
        assertThrows(IllegalArgumentException.class, () -> new Appointment(futureDate, longDescription));
    }

    @Test
    void testAppointmentIdImmutability() {
        Appointment appointment = new Appointment(new Date(System.currentTimeMillis() + 100000), "Valid description");
        // Attempt to reflectively access a non-existent setId method
        assertThrows(NoSuchMethodException.class, () -> appointment.getClass().getMethod("setId", String.class));
    }

    @Test
    void testSetAppointmentDateWithValidDate() {
        Date futureDate = new Date(System.currentTimeMillis() + 100000); // Future date
        Date newFutureDate = new Date(System.currentTimeMillis() + 200000); // Another future date
        Appointment appointment = new Appointment(futureDate, "Valid description");

        appointment.setAppointmentDate(newFutureDate);
        assertEquals(newFutureDate, appointment.getAppointmentDate());
    }

    @Test
    void testSetAppointmentDateWithPastDate() {
        Date futureDate = new Date(System.currentTimeMillis() + 100000); // Future date
        Date pastDate = new Date(System.currentTimeMillis() - 100000); // Past date
        Appointment appointment = new Appointment(futureDate, "Valid description");

        assertThrows(IllegalArgumentException.class, () -> appointment.setAppointmentDate(pastDate));
    }

    @Test
    void testSetDescriptionWithValidData() {
        Date futureDate = new Date(System.currentTimeMillis() + 100000); // Future date
        Appointment appointment = new Appointment(futureDate, "Initial description.");

        appointment.setDescription("Updated description.");
        assertEquals("Updated description.", appointment.getDescription());
    }

    @Test
    void testSetDescriptionWithTooLongData() {
        Date futureDate = new Date(System.currentTimeMillis() + 100000); // Future date
        Appointment appointment = new Appointment(futureDate, "Initial description.");

        String longDescription = "This description exceeds the maximum allowed length of fifty characters.";
        assertThrows(IllegalArgumentException.class, () -> appointment.setDescription(longDescription));
    }

    @Test
    void testSetDescriptionWithNull() {
        Date futureDate = new Date(System.currentTimeMillis() + 100000); // Future date
        Appointment appointment = new Appointment(futureDate, "Initial description.");

        assertThrows(NullPointerException.class, () -> appointment.setDescription(null));
    }

    @Test
    void testSetDescriptionAtMaxLength() {
        Date futureDate = new Date(System.currentTimeMillis() + 100000); // Future date
        String maxLengthDesc = "This description is exactly fifty characters long!!!"; // 50 characters
        Appointment appointment = new Appointment(futureDate, "Initial description.");

        appointment.setDescription(maxLengthDesc);
        assertEquals(maxLengthDesc, appointment.getDescription());
    }

    @Test
    void testSetAppointmentDateAtBoundary() {
        // Assuming boundary includes dates that are not in the past
        Date now = new Date(); // Current date and time
        Appointment appointment = new Appointment(new Date(System.currentTimeMillis() + 100000), "Initial description.");

        appointment.setAppointmentDate(now); // Exactly current time, not in the past
        assertEquals(now, appointment.getAppointmentDate());
    }

    @Test
    void testAddAppointmentAtCurrentTime() {
        Date currentTime = new Date(); // Current date and time
        AppointmentService service = new AppointmentService();

        // Depending on your implementation, setting the appointment date to current time might be valid
        // or considered as in the past. Adjust the test accordingly.
        assertDoesNotThrow(() -> service.addAppointment(currentTime, "Appointment at current time."));

        Appointment appointment = service.getAppointment("0000000001");
        assertNotNull(appointment);
        assertEquals(currentTime, appointment.getAppointmentDate());
        assertEquals("Appointment at current time.", appointment.getDescription());
    }

    @Test
    void testDeleteOneOfMultipleAppointments() {
        AppointmentService service = new AppointmentService();
        Date futureDate1 = new Date(System.currentTimeMillis() + 100000);
        Date futureDate2 = new Date(System.currentTimeMillis() + 200000);
        Date futureDate3 = new Date(System.currentTimeMillis() + 300000);
        
        service.addAppointment(futureDate1, "Appointment One");
        service.addAppointment(futureDate2, "Appointment Two");
        service.addAppointment(futureDate3, "Appointment Three");
        
        service.deleteAppointment("0000000002");
        
        assertNotNull(service.getAppointment("0000000001"));
        assertNull(service.getAppointment("0000000002"));
        assertNotNull(service.getAppointment("0000000003"));
    }

    @Test
    void testUpdateAppointmentWithInvalidData() {
        AppointmentService service = new AppointmentService();
        Date futureDate = new Date(System.currentTimeMillis() + 100000);
        service.addAppointment(futureDate, "Initial description.");

        Appointment appointment = service.getAppointment("0000000001");
        Date pastDate = new Date(System.currentTimeMillis() - 100000); // Past date
        String longDescription = "This description exceeds the maximum allowed length of fifty characters.";

        // Attempt to set a past date
        assertThrows(IllegalArgumentException.class, () -> appointment.setAppointmentDate(pastDate));

        // Attempt to set an overly long description
        assertThrows(IllegalArgumentException.class, () -> appointment.setDescription(longDescription));
    }

    @Test
    void testAddAppointmentEnsuresUniqueIds() {
        AppointmentService service = new AppointmentService();
        Date futureDate1 = new Date(System.currentTimeMillis() + 100000);
        Date futureDate2 = new Date(System.currentTimeMillis() + 200000);

        service.addAppointment(futureDate1, "Appointment One");
        service.addAppointment(futureDate2, "Appointment Two");

        Appointment appointment1 = service.getAppointment("0000000001");
        Appointment appointment2 = service.getAppointment("0000000002");

        assertNotNull(appointment1);
        assertNotNull(appointment2);
        assertNotEquals(appointment1.getId(), appointment2.getId());
    }

    @Test
    void testEdgeCaseDescriptionLength() {
        Date futureDate = new Date(System.currentTimeMillis() + 100000); // Future date
        String validDesc = "This description is exactly fifty characters long!!!"; // 50 characters
        Appointment appointment = new Appointment(futureDate, validDesc);

        assertEquals(validDesc, appointment.getDescription());
    }

    @Test
    void testIdGenerationLimit() {
        Appointment.setIdCounterForTesting(999999999); // Set ID counter close to max

        // Create an appointment with the last valid ID
        Appointment appointment1 = new Appointment(new Date(System.currentTimeMillis() + 100000), "Last valid appointment");
        assertEquals("9999999999", appointment1.getId());

        // Attempting to create another appointment should throw an exception
        assertThrows(IllegalStateException.class, () -> 
            new Appointment(new Date(System.currentTimeMillis() + 100000), "Exceeding appointment")
        );
    }
}
