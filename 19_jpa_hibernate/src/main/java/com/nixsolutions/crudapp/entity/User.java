package com.nixsolutions.crudapp.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Date;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 2, max = 10)
    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @NotEmpty
    @Size(min = 2, max = 10)
    @Column(name = "password", nullable = false)
    private String password;

    @NotEmpty
    @Email
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @NotEmpty
    @Size(min = 2, max = 40)
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotEmpty
    @Size(min = 2, max = 40)
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @PastOrPresent
    @Column(name = "birthday", nullable = false)
    @FutureOrPresent
    private Date birthday;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    public User(String login, String password, String email, String firstName,
            String lastName, Date birthday, Role role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(login, user.login)
                && Objects.equals(password, user.password) && Objects.equals(
                email, user.email) && Objects.equals(firstName, user.firstName)
                && Objects.equals(lastName, user.lastName) && Objects.equals(
                birthday, user.birthday) && Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, email, firstName, lastName,
                birthday, role);
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", login='" + login + '\''
                + ", password='" + password + '\'' + ", email='" + email + '\''
                + ", firstName='" + firstName + '\'' + ", lastName='" + lastName
                + '\'' + ", birthday=" + birthday + ", roleId="
                + role.toString() + '}';
    }
}