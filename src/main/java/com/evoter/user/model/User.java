package com.evoter.user.model;


import com.evoter.role.model.Role;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "admin_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    @Column(unique = true)
    private String email;

    private String publicKey;

    private String privateKey;

    private String govtId;

    @ManyToOne
    @ToString.Exclude
    private Role role;

}
