package cs320mod5;

import java.util.HashMap;
import java.util.Map;
import java.util.Date;

public class AppointmentService {

    private final Map<String, Appointment> appointments = new HashMap<>();

    public void addAppointment(Date appointmentDate, String description) {
        Appointment appointment = new Appointment(appointmentDate, description);
        appointments.put(appointment.getId(), appointment);
    }

    public void deleteAppointment(String id) {
        if (!appointments.containsKey(id)) {
            throw new IllegalArgumentException("No appointment found for ID: " + id);
        }
        appointments.remove(id);
    }

    public Appointment getAppointment(String id) {
    	if (!appointments.containsKey(id)) {
            throw new IllegalArgumentException("No appointment found for ID: " + id);
        }
        return appointments.get(id);
    }
}
