package entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {

    private Long id;

    private String login;

    private String password;

    private String passwordConfirm;

    private String email;

    private String firstName;

    private String lastName;

    private Date birthday;

    private Role role;

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