package cs320mod3;

import java.util.HashMap;
import java.util.Map;

public class ContactService {
    private final Map<String, Contact> contacts;
    private static int idCounter = 1;

    public ContactService() {
        this.contacts = new HashMap<>();
    }
    
    private String generateUniqueId() {
        if (idCounter > 999999999) {
            throw new IllegalStateException("ID limit exceeded");
        }
        return String.format("%010d", idCounter++); // Add zero padding
    }

    public void addContact(String firstName, String lastName, String phone, String address) {
        String id = generateUniqueId();
        Contact contact = new Contact(id, firstName, lastName, phone, address);
        contacts.put(id, contact);
    }

    public void deleteContact(String id) {
        if (!contacts.containsKey(id)) {
            throw new IllegalArgumentException("No contact found with ID " + id);
        }
        contacts.remove(id);
    }

    public void updateContact(String id, String firstName, String lastName, String phone, String address) {
        Contact contact = contacts.get(id);
        if (contact == null) {
            throw new IllegalArgumentException("No contact found with ID " + id);
        }

        // Update only fields that are not null
        if (firstName != null && !firstName.isEmpty()) {
            contact.setFirstName(firstName);
        }
        if (lastName != null && !lastName.isEmpty()) {
            contact.setLastName(lastName);
        }
        if (phone != null && !phone.isEmpty()) {
            contact.setPhone(phone);
        }
        if (address != null && !address.isEmpty()) {
            contact.setAddress(address);
        }
    }
    
    public Contact getContact(String id) {
        return contacts.get(id);
    }
    
    public static void resetIdCounter() {
        idCounter = 1; // Reset the counter
    }


}
