package com.example.guessnumberservice.request;

import com.example.guessnumberservice.util.ValidationConstants;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class LoginRequest {
    @NotBlank(message = "{username.required}")
    @Length(max = ValidationConstants.User.USERNAME_MAX, message = "{username.length}")
    String username;

    @NotBlank(message = "{password.required}")
    @Length(min = ValidationConstants.User.PASSWORD_MIN,
            max = ValidationConstants.User.PASSWORD_MAX,
            message = "{password.length}")
    String password;
}