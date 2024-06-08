package com.ecommerce.ecommerce.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity

@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Size(min = 4, max = 20, message = "Firstname must contain at least 4 characters and 20 characters long")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "Firstname must not contain numbers or special characters")
    private String firstName;

    @Size(min = 4, max = 20, message = "Lastname must contain at least 4 characters and 20 characters long")
    @Pattern(regexp = "^[a-zA-Z]*$",message = "Lastname must not contain numbers or special characters")
    private String lastName;

    private int age;

    @Size(min = 10, max = 10, message = "Phone number must be exactly 10 characters")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must contain only numbers")
    private String phoneNumber;

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    public User() {
    }

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();



    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "user_address", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "address_id"))
    private List<Address> addresses = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
    private Cart cart;


}
