package dev.n1t.authentication.ResponseTests;

import dev.n1t.authentication.Auth.RegisterRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import static org.junit.Assert.*;

public class RegisterRequestTests {
    @Test
    void testEqualsMethod() {
        RegisterRequest request1 = RegisterRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .city("New York")
                .state("NY")
                .street("123 Main St")
                .zipCode("10001")
                .birthDate(1234567890L)
                .build();
        RegisterRequest request2 = RegisterRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .city("New York")
                .state("NY")
                .street("123 Main St")
                .zipCode("10001")
                .birthDate(1234567890L)
                .build();
        RegisterRequest request3 = RegisterRequest.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("jane.doe@example.com")
                .password("password456")
                .city("Boston")
                .state("MA")
                .street("456 Elm St")
                .zipCode("02115")
                .birthDate(2345678901L)
                .build();

        Assertions.assertEquals(request1, request2);
        Assertions.assertNotEquals(request1, request3);
        Assertions.assertNotEquals(request1, null);
        Assertions.assertFalse(request1.equals("a string"));
    }
    @Test
    void testEqualsAndHashCodeBuilderAnnotation() {
        RegisterRequest request1 = RegisterRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .city("New York")
                .state("NY")
                .street("123 Main St")
                .zipCode("10001")
                .birthDate(1234567890L)
                .build();
        RegisterRequest request2 = RegisterRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .city("New York")
                .state("NY")
                .street("123 Main St")
                .zipCode("10001")
                .birthDate(1234567890L)
                .build();

        Assertions.assertEquals(request1, request2);
        Assertions.assertEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    void testNotEqualsAndHashCodeBuilderAnnotation() {
        RegisterRequest request1 = RegisterRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .city("New York")
                .state("NY")
                .street("123 Main St")
                .zipCode("10001")
                .birthDate(1234567890L)
                .build();
        RegisterRequest request2 = RegisterRequest.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("jane.doe@example.com")
                .password("password456")
                .city("Boston")
                .state("MA")
                .street("456 Elm St")
                .zipCode("02115")
                .birthDate(2345678901L)
                .build();

        Assertions.assertNotEquals(request1, request2);
        Assertions.assertNotEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    void testEqualsAndHashCodeDataAnnotation() {
        RegisterRequest request1 = new RegisterRequest();
        request1.setFirstName("John");
        request1.setLastName("Doe");
        request1.setEmail("john.doe@example.com");
        request1.setPassword("password123");
        request1.setCity("New York");
        request1.setState("NY");
        request1.setStreet("123 Main St");
        request1.setZipCode("10001");
        request1.setBirthDate(1234567890L);

        RegisterRequest request2 = new RegisterRequest();
        request2.setFirstName("John");
        request2.setLastName("Doe");
        request2.setEmail("john.doe@example.com");
        request2.setPassword("password123");
        request2.setCity("New York");
        request2.setState("NY");
        request2.setStreet("123 Main St");
        request2.setZipCode("10001");
        request2.setBirthDate(1234567890L);

        Assertions.assertEquals(request1, request2);
        Assertions.assertEquals(request1.hashCode(), request2.hashCode());
    }
    @Test
    void testDataAnnotation() {
        RegisterRequest request1 = new RegisterRequest();
        request1.setFirstName("John");
        request1.setLastName("Doe");
        request1.setEmail("johndoe@example.com");
        request1.setPassword("password");
        request1.setCity("New York");
        request1.setState("NY");
        request1.setStreet("123 Main St");
        request1.setZipCode("10001");
        request1.setBirthDate(946684800L); // 01/01/2000

        RegisterRequest request2 = new RegisterRequest();
        request2.setFirstName("John");
        request2.setLastName("Doe");
        request2.setEmail("johndoe@example.com");
        request2.setPassword("password");
        request2.setCity("New York");
        request2.setState("NY");
        request2.setStreet("123 Main St");
        request2.setZipCode("10001");
        request2.setBirthDate(946684800L); // 01/01/2000

        Assertions.assertEquals(request1, request2);
        Assertions.assertEquals(request1.hashCode(), request2.hashCode());
        Assertions.assertEquals(request1.toString(), request2.toString());
    }

    @Test
    void testBuilderAnnotation() {
        RegisterRequest request = RegisterRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@example.com")
                .password("password")
                .city("New York")
                .state("NY")
                .street("123 Main St")
                .zipCode("10001")
                .birthDate(946684800L) // 01/01/2000
                .build();

        Assertions.assertEquals("John", request.getFirstName());
        Assertions.assertEquals("Doe", request.getLastName());
        Assertions.assertEquals("johndoe@example.com", request.getEmail());
        Assertions.assertEquals("password", request.getPassword());
        Assertions.assertEquals("New York", request.getCity());
        Assertions.assertEquals("NY", request.getState());
        Assertions.assertEquals("123 Main St", request.getStreet());
        Assertions.assertEquals("10001", request.getZipCode());
        Assertions.assertEquals(946684800L, (long) request.getBirthDate());
    }
    @Test
    void testConstructor() {
        RegisterRequest request = new RegisterRequest("John", "Doe", "johndoe@example.com", "password", "New York", "NY", "123 Main St", "10001", 946684800L);
        Assertions.assertEquals("John", request.getFirstName());
        Assertions.assertEquals("Doe", request.getLastName());
        Assertions.assertEquals("johndoe@example.com", request.getEmail());
        Assertions.assertEquals("password", request.getPassword());
        Assertions.assertEquals("New York", request.getCity());
        Assertions.assertEquals("NY", request.getState());
        Assertions.assertEquals("123 Main St", request.getStreet());
        Assertions.assertEquals("10001", request.getZipCode());
        Assertions.assertEquals(946684800L, (long) request.getBirthDate());
    }
    @Test
    void testEqualsForFirstNameUsingBuilder() {
        RegisterRequest request1 = RegisterRequest.builder()
                .firstName("John")
                .build();
        RegisterRequest request2 = RegisterRequest.builder()
                .firstName("John")
                .build();
        RegisterRequest request3 = RegisterRequest.builder()
                .firstName("Jane")
                .build();

        // Test that objects with the same first name are equal
        Assertions.assertEquals(request1, request2);

        // Test that objects with different first names are not equal
        Assertions.assertNotEquals(request1, request3);
    }
    @Test
    void testSetAndGetFirstName() {
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("John");
        Assertions.assertEquals("John", request.getFirstName());
    }
    @Test
    void testBuilder() {
        RegisterRequest request = RegisterRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@example.com")
                .password("password")
                .city("New York")
                .state("NY")
                .street("123 Main St")
                .zipCode("10001")
                .birthDate(946684800L) // 01/01/2000
                .build();

        Assertions.assertEquals("John", request.getFirstName());
        Assertions.assertEquals("Doe", request.getLastName());
        Assertions.assertEquals("johndoe@example.com", request.getEmail());
        Assertions.assertEquals("password", request.getPassword());
        Assertions.assertEquals("New York", request.getCity());
        Assertions.assertEquals("NY", request.getState());
        Assertions.assertEquals("123 Main St", request.getStreet());
        Assertions.assertEquals("10001", request.getZipCode());
        Assertions.assertEquals(946684800L, (long) request.getBirthDate());
    }

    @Test
    void testSetAndGetLastName() {
        RegisterRequest request = new RegisterRequest();
        request.setLastName("Doe");
        Assertions.assertEquals("Doe", request.getLastName());
    }

    @Test
    void testEqualsAndHashCode() {
        RegisterRequest request1 = RegisterRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .city("New York")
                .state("NY")
                .street("123 Main St")
                .zipCode("10001")
                .birthDate(1234567890L)
                .build();
        RegisterRequest request2 = RegisterRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .city("New York")
                .state("NY")
                .street("123 Main St")
                .zipCode("10001")
                .birthDate(1234567890L)
                .build();

        Assertions.assertEquals(request1, request2);
        Assertions.assertEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    void testNotEqualsAndHashCode() {
        RegisterRequest request1 = RegisterRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .city("New York")
                .state("NY")
                .street("123 Main St")
                .zipCode("10001")
                .birthDate(1234567890L)
                .build();
        RegisterRequest request2 = RegisterRequest.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("jane.doe@example.com")
                .password("password456")
                .city("Boston")
                .state("MA")
                .street("456 Elm St")
                .zipCode("02115")
                .birthDate(2345678901L)
                .build();

        Assertions.assertNotEquals(request1, request2);
        Assertions.assertNotEquals(request1.hashCode(), request2.hashCode());
    }
}
