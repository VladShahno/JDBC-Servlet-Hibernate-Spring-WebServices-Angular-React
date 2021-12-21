package com.nixsolutions.crudapp.controller;

import com.nixsolutions.crudapp.data.LoginDto;
import com.nixsolutions.crudapp.entity.User;
import com.nixsolutions.crudapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/login")
@Component
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@AllArgsConstructor
public class LoginController implements Controller {

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    @POST
    public Response login(LoginDto loginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getLogin(),
                        loginDto.getPassword()));
        Optional<User> user = Optional.ofNullable(
                userService.findByLogin(loginDto.getLogin()));
        if (user.isPresent()) {
            return Response.ok(userService.getToken(user.get())).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}