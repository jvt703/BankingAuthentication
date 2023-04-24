package dev.n1t.authentication.ResponseTests;

import dev.n1t.authentication.Auth.AuthenticationRequest;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class AuthenticationRequestTests {

    @Test
    void createAuthenticationRequest_withParameters_shouldSetParameters() {
        // Given
        String email = "test@example.com";
        String password = "password123";

        // When
        AuthenticationRequest request = new AuthenticationRequest(email, password);

        // Then
        assertNotNull(request);
        assertEquals(email, request.getEmail());
        assertEquals(password, request.getPassword());
    }

    @Test
    void setEmail_withNewEmail_shouldUpdateEmail() {
        // Given
        AuthenticationRequest request = new AuthenticationRequest("test@example.com", "password123");

        // When
        String newEmail = "new_test@example.com";
        request.setEmail(newEmail);

        // Then
        assertEquals(newEmail, request.getEmail());
    }

    @Test
    void setPassword_withNewPassword_shouldUpdatePassword() {
        // Given
        AuthenticationRequest request = new AuthenticationRequest("test@example.com", "password123");

        // When
        String newPassword = "new_password123";
        request.setPassword(newPassword);

        // Then
        assertEquals(newPassword, request.getPassword());
    }
    @Test
    void equals_givenTwoEqualRequests_shouldReturnTrue() {
        // Arrange
        AuthenticationRequest request1 = new AuthenticationRequest("user@example.com", "password");
        AuthenticationRequest request2 = new AuthenticationRequest("user@example.com", "password");

        // Act
        boolean result = request1.equals(request2);

        // Assert
        assertTrue(result);
    }

    @Test
    void equals_givenTwoDifferentRequests_shouldReturnFalse() {
        // Arrange
        AuthenticationRequest request1 = new AuthenticationRequest("user1@example.com", "password1");
        AuthenticationRequest request2 = new AuthenticationRequest("user2@example.com", "password2");

        // Act
        boolean result = request1.equals(request2);

        // Assert
        assertFalse(result);
    }

    @Test
    void hashCode_givenTwoEqualRequests_shouldReturnEqualHashCodes() {
        // Arrange
        AuthenticationRequest request1 = new AuthenticationRequest("user@example.com", "password");
        AuthenticationRequest request2 = new AuthenticationRequest("user@example.com", "password");

        // Act
        int hashCode1 = request1.hashCode();
        int hashCode2 = request2.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    void hashCode_givenTwoDifferentRequests_shouldReturnDifferentHashCodes() {
        // Arrange
        AuthenticationRequest request1 = new AuthenticationRequest("user1@example.com", "password1");
        AuthenticationRequest request2 = new AuthenticationRequest("user2@example.com", "password2");

        // Act
        int hashCode1 = request1.hashCode();
        int hashCode2 = request2.hashCode();

        // Assert
        assertNotEquals(hashCode1, hashCode2);
    }
}
