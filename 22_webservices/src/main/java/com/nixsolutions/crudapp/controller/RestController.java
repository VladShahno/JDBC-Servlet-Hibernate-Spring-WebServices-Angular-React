package com.nixsolutions.crudapp.controller;

import com.nixsolutions.crudapp.data.UserDto;
import com.nixsolutions.crudapp.entity.User;
import com.nixsolutions.crudapp.exception.UserBirthdayException;
import com.nixsolutions.crudapp.exception.UserPasswordEqualsException;
import com.nixsolutions.crudapp.exception.UserWithEmailExistsException;
import com.nixsolutions.crudapp.exception.UserWithLoginExistsException;
import com.nixsolutions.crudapp.service.AuthenticationService;
import com.nixsolutions.crudapp.service.UpdateUserService;
import com.nixsolutions.crudapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/users")

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RestController extends SpringBeanAutowiringSupport {

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserService userService;
    @Autowired
    private UpdateUserService updateUserService;

    @Path("/create")
    @POST
    public Response postCreate(@Valid User user)
            throws UserWithLoginExistsException, UserWithEmailExistsException,
            UserPasswordEqualsException, UserBirthdayException {
        authenticationService.register(user);
        return Response.status(Response.Status.CREATED).build();
    }

    @Path("/update/{login}")
    @PUT
    public Response putUpdate(@PathParam(value = "login") String login,
            @Valid UserDto userDto)
            throws UserPasswordEqualsException, UserWithEmailExistsException,
            UserBirthdayException {
            updateUserService.update(userDto);
        return Response.status(Response.Status.OK).build();
    }

    @Path("/delete/{login}")
    @DELETE
    public Response delete(@PathParam(value = "login") String login) {
        userService.remove(userService.findByLogin(login));
        return Response.status(Response.Status.OK).build();
    }

    @Path("/all")
    @GET
    public Response getFindAll() {
        Map<Integer, Map<String, Object>> result = new HashMap<>();
        List<User> userList = userService.findAll();
        for (int i = 0; i < userList.size(); i++) {
            result.put(i, convertToMap(userList.get(i)));
        }
        return Response.ok(result).status(Response.Status.OK).build();
    }

    @Path("/findByEmail/{email}")
    @GET
    public Response getFindByEmail(@PathParam(value = "email") String email) {
        return Response.ok(convertToMap(userService.findByEmail(email)))
                .status(Response.Status.OK).build();
    }

    @Path("/findByLogin/{login}")
    @GET
    public Response getFindByLogin(@PathParam(value = "login") String login) {
        return Response.ok(convertToMap(userService.findByLogin(login)))
                .status(Response.Status.OK).build();
    }

    private Map<String, Object> convertToMap(User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("login", user.getLogin());
        map.put("password", user.getPassword());
        map.put("passwordConfirm", user.getPasswordConfirm());
        map.put("email", user.getEmail());
        map.put("first_name", user.getFirstName());
        map.put("last_name", user.getLastName());
        map.put("birthday", user.getBirthday().toString());
        map.put("role", user.getRole());
        return map;
    }
}