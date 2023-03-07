package dev.n1t.authentication.Auth;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    //request will be giving Role somehow
    //testing changing to int
    private Integer role;


    private String city;


    private String state;


    private String street;

    private String zipCode;

}
