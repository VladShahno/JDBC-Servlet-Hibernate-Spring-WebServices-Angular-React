package com.nixsolutions.crudapp.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtTokenDto {

    private String user;
    private String token;
    private String role;
}
