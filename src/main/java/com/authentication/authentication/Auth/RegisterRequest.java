package com.authentication.authentication.Auth;
import com.authentication.authentication.models.Role;
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

}
