package com.nixsolutions.com.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDtoForCreate {

    @NotBlank
    @NotNull
    @Size(min = 2, max = 10)
    private String login;

    @NotNull
    @NotBlank
    private String password;

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

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    @NotNull
    private String role;
}