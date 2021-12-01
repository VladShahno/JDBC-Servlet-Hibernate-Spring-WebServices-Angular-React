package com.nixsolutions.crudapp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 10)
    @Column(name = "login", unique = true)
    private String login;

    @NotNull
    @NotBlank
    @Column(name = "password")
    private String password;

    @NotNull
    @NotBlank
    @Transient
    private String passwordConfirm;

    @NotNull
    @Email
    @Column(name = "email", unique = true)
    private String email;

    @NotBlank
    @NotNull
    @Size(min = 2, max = 40)
    @Column(name = "first_name")
    private String firstName;

    @NotBlank
    @NotNull
    @Size(min = 2, max = 40)
    @Column(name = "last_name")
    private String lastName;

    @Past
    @Column(name = "birthday")
    private Date birthday;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    public User(String login, String password, String email, String firstName,
            String lastName, Date birthday) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
    }

    public User(String login, String password, String passwordConfirm,
            String email, String firstName, String lastName, Date birthday,
            Role role) {
        this.login = login;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.role = role;
    }
}