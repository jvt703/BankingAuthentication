package dev.n1t.authentication.Auth;


import dev.n1t.authentication.DTO.AuthResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse implements AuthResponse {

    private String token;

    private String RefreshToken;

    private String id;

}
