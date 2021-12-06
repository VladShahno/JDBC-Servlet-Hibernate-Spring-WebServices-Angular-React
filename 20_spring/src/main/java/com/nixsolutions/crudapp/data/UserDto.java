package com.nixsolutions.crudapp.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {

    @NotBlank
    @Size(min = 2, max = 10)
    private String login;

    @NotNull
    @NotBlank
    private String password;

    @Transient
    @NotNull
    @NotBlank
    private String passwordConfirm;

    @NotNull
    @Email
    private String email;

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 40)
    private String firstName;

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 40)
    private String lastName;

    @Past
    private Date birthday;

    @NotNull
    private Long role;
}