package cs320mod5;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentServiceTest {

    @BeforeEach
    void resetIdCounter() {
        Appointment.resetIdCounter(); // Reset ID counter for consistent test results
    }

    @Test
    void testAddAppointment() {
        AppointmentService service = new AppointmentService();
        Date futureDate = new Date(System.currentTimeMillis() + 100000); // Future date
        service.addAppointment(futureDate, "Valid description");

        Appointment appointment = service.getAppointment("0000000001");
        assertNotNull(appointment);
        assertEquals(futureDate, appointment.getAppointmentDate());
        assertEquals("Valid description", appointment.getDescription());
    }

    @Test
    void testAddMultipleAppointments() {
        AppointmentService service = new AppointmentService();
        Date futureDate1 = new Date(System.currentTimeMillis() + 100000);
        Date futureDate2 = new Date(System.currentTimeMillis() + 200000);
        Date futureDate3 = new Date(System.currentTimeMillis() + 300000);
        
        service.addAppointment(futureDate1, "Appointment One");
        service.addAppointment(futureDate2, "Appointment Two");
        service.addAppointment(futureDate3, "Appointment Three");
        
        Appointment appointment1 = service.getAppointment("0000000001");
        Appointment appointment2 = service.getAppointment("0000000002");
        Appointment appointment3 = service.getAppointment("0000000003");
        
        assertNotNull(appointment1);
        assertNotNull(appointment2);
        assertNotNull(appointment3);
        
        assertEquals("Appointment One", appointment1.getDescription());
        assertEquals("Appointment Two", appointment2.getDescription());
        assertEquals("Appointment Three", appointment3.getDescription());
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
    void testDeleteAppointment() {
        AppointmentService service = new AppointmentService();
        Date futureDate = new Date(System.currentTimeMillis() + 100000);
        service.addAppointment(futureDate, "Description to delete");

        service.deleteAppointment("0000000001");

        assertThrows(IllegalArgumentException.class, () -> service.getAppointment("0000000001"));
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
    void testDeleteAppointmentNotFound() {
        AppointmentService service = new AppointmentService();
        assertThrows(IllegalArgumentException.class, () -> service.deleteAppointment("0000000001"));
    }

    @Test
    void testGetAppointmentNotFound() {
        AppointmentService service = new AppointmentService();
        assertThrows(IllegalArgumentException.class, () -> service.getAppointment("0000000001"));
    }

    @Test
    void testUpdateAppointmentDateOnly() {
        AppointmentService service = new AppointmentService();
        Date futureDate = new Date(System.currentTimeMillis() + 100000);
        service.addAppointment(futureDate, "Initial description.");
        
        Appointment appointment = service.getAppointment("0000000001");
        Date newFutureDate = new Date(System.currentTimeMillis() + 200000);
        
        appointment.setAppointmentDate(newFutureDate);
        assertEquals(newFutureDate, appointment.getAppointmentDate());
        assertEquals("Initial description.", appointment.getDescription());
    }

    @Test
    void testUpdateAppointmentDescriptionOnly() {
        AppointmentService service = new AppointmentService();
        Date futureDate = new Date(System.currentTimeMillis() + 100000);
        service.addAppointment(futureDate, "Initial description.");
        
        Appointment appointment = service.getAppointment("0000000001");
        String newDescription = "Updated description.";
        
        appointment.setDescription(newDescription);
        assertEquals(futureDate, appointment.getAppointmentDate());
        assertEquals(newDescription, appointment.getDescription());
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
    void testAddAppointmentWithExactBoundaryDate() {
        Date exactBoundaryDate = new Date(System.currentTimeMillis()); // Current date and time
        AppointmentService service = new AppointmentService();
        
        // Depending on your implementation, this might be valid or invalid
        // Here, assuming it's valid
        assertDoesNotThrow(() -> service.addAppointment(exactBoundaryDate, "Boundary date appointment."));
        
        Appointment appointment = service.getAppointment("0000000001");
        assertNotNull(appointment);
        assertEquals(exactBoundaryDate, appointment.getAppointmentDate());
        assertEquals("Boundary date appointment.", appointment.getDescription());
    }

    @Test
    void testAddAppointmentWithPastDate() {
        AppointmentService service = new AppointmentService();
        Date pastDate = new Date(System.currentTimeMillis() - 100000); // Past date

        assertThrows(IllegalArgumentException.class, () -> service.addAppointment(pastDate, "Past date appointment."));
    }

    @Test
    void testAddAppointmentsWithDuplicateDescriptions() {
        AppointmentService service = new AppointmentService();
        Date futureDate1 = new Date(System.currentTimeMillis() + 100000);
        Date futureDate2 = new Date(System.currentTimeMillis() + 200000);
        
        service.addAppointment(futureDate1, "Duplicate description.");
        service.addAppointment(futureDate2, "Duplicate description.");
        
        Appointment appointment1 = service.getAppointment("0000000001");
        Appointment appointment2 = service.getAppointment("0000000002");
        
        assertNotNull(appointment1);
        assertNotNull(appointment2);
        assertEquals("Duplicate description.", appointment1.getDescription());
        assertEquals("Duplicate description.", appointment2.getDescription());
    }

    @Test
    void testDeleteAllAppointments() {
        AppointmentService service = new AppointmentService();
        Date futureDate1 = new Date(System.currentTimeMillis() + 100000);
        Date futureDate2 = new Date(System.currentTimeMillis() + 200000);
        
        service.addAppointment(futureDate1, "Appointment One");
        service.addAppointment(futureDate2, "Appointment Two");
        
        service.deleteAppointment("0000000001");
        service.deleteAppointment("0000000002");
        
        assertNull(service.getAppointment("0000000001"));
        assertNull(service.getAppointment("0000000002"));
    }

    @Test
    void testGetAppointmentWithInvalidIdFormat() {
        AppointmentService service = new AppointmentService();
        service.addAppointment(new Date(System.currentTimeMillis() + 100000), "Valid appointment.");
        
        // Assuming IDs should be exactly 10 digits, trying to retrieve with invalid ID
        assertThrows(IllegalArgumentException.class, () -> service.getAppointment("invalidID"));
    }

    @Test
    void testResetIdCounter() {
        AppointmentService service = new AppointmentService();
        service.addAppointment(new Date(System.currentTimeMillis() + 100000), "Appointment One");
        assertEquals("0000000001", service.getAppointment("0000000001").getId());
        
        AppointmentService.resetIdCounter();
        service.addAppointment(new Date(System.currentTimeMillis() + 200000), "Appointment Two");
        assertEquals("0000000001", service.getAppointment("0000000001").getId()); // After reset, first ID is reused
    }

    @Test
    void testAddAppointmentUntilIdLimit() {
        AppointmentService service = new AppointmentService();
        Appointment.setIdCounterForTesting(999999990); // Set ID counter close to max (999999999)
        
        // Add 9 appointments to reach the limit (assuming max is 9999999999 for 10-digit IDs)
        for (int i = 0; i < 9; i++) {
            service.addAppointment(new Date(System.currentTimeMillis() + 100000 + i * 1000), "Appointment " + (i + 1));
        }
        
        // Create an appointment with the last valid ID
        service.addAppointment(new Date(System.currentTimeMillis() + 200000), "Last valid appointment.");
        Appointment lastAppointment = service.getAppointment("9999999999");
        assertNotNull(lastAppointment);
        assertEquals("Last valid appointment.", lastAppointment.getDescription());

        // Attempting to create another appointment should throw an exception
        assertThrows(IllegalStateException.class, () -> 
            service.addAppointment(new Date(System.currentTimeMillis() + 300000), "Exceeding appointment.")
        );
    }
}
