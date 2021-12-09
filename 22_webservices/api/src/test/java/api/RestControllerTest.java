package api;

import data.JwtTokenDto;
import data.LoginDto;
import data.PublicUserDto;
import data.UserDtoForCreate;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.Arrays;
import java.util.Map;

public class RestControllerTest {
    public static final String BASE_ADDR = "http://localhost:8080/";
    private final Client client = ClientBuilder.newClient();
    private final WebTarget apiTarget = client.target(BASE_ADDR + "api/");
    private String token;

    @BeforeEach
    public void getToken() {
        WebTarget loginTarget = apiTarget.path("login");
        Response response = loginTarget.request()
                .post(Entity.entity(new LoginDto("admin", "admin"),
                        MediaType.APPLICATION_JSON));
        token = "Bearer " + response.readEntity(JwtTokenDto.class).getToken();
    }

    @Test
    public void getAll() {
        WebTarget users = apiTarget.path("users/all");
        Response response = getResponse(users);
        System.out.println(response.getEntity());
        PublicUserDto[] publicUserDtoArray = response.readEntity(
                PublicUserDto[].class);

        Assertions.assertNotNull(publicUserDtoArray);
        Assertions.assertTrue(Arrays.stream(publicUserDtoArray).anyMatch(
                publicUserDto -> publicUserDto.getLogin().equals("admin")));
        Assertions.assertEquals(200, response.getStatus());

    }

    private Response getResponse(WebTarget webTarget) {
        Invocation.Builder invocationBuilder = webTarget.request(
                MediaType.APPLICATION_JSON).header("Authorization", token);
        return invocationBuilder.get();
    }

    @Test
    public void getByLogin() {
        WebTarget findByLogin = apiTarget.path("users/admin");
        Response response = findByLogin.request(MediaType.APPLICATION_JSON)
                .header("Authorization", token).get();
        UserDtoForCreate admin = response.readEntity(UserDtoForCreate.class);

        Assertions.assertNotNull(admin);
        Assertions.assertNotNull(admin.getRole());
        Assertions.assertNotNull(admin.getEmail());
    }

    @Test
    public void createUser() {

        UserDtoForCreate userDto = new UserDtoForCreate("admin12", "pass",
                "pass", "email@email.com", "lastname", "firstname",
                Date.valueOf("2000-03-10"), "ADMIN");

        Map<String, String> problems = postUser(userDto);

        Assertions.assertNull(problems);
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> postUser(UserDtoForCreate userDto) {
        WebTarget postUser = apiTarget.path("users");
        Response response = postUser.request(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .post(Entity.entity(userDto, MediaType.APPLICATION_JSON));
        return response.readEntity(Map.class);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void editUser() {

        UserDtoForCreate editUserDto = new UserDtoForCreate("admin12", "pass",
                "pass", "email11@email.com", "FIRST", "LAST",
                Date.valueOf("2001-01-01"), "USER");

        Response response = putUser(editUserDto);
        Map<String, String> problems = (Map<String, String>) response.readEntity(
                Map.class);

        Assertions.assertNull(problems);
        Assertions.assertEquals(editUserDto.getEmail(), "email11@email.com");
        Assertions.assertEquals(editUserDto.getFirstName(), "FIRST");
        Assertions.assertEquals(editUserDto.getLastName(), "LAST");
        Assertions.assertEquals(editUserDto.getRole(), "USER");
        Assertions.assertEquals(editUserDto.getBirthday(),
                Date.valueOf("2001-01-01"));
    }

    private Response putUser(UserDtoForCreate userDtoForCreate) {
        WebTarget putUserTarget = apiTarget.path("users");
        return putUserTarget.request(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .put(Entity.entity(userDtoForCreate,
                        MediaType.APPLICATION_JSON));
    }

    @Test
    public void deleteUser() {
        Response response = deleteUser("admin12");
        Assertions.assertEquals(response.getStatus(), HttpStatus.OK_200);
    }

    private Response deleteUser(String login) {
        WebTarget deleteUserTarget = apiTarget.path("users/" + login);
        return deleteUserTarget.request(MediaType.APPLICATION_JSON)
                .header("Authorization", token).delete();
    }
}