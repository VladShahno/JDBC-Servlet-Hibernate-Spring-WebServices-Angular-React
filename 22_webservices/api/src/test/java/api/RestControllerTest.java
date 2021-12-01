package api;

import com.fasterxml.jackson.databind.ObjectMapper;

import entity.Role;
import entity.User;

import org.eclipse.jetty.http.HttpStatus;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RestControllerTest {

    private static final String REST_ENDPOINT = "http://localhost:8080/users";

    private Client client;

    private static User testUser;

    private static ObjectMapper objectMapper;

    @BeforeClass
    public static void beforeClass() {
        objectMapper = new ObjectMapper();
        testUser = new User();

        testUser.setEmail("Parker@gmail.com");
        testUser.setFirstName("Peter");
        testUser.setLastName("Parker");
        testUser.setLogin("SpiderMan");
        testUser.setPassword("admin");
        testUser.setPasswordConfirm("admin");
        testUser.setBirthday(new Date(1999 - 10 - 3));
        testUser.setRole(new Role(2L, "ADMIN"));
    }

    @Before
    public void setUp() {
        client = ClientBuilder.newClient();
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(
                "admin", "admin");
        client.register(feature);
    }

    @After
    public void tearUp() {
        client.close();
    }

    @Test
    public void testACreateUser() {

        String jsonDataCreate = "{\n" + " \"birthday\": \"1999-03-10\",\n"
                + "   \"password\": \"admin\",\n" + "\"role\": {\n"
                + "   \"id\": 2,\n" + "\"name\": \"ADMIN\"\n" + "},\n"
                + "\"passwordConfirm\":\"admin\",\n"
                + "   \"lastName\":\"Parker\",\n"
                + "   \"login\": \"SpiderMan\",\n"
                + "   \"firstName\": \"Peter\",\n"
                + "   \"email\": \"Parker@gmail.com\"\n" + "}";
        Response response = client.target(REST_ENDPOINT + "/create")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(jsonDataCreate,
                        MediaType.APPLICATION_JSON));
        System.out.println(response.readEntity(String.class));
        Assert.assertEquals(HttpStatus.CREATED_201, response.getStatus());
    }

    @Test
    public void testBFindByLogin() throws IOException {

        String response = client.target(REST_ENDPOINT + "/findByLogin")
                .path(testUser.getLogin()).request(MediaType.APPLICATION_JSON)
                .get(String.class);
        Assert.assertTrue(response.length() > 0);
        assertUserEquals(testUser, jsonResponseToUser(response));
    }

    @Test
    public void testCFindByEmail() throws IOException {

        String response = client.target(REST_ENDPOINT + "/findByEmail")
                .path(testUser.getEmail()).request(MediaType.APPLICATION_JSON)
                .get(String.class);
        Assert.assertTrue(response.length() > 0);
        assertUserEquals(testUser, jsonResponseToUser(response));
    }

    @Test
    public void testDUpdateUser() {

        String jsonDataUpdate = "{\n" + " \"birthday\": \"1999-03-10\",\n"
                + "    \"password\": \"qwerty\",\n" + "    \"role\": 1,\n"
                + "    \"passwordConfirm\": \"qwerty\",\n"
                + "    \"lastName\": \"Skaletto\",\n"
                + "    \"login\": \"SpiderMan\",\n"
                + "    \"firstName\": \"Vito\",\n"
                + "    \"email\": \"Vito@gmail.com\"\n" + "}";
        Response response = client.target(REST_ENDPOINT + "/update")
                .path(testUser.getLogin()).request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(jsonDataUpdate, MediaType.APPLICATION_JSON));
        Assert.assertEquals(response.getStatus(), HttpStatus.OK_200);
    }

    @Test
    public void testEGetAll() throws IOException {

        String response = client.target(REST_ENDPOINT + "/all")
                .request(MediaType.APPLICATION_JSON).get(String.class);
        Map<String, Map<String, String>> map = objectMapper.readValue(response,
                Map.class);
        List<User> users = new ArrayList<>();
        for (String key : map.keySet()) {
            users.add(jsonResponseToUser(map.get(key)));
        }
        Assert.assertNotNull(users);
        Assert.assertTrue(users.size() > 0);
    }

    @Test
    public void testGRemove() {

        Response response = client.target(REST_ENDPOINT + "/delete")
                .path(testUser.getLogin()).request(MediaType.APPLICATION_JSON)
                .delete();
        Assert.assertEquals(response.getStatus(), HttpStatus.OK_200);
    }

    private void assertUserEquals(User testUser, User fromDatabase) {

        Assert.assertEquals(testUser.getEmail(), fromDatabase.getEmail());
        Assert.assertEquals(testUser.getFirstName(),
                fromDatabase.getFirstName());
        Assert.assertEquals(testUser.getLastName(), fromDatabase.getLastName());
        Assert.assertEquals(testUser.getLogin(), fromDatabase.getLogin());
    }

    private User jsonResponseToUser(String response) throws IOException {

        Map<String, String> map = objectMapper.readValue(response, Map.class);
        User user = new User();
        user.setLastName(map.get("last_name"));
        user.setFirstName(map.get("first_name"));
        user.setLogin(map.get("login"));
        user.setEmail(map.get("email"));
        user.setPassword(map.get("password"));
        user.setPasswordConfirm(map.get("passwordConfirm"));
        user.setBirthday(Date.valueOf(map.get("birthday")));
        return user;
    }

    private User jsonResponseToUser(Map<String, String> response) {

        User user = new User();
        user.setLastName(response.get("last_name"));
        user.setFirstName(response.get("first_name"));
        user.setLogin(response.get("login"));
        user.setPassword(response.get("password"));
        user.setEmail(response.get("email"));
        user.setBirthday(Date.valueOf(response.get("birthday")));
        return user;
    }
}