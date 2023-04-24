package dev.n1t.authentication.ResponseTests;

import dev.n1t.authentication.Auth.AuthenticationResponse;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class AuthenticationResponseTests {

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
