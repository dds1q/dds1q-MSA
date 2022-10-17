package com.example.ecommerceuserservice.vo;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequest {

    @NotNull( message = "Email cannot be null" )
    @Size( min = 2 , message = "Email not be less then two characters" )
    @Email
    private String email;

    @NotNull( message = "Name cannot be null" )
    @Size( min = 2 , message = "Name not be less then two characters" )
    private String name;

    @NotNull( message = "Password cannot be null" )
    @Size( min = 8 , message = "Password must be equal or grater then 8 characters" )
    private String pwd;

}
