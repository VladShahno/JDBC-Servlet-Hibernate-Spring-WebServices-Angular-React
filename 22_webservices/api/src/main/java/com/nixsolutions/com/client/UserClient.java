package com.nixsolutions.com.client;

import com.nixsolutions.com.data.JwtTokenDto;
import com.nixsolutions.com.data.LoginDto;
import com.nixsolutions.com.data.PublicUserDto;
import com.nixsolutions.com.data.UserDtoForCreate;
import com.nixsolutions.com.utils.StringUtils;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class UserClient {

    private final Client client = ClientBuilder.newClient();

    private final WebTarget apiTarget = client.target(
            StringUtils.BASE_URL + "api/");

    public String getToken() {
        System.out.println(StringUtils.BASE_URL);
        WebTarget loginTarget = apiTarget.path("login");
        Response response = loginTarget.request()
                .post(Entity.entity(new LoginDto("admin", "admin"),
                        MediaType.APPLICATION_JSON));
        return "Bearer " + response.readEntity(JwtTokenDto.class).getToken();
    }

    public Response deleteUser(String login) {

        WebTarget deleteUserTarget = apiTarget.path("users/" + login);
        return deleteUserTarget.request(MediaType.APPLICATION_JSON)
                .header("Authorization", getToken()).delete();
    }

    public UserDtoForCreate putUser(UserDtoForCreate userDtoForCreate) {

        WebTarget putUserTarget = apiTarget.path(
                "users/" + userDtoForCreate.getLogin());
        return putUserTarget.request(MediaType.APPLICATION_JSON)
                .header("Authorization", getToken())
                .put(Entity.entity(userDtoForCreate,
                        MediaType.APPLICATION_JSON))
                .readEntity(userDtoForCreate.getClass());
    }

    public UserDtoForCreate postUser(UserDtoForCreate userDto) {

        WebTarget postUser = apiTarget.path("users");
        return postUser.request(MediaType.APPLICATION_JSON)
                .header("Authorization", getToken())
                .post(Entity.entity(userDto, MediaType.APPLICATION_JSON))
                .readEntity(userDto.getClass());
    }

    public PublicUserDto[] getAll() {

        WebTarget users = apiTarget.path("users/all");
        Response response = getResponse(users);
        return response.readEntity(PublicUserDto[].class);
    }

    public UserDtoForCreate getByLogin(String login) {

        WebTarget findByLogin = apiTarget.path("users/" + login);
        Response response = findByLogin.request(MediaType.APPLICATION_JSON)
                .header("Authorization", getToken()).get();
        return response.readEntity(UserDtoForCreate.class);
    }

    private Response getResponse(WebTarget webTarget) {

        Invocation.Builder invocationBuilder = webTarget.request(
                MediaType.APPLICATION_JSON).header("Authorization", getToken());
        return invocationBuilder.get();
    }
}
