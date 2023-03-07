package dev.n1t.authentication.DTO;

public record UserWithTokenDTO( String firstname, String lastname, String email, String AccessToken, String RefreshToken) {


}
