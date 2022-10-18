package com.example.ecommerceuserservice.domain;

import javax.persistence.*;
import lombok.Data;

@Entity
@Data
@Table( name = "users" )
public class User {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;
    @Column( nullable = false , length = 50, unique = true )
    private String email;
    @Column( nullable = false , length = 50 )
    private String name;
    @Column( nullable = false , unique = true )
    private String userId;
    @Column( nullable = false , unique = true )
    private String encryptedPwd;
}
