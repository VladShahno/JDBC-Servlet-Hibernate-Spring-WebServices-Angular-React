package com.nixsolutions.crudapp.controller;

import com.nixsolutions.crudapp.data.PublicUserDto;
import com.nixsolutions.crudapp.data.UserDtoForCreate;
import com.nixsolutions.crudapp.exception.FormProcessingException;
import com.nixsolutions.crudapp.mapper.UserMapper;
import com.nixsolutions.crudapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RestController implements Controller {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    ExceptionController exceptionController;

    public RestController() {
    }

    @POST
    public Response postCreate(@Valid UserDtoForCreate userDtoForCreate) {

        Map<String, String> invalidFields = userService.create(
                userMapper.userFromUserDtoForCreate(userDtoForCreate));
        if (invalidFields.isEmpty()) {
            return Response.status(Response.Status.CREATED)
                    .entity(userDtoForCreate).build();
        } else {
            return exceptionController.handleException(
                    new FormProcessingException(
                            invalidFields.values().toString(),
                            invalidFields.keySet().toString()));
        }
    }

    @Path("/{login}")
    @PUT
    public Response putUpdate(@PathParam(value = "login") String login,
            @Valid UserDtoForCreate userDtoForCreate) {

        Map<String, String> invalidFields = userService.update(
                userDtoForCreate);
        if (invalidFields.isEmpty()) {
            return Response.status(Response.Status.OK).entity(userDtoForCreate)
                    .build();
        } else {
            return exceptionController.handleException(
                    new FormProcessingException(
                            invalidFields.values().toString(),
                            invalidFields.keySet().toString()));
        }
    }

    @Path("/{login}")
    @DELETE
    public Response delete(@PathParam(value = "login") String login) {
        userService.remove(userService.findByLogin(login));
        return Response.status(Response.Status.OK).build();
    }

    @Path("/all")
    @GET
    public List<PublicUserDto> getAll() {
        return userService.findAll();
    }

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByLogin(@PathParam("username") String login) {
        Optional<UserDtoForCreate> userDto = Optional.ofNullable(
                userMapper.userDtoForCreateFromUser(
                        userService.findByLogin(login)));
        if (userDto.isPresent()) {
            return Response.ok(userDto.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}