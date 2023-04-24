package dev.n1t.authentication.ResponseTests;

import dev.n1t.authentication.Auth.RefreshRequest;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class RefreshRequestTest {
    @Test
    void testConstructorAndGetters() {
        // Given
        String refreshToken = "1234567890";

        // When
        RefreshRequest request = new RefreshRequest(refreshToken);

        // Then
        assertEquals(refreshToken, request.getRefreshToken());
    }

    @Test
    void testSetters() {
        // Given
        String refreshToken = "1234567890";
        RefreshRequest request = new RefreshRequest(refreshToken);

        // When
        String newRefreshToken = "0987654321";
        request.setRefreshToken(newRefreshToken);

        // Then
        assertEquals(newRefreshToken, request.getRefreshToken());
    }
    @Test
    void equals_withSameObject_shouldReturnTrue() {
        // Given
        RefreshRequest request = new RefreshRequest("refresh_token");

        // When/Then
        assertEquals(request, request);
    }

    @Test
    void equals_withEqualObjects_shouldReturnTrue() {
        // Given
        RefreshRequest request1 = new RefreshRequest("refresh_token");
        RefreshRequest request2 = new RefreshRequest("refresh_token");

        // When/Then
        assertEquals(request1, request2);
    }
    @Test
    void testNoArgsConstructor() {
        RefreshRequest request = new RefreshRequest();
        assertNull(request.getRefreshToken());
    }


    @Test
    void testBuilder() {
        String refreshToken = "valid_refresh_token";
        RefreshRequest request = RefreshRequest.builder()
                .refreshToken(refreshToken)
                .build();
        assertEquals(refreshToken, request.getRefreshToken());
    }
    @Test
    void testEquals() {
        String token = "test_token";
        RefreshRequest request1 = RefreshRequest.builder()
                .refreshToken(token)
                .build();
        RefreshRequest request2 = RefreshRequest.builder()
                .refreshToken(token)
                .build();
        assertEquals(request1, request2);
    }
    @Test
    void testNotEquals() {
        RefreshRequest request1 = RefreshRequest.builder()
                .refreshToken("token1")
                .build();
        RefreshRequest request2 = RefreshRequest.builder()
                .refreshToken("token2")
                .build();
        assertNotEquals(request1, request2);
    }
    @Test
    void equals_withDifferentObjects_shouldReturnFalse() {
        // Given
        RefreshRequest request1 = new RefreshRequest("refresh_token1");
        RefreshRequest request2 = new RefreshRequest("refresh_token2");

        // When/Then
        assertNotEquals(request1, request2);
    }

    @Test
    void hashCode_withEqualObjects_shouldReturnEqualValue() {
        // Given
        RefreshRequest request1 = new RefreshRequest("refresh_token");
        RefreshRequest request2 = new RefreshRequest("refresh_token");

        // When
        int hashCode1 = request1.hashCode();
        int hashCode2 = request2.hashCode();

        // Then
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    void hashCode_withDifferentObjects_shouldReturnDifferentValue() {
        // Given
        RefreshRequest request1 = new RefreshRequest("refresh_token1");
        RefreshRequest request2 = new RefreshRequest("refresh_token2");

        // When
        int hashCode1 = request1.hashCode();
        int hashCode2 = request2.hashCode();

        // Then
        assertNotEquals(hashCode1, hashCode2);
    }

}
