package br.com.compassuol.pb.challenge.msproducts.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    @NotEmpty(message = "The User first name should not be empty")
    @Size(min = 3, message = "User first name should have at least 3 characters")
    private String firstName;

    @NotEmpty(message = "User last name should not be empty")
    @Size(min = 3, message = "User last name should have at least 3 characters")
    @Column(name = "last_name")
    private String lastName;

    @Column(nullable = false,unique = true)
    @Email(message = "The email should be valid")
    private String email;

    @Column(nullable = false,unique = true)
    @NotEmpty(message = "User password should not be empty")
    @Size(min = 6, message = "User password should have at least 6 characters")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name="user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name ="role_id", referencedColumnName = "id"))
    private Set<Role> roles;
}
