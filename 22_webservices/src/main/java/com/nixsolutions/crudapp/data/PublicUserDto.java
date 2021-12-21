package com.nixsolutions.crudapp.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicUserDto {

    private String login;
    private String email;
    private String firstName;
    private String lastName;
    private Long age;
    private String role;
}
