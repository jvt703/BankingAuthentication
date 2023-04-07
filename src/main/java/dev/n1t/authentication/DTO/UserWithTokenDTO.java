package dev.n1t.authentication.DTO;

public record UserWithTokenDTO( Long id, String firstname, String lastname, String email, String AccessToken, String RefreshToken) {


}
