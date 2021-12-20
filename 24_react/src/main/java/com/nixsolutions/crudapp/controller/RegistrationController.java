package com.nixsolutions.crudapp.controller;

import com.nixsolutions.crudapp.data.UserDtoRegisterRequest;
import com.nixsolutions.crudapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

@Path("/registration")
@Component
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@AllArgsConstructor
public class RegistrationController implements Controller {

    @Autowired
    private UserService userService;

    @POST
    public Response postRegister(
            @Valid UserDtoRegisterRequest userDtoRegisterRequest) {

        Map<String, String> invalidFields = userService.register(
                userDtoRegisterRequest);
        if (invalidFields.isEmpty()) {
            return Response.status(Response.Status.CREATED)
                    .entity(userDtoRegisterRequest).build();
        }
        throw new WebApplicationException(
                Response.status(Response.Status.BAD_REQUEST)
                        .entity(invalidFields).build());
    }
}