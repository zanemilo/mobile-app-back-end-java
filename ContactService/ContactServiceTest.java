package cs320mod3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class ContactServiceTest {
	
	@BeforeEach
	void resetIdCounter() {  // Resets ID increment in ContactService to achieve correct results
	    ContactService.resetIdCounter();
	}

    @Test
    void testAddContact() {
        ContactService service = new ContactService();
        service.addContact("John", "Doe", "1234567890", "123 Elm Street");

        Contact contact = service.getContact("0000000001");
        assertNotNull(contact);
        assertEquals("John", contact.getFirstName());
        assertEquals("Doe", contact.getLastName());
        assertEquals("1234567890", contact.getPhone());
        assertEquals("123 Elm Street", contact.getAddress());
    }

    @Test
    void testAddMultipleContacts() {
        ContactService service = new ContactService();
        service.addContact("John", "Doe", "1234567890", "123 Elm Street");
        service.addContact("Jane", "Smith", "0987654321", "456 Oak Avenue");

        Contact contact1 = service.getContact("0000000001");
        Contact contact2 = service.getContact("0000000002");

        assertNotNull(contact1);
        assertNotNull(contact2);
        assertEquals("John", contact1.getFirstName());
        assertEquals("Doe", contact1.getLastName());
        assertEquals("1234567890", contact1.getPhone());
        assertEquals("123 Elm Street", contact1.getAddress());

        assertEquals("Jane", contact2.getFirstName());
        assertEquals("Smith", contact2.getLastName());
        assertEquals("0987654321", contact2.getPhone());
        assertEquals("456 Oak Avenue", contact2.getAddress());
    }

    @Test
    void testAddContactUniqueId() {
        ContactService service = new ContactService();
        service.addContact("John", "Doe", "1234567890", "123 Elm Street");
        service.addContact("Jane", "Smith", "0987654321", "456 Oak Avenue");

        assertNotEquals(service.getContact("0000000001").getId(), service.getContact("0000000002").getId());
    }

    @Test
    void testAddContactWithEdgeCaseFieldLengths() {
        String firstName = "Johnathan"; // 10 characters
        String lastName = "DoeSmith12"; // 10 characters
        String phone = "0123456789"; // 10 digits
        String address = "1234567890 Elm Street, Apt 1"; // 30 characters
        
        ContactService service = new ContactService();
        service.addContact(firstName, lastName, phone, address);
        
        Contact contact = service.getContact("0000000001");
        assertNotNull(contact);
        assertEquals("0000000001", contact.getId());
        assertEquals(firstName, contact.getFirstName());
        assertEquals(lastName, contact.getLastName());
        assertEquals(phone, contact.getPhone());
        assertEquals(address, contact.getAddress());
    }

    @Test
    void testDeleteContact() {
        ContactService service = new ContactService();
        service.addContact("John", "Doe", "1234567890", "123 Elm Street");
        service.deleteContact("0000000001");

        assertNull(service.getContact("0000000001"));
    }

    @Test
    void testDeleteOneOfMultipleContacts() {
        ContactService service = new ContactService();
        service.addContact("John", "Doe", "1234567890", "123 Elm Street");
        service.addContact("Jane", "Smith", "0987654321", "456 Oak Avenue");
        
        service.deleteContact("0000000001");
        
        assertNull(service.getContact("0000000001"));
        assertNotNull(service.getContact("0000000002"));
        assertEquals("Jane", service.getContact("0000000002").getFirstName());
    }

    @Test
    void testDeleteContactNotFound() {
        ContactService service = new ContactService();
        assertThrows(IllegalArgumentException.class, () -> service.deleteContact("0000000001"));
    }

    @Test
    void testUpdateContact() {
        ContactService service = new ContactService();
        service.addContact("John", "Doe", "1234567890", "123 Elm Street");

        service.updateContact("0000000001", "Johnny", "Smith", "0987654321", "456 Oak Avenue");
        Contact updatedContact = service.getContact("0000000001");

        assertEquals("Johnny", updatedContact.getFirstName());
        assertEquals("Smith", updatedContact.getLastName());
        assertEquals("0987654321", updatedContact.getPhone());
        assertEquals("456 Oak Avenue", updatedContact.getAddress());
    }

    @Test
    void testUpdateContactFirstNameOnly() {
        ContactService service = new ContactService();
        service.addContact("John", "Doe", "1234567890", "123 Elm Street");
        
        service.updateContact("0000000001", "Johnny", null, null, null);
        Contact updatedContact = service.getContact("0000000001");
        
        assertEquals("Johnny", updatedContact.getFirstName());
        assertEquals("Doe", updatedContact.getLastName());
        assertEquals("1234567890", updatedContact.getPhone());
        assertEquals("123 Elm Street", updatedContact.getAddress());
    }

    @Test
    void testUpdateContactLastNameOnly() {
        ContactService service = new ContactService();
        service.addContact("John", "Doe", "1234567890", "123 Elm Street");
        
        service.updateContact("0000000001", null, "Smith", null, null);
        Contact updatedContact = service.getContact("0000000001");
        
        assertEquals("John", updatedContact.getFirstName());
        assertEquals("Smith", updatedContact.getLastName());
        assertEquals("1234567890", updatedContact.getPhone());
        assertEquals("123 Elm Street", updatedContact.getAddress());
    }

    @Test
    void testUpdateContactPhoneOnly() {
        ContactService service = new ContactService();
        service.addContact("John", "Doe", "1234567890", "123 Elm Street");
        
        service.updateContact("0000000001", null, null, "0987654321", null);
        Contact updatedContact = service.getContact("0000000001");
        
        assertEquals("John", updatedContact.getFirstName());
        assertEquals("Doe", updatedContact.getLastName());
        assertEquals("0987654321", updatedContact.getPhone());
        assertEquals("123 Elm Street", updatedContact.getAddress());
    }

    @Test
    void testUpdateContactAddressOnly() {
        ContactService service = new ContactService();
        service.addContact("John", "Doe", "1234567890", "123 Elm Street");
        
        service.updateContact("0000000001", null, null, null, "456 Oak Avenue");
        Contact updatedContact = service.getContact("0000000001");
        
        assertEquals("John", updatedContact.getFirstName());
        assertEquals("Doe", updatedContact.getLastName());
        assertEquals("1234567890", updatedContact.getPhone());
        assertEquals("456 Oak Avenue", updatedContact.getAddress());
    }

    @Test
    void testUpdateContactWithInvalidFirstName() {
        ContactService service = new ContactService();
        service.addContact("John", "Doe", "1234567890", "123 Elm Street");
        
        assertThrows(IllegalArgumentException.class, () -> service.updateContact("0000000001", "ThisNameIsWayTooLong", null, null, null));
    }

    @Test
    void testUpdateContactWithInvalidLastName() {
        ContactService service = new ContactService();
        service.addContact("John", "Doe", "1234567890", "123 Elm Street");
        
        assertThrows(IllegalArgumentException.class, () -> service.updateContact("0000000001", null, "ThisLastNameIsTooLong", null, null));
    }

    @Test
    void testUpdateContactWithInvalidPhone() {
        ContactService service = new ContactService();
        service.addContact("John", "Doe", "1234567890", "123 Elm Street");
        
        assertThrows(IllegalArgumentException.class, () -> service.updateContact("0000000001", null, null, "12345abcde", null));
    }

    @Test
    void testUpdateContactWithInvalidAddress() {
        ContactService service = new ContactService();
        service.addContact("John", "Doe", "1234567890", "123 Elm Street");
        
        assertThrows(IllegalArgumentException.class, () -> service.updateContact("0000000001", null, null, null, "This address is way too long and exceeds the maximum allowed length."));
    }

    @Test
    void testUpdateContactNotFound() {
        ContactService service = new ContactService();
        assertThrows(IllegalArgumentException.class, () -> service.updateContact("0000000001", "Johnny", "Smith", "0987654321", "456 Oak Avenue"));
    }

    @Test
    void testDeleteContactNotFound() {
        ContactService service = new ContactService();
        assertThrows(IllegalArgumentException.class, () -> service.deleteContact("0000000001"));
    }

    @Test
    void testGetContactNotFound() {
        ContactService service = new ContactService();
        assertNull(service.getContact("0000000001"));
    }

    @Test
    void testResetIdCounter() {
        ContactService service = new ContactService();
        service.addContact("John", "Doe", "1234567890", "123 Elm Street");
        assertEquals("0000000001", service.getContact("0000000001").getId());
        
        ContactService.resetIdCounter();
        service.addContact("Jane", "Smith", "0987654321", "456 Oak Avenue");
        assertEquals("0000000001", service.getContact("0000000001").getId()); // After reset, first ID is reused
    }
}
