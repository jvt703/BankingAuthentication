package dev.n1t.authentication.ResponseTests;

import dev.n1t.authentication.Auth.AuthenticationResponse;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class AuthenticationResponseTests {
    @Test
    void testEqualsAndHashCodeUsingDataAnnotation() {
        AuthenticationResponse response1 = new AuthenticationResponse("token", "refreshToken", "1");
        AuthenticationResponse response2 = new AuthenticationResponse("token", "refreshToken", "1");
        AuthenticationResponse response3 = new AuthenticationResponse("differentToken", "refreshToken", "2");

        // Test equals()
        assertTrue(response1.equals(response2));
        assertFalse(response1.equals(response3));

        // Test hashCode()
        assertEquals(response1.hashCode(), response2.hashCode());
        assertNotEquals(response1.hashCode(), response3.hashCode());
    }
    @Test
    void testEqualsAndHashCode_NoArgs() {
        AuthenticationResponse response1 = new AuthenticationResponse();
        response1.setToken("token");
        response1.setRefreshToken("refreshToken");
        response1.setId("id");

        AuthenticationResponse response2 = new AuthenticationResponse();
        response2.setToken("token");
        response2.setRefreshToken("refreshToken");
        response2.setId("id");

        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
    }

    @Test
    void testNotEqualsAndHashCode_NoArgs() {
        AuthenticationResponse response1 = new AuthenticationResponse();
        response1.setToken("token1");
        response1.setRefreshToken("refreshToken1");
        response1.setId("id1");

        AuthenticationResponse response2 = new AuthenticationResponse();
        response2.setToken("token2");
        response2.setRefreshToken("refreshToken2");
        response2.setId("id2");

        assertNotEquals(response1, response2);
        assertNotEquals(response1.hashCode(), response2.hashCode());
    }
    @Test
    void testEqualsAndHashCodeUsingBuilderAnnotation() {
        AuthenticationResponse response1 = AuthenticationResponse.builder()
                .token("token")
                .RefreshToken("refreshToken")
                .id("1")
                .build();
        AuthenticationResponse response2 = AuthenticationResponse.builder()
                .token("token")
                .RefreshToken("refreshToken")
                .id("1")
                .build();
        AuthenticationResponse response3 = AuthenticationResponse.builder()
                .token("differentToken")
                .RefreshToken("refreshToken")
                .id("2")
                .build();

        // Test equals()
        assertTrue(response1.equals(response2));
        assertFalse(response1.equals(response3));

        // Test hashCode()
        assertEquals(response1.hashCode(), response2.hashCode());
        assertNotEquals(response1.hashCode(), response3.hashCode());
    }
    @Test
    void testBuilder() {
        AuthenticationResponse response = AuthenticationResponse.builder()
                .token("token")
                .RefreshToken("refreshToken")
                .id("1")
                .build();

        assertEquals("token", response.getToken());
        assertEquals("refreshToken", response.getRefreshToken());
        assertEquals("1", response.getId());
    }

    @Test
    void testEqualsAndHashCode() {
        AuthenticationResponse response1 = AuthenticationResponse.builder()
                .token("token")
                .RefreshToken("refreshToken")
                .id("1")
                .build();
        AuthenticationResponse response2 = AuthenticationResponse.builder()
                .token("token")
                .RefreshToken("refreshToken")
                .id("1")
                .build();

        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
    }

    @Test
    void testNotEqualsAndHashCode() {
        AuthenticationResponse response1 = AuthenticationResponse.builder()
                .token("token1")
                .RefreshToken("refreshToken")
                .id("1")
                .build();
        AuthenticationResponse response2 = AuthenticationResponse.builder()
                .token("token2")
                .RefreshToken("refreshToken")
                .id("1")
                .build();

        assertNotEquals(response1, response2);
        assertNotEquals(response1.hashCode(), response2.hashCode());
    }

    @Test
    void testToString() {
        AuthenticationResponse response = AuthenticationResponse.builder()
                .token("token")
                .RefreshToken("refreshToken")
                .id("1")
                .build();

        String expected = "AuthenticationResponse(token=token, RefreshToken=refreshToken, id=1)";
        assertEquals(expected, response.toString());
    }

}
