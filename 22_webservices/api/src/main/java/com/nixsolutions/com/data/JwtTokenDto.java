package com.nixsolutions.com.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtTokenDto {

    private String user;
    private String token;
    private String role;
}
