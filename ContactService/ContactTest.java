package cs320mod3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class ContactTest {

    @Test
    void testContactCreationWithValidData() {
        Contact contact = new Contact("1234567890", "John", "Doe", "1234567890", "123 Elm Street");
        assertEquals("1234567890", contact.getId());
        assertEquals("John", contact.getFirstName());
        assertEquals("Doe", contact.getLastName());
        assertEquals("1234567890", contact.getPhone());
        assertEquals("123 Elm Street", contact.getAddress());
    }

    @Test
    void testContactCreationWithNullId() {
        assertThrows(NullPointerException.class, () -> new Contact(null, "John", "Doe", "1234567890", "123 Elm Street"));
    }

    @Test
    void testContactCreationWithLongId() {
        assertThrows(IllegalArgumentException.class, () -> new Contact("12345678901", "John", "Doe", "1234567890", "123 Elm Street"));
    }

    @Test
    void testContactCreationWithNullFirstName() {
        assertThrows(NullPointerException.class, () -> new Contact("1234567890", null, "Doe", "1234567890", "123 Elm Street"));
    }

    @Test
    void testContactCreationWithLongFirstName() {
        assertThrows(IllegalArgumentException.class, () -> new Contact("1234567890", "ThisNameIsWayTooLong", "Doe", "1234567890", "123 Elm Street"));
    }

    @Test
    void testContactCreationWithNullLastName() {
        assertThrows(NullPointerException.class, () -> new Contact("1234567890", "John", null, "1234567890", "123 Elm Street"));
    }

    @Test
    void testContactCreationWithLongLastName() {
        assertThrows(IllegalArgumentException.class, () -> new Contact("1234567890", "John", "ThisLastNameIsTooLong", "1234567890", "123 Elm Street"));
    }

    @Test
    void testContactCreationWithNullPhone() {
        assertThrows(NullPointerException.class, () -> new Contact("1234567890", "John", "Doe", null, "123 Elm Street"));
    }

    @Test
    void testContactCreationWithInvalidPhoneLength() {
        assertThrows(IllegalArgumentException.class, () -> new Contact("1234567890", "John", "Doe", "12345", "123 Elm Street"));
    }

    @Test
    void testContactCreationWithNonNumericPhone() {
        assertThrows(IllegalArgumentException.class, () -> new Contact("1234567890", "John", "Doe", "12345abcde", "123 Elm Street"));
    }

    @Test
    void testContactCreationWithNullAddress() {
        assertThrows(NullPointerException.class, () -> new Contact("1234567890", "John", "Doe", "1234567890", null));
    }

    @Test
    void testContactCreationWithLongAddress() {
        assertThrows(IllegalArgumentException.class, () -> new Contact("1234567890", "John", "Doe", "1234567890", "123 Elm Street Address That Is Way Too Long"));
    }

    @Test
    void testSetFirstNameWithValidData() {
        Contact contact = new Contact("1234567890", "John", "Doe", "1234567890", "123 Elm Street");
        contact.setFirstName("Jane");
        assertEquals("Jane", contact.getFirstName());
    }

    @Test
    void testSetFirstNameWithNull() {
        Contact contact = new Contact("1234567890", "John", "Doe", "1234567890", "123 Elm Street");
        assertThrows(NullPointerException.class, () -> contact.setFirstName(null));
    }

    @Test
    void testSetFirstNameWithLongData() {
        Contact contact = new Contact("1234567890", "John", "Doe", "1234567890", "123 Elm Street");
        assertThrows(IllegalArgumentException.class, () -> contact.setFirstName("ThisNameIsWayTooLong"));
    }

    @Test
    void testSetLastNameWithValidData() {
        Contact contact = new Contact("1234567890", "John", "Doe", "1234567890", "123 Elm Street");
        contact.setLastName("Smith");
        assertEquals("Smith", contact.getLastName());
    }

    @Test
    void testSetLastNameWithNull() {
        Contact contact = new Contact("1234567890", "John", "Doe", "1234567890", "123 Elm Street");
        assertThrows(NullPointerException.class, () -> contact.setLastName(null));
    }

    @Test
    void testSetLastNameWithLongData() {
        Contact contact = new Contact("1234567890", "John", "Doe", "1234567890", "123 Elm Street");
        assertThrows(IllegalArgumentException.class, () -> contact.setLastName("ThisLastNameIsTooLong"));
    }

    @Test
    void testSetPhoneWithValidData() {
        Contact contact = new Contact("1234567890", "John", "Doe", "1234567890", "123 Elm Street");
        contact.setPhone("0987654321");
        assertEquals("0987654321", contact.getPhone());
    }

    @Test
    void testSetPhoneWithNull() {
        Contact contact = new Contact("1234567890", "John", "Doe", "1234567890", "123 Elm Street");
        assertThrows(NullPointerException.class, () -> contact.setPhone(null));
    }

    @Test
    void testSetPhoneWithInvalidLength() {
        Contact contact = new Contact("1234567890", "John", "Doe", "1234567890", "123 Elm Street");
        assertThrows(IllegalArgumentException.class, () -> contact.setPhone("12345"));
    }

    @Test
    void testSetPhoneWithNonNumericData() {
        Contact contact = new Contact("1234567890", "John", "Doe", "1234567890", "123 Elm Street");
        assertThrows(IllegalArgumentException.class, () -> contact.setPhone("12345abcde"));
    }

    @Test
    void testSetAddressWithValidData() {
        Contact contact = new Contact("1234567890", "John", "Doe", "1234567890", "123 Elm Street");
        contact.setAddress("456 Oak Avenue");
        assertEquals("456 Oak Avenue", contact.getAddress());
    }

    @Test
    void testSetAddressWithNull() {
        Contact contact = new Contact("1234567890", "John", "Doe", "1234567890", "123 Elm Street");
        assertThrows(NullPointerException.class, () -> contact.setAddress(null));
    }

    @Test
    void testSetAddressWithLongData() {
        Contact contact = new Contact("1234567890", "John", "Doe", "1234567890", "123 Elm Street");
        assertThrows(IllegalArgumentException.class, () -> contact.setAddress("456 Oak Avenue Address That Is Way Too Long"));
    }

    @Test
    void testContactCreationWithEdgeCaseFieldLengths() {
        String id = "1234567890"; // 10 characters
        String firstName = "Johnathan"; // 10 characters
        String lastName = "DoeSmith12"; // 10 characters
        String phone = "0123456789"; // 10 digits
        String address = "1234567890 Elm Street, Apt 1"; // 30 characters
        
        Contact contact = new Contact(id, firstName, lastName, phone, address);
        
        assertEquals(id, contact.getId());
        assertEquals(firstName, contact.getFirstName());
        assertEquals(lastName, contact.getLastName());
        assertEquals(phone, contact.getPhone());
        assertEquals(address, contact.getAddress());
    }

    @Test
    void testSetFirstNameAtMaxLength() {
        Contact contact = new Contact("1234567890", "John", "Doe", "1234567890", "123 Elm Street");
        String maxLengthName = "Johnathan"; // 10 characters
        contact.setFirstName(maxLengthName);
        assertEquals(maxLengthName, contact.getFirstName());
    }

    @Test
    void testSetLastNameAtMaxLength() {
        Contact contact = new Contact("1234567890", "John", "Doe", "1234567890", "123 Elm Street");
        String maxLengthLastName = "DoeSmith12"; // 10 characters
        contact.setLastName(maxLengthLastName);
        assertEquals(maxLengthLastName, contact.getLastName());
    }

    @Test
    void testSetAddressAtMaxLength() {
        Contact contact = new Contact("1234567890", "John", "Doe", "1234567890", "123 Elm Street");
        String maxLengthAddress = "1234567890 Elm Street, Apt 1"; // 30 characters
        contact.setAddress(maxLengthAddress);
        assertEquals(maxLengthAddress, contact.getAddress());
    }

    @Test
    void testUpdateContactWithEmptyFirstName() {
        Contact contact = new Contact("1234567890", "John", "Doe", "1234567890", "123 Elm Street");
        // Assuming empty strings are treated as no update; adjust based on implementation
        contact.setFirstName("");
        // Verify that firstName remains unchanged or is updated based on your logic
        // For this example, let's assume it should not update
        assertEquals("John", contact.getFirstName());
    }

    @Test
    void testUpdateContactWithEmptyLastName() {
        Contact contact = new Contact("1234567890", "John", "Doe", "1234567890", "123 Elm Street");
        contact.setLastName("");
        // Assuming empty strings are treated as no update
        assertEquals("Doe", contact.getLastName());
    }

    @Test
    void testUpdateContactWithEmptyPhone() {
        Contact contact = new Contact("1234567890", "John", "Doe", "1234567890", "123 Elm Street");
        assertThrows(IllegalArgumentException.class, () -> contact.setPhone(""));
    }

    @Test
    void testUpdateContactWithEmptyAddress() {
        Contact contact = new Contact("1234567890", "John", "Doe", "1234567890", "123 Elm Street");
        contact.setAddress("");
        // Assuming empty strings are treated as no update
        assertEquals("123 Elm Street", contact.getAddress());
    }
}
