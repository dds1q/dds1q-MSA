package com.example.ecommerceuserservice.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {

    @NotNull( message = "Email cannot be null" )
    @Size( min = 2 , message = "Email not be less than two characters")
    private String email;

    @NotNull( message = "Password cannot be null" )
    @Size( min = 8 , message = "Email must be equals or grater than 8 characters")
    private String password;

}
