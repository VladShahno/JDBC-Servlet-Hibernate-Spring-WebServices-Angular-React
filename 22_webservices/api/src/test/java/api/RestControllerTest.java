package api;

import com.nixsolutions.com.client.UserClient;
import com.nixsolutions.com.data.PublicUserDto;
import com.nixsolutions.com.data.UserDtoForCreate;
import jakarta.ws.rs.core.Response;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.sql.Date;
import java.util.Arrays;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RestControllerTest {

    private final UserClient userClient = new UserClient();

    @Test
    @Order(4)
    public void shouldReturnAllUserTest() {

        PublicUserDto[] publicUserDtoArray = userClient.getAll();

        Assertions.assertNotNull(publicUserDtoArray);
        Assertions.assertTrue(Arrays.stream(publicUserDtoArray).anyMatch(
                publicUserDto -> publicUserDto.getLogin().equals("admin")));
    }

    @Test
    @Order(3)
    public void shouldFindUserByLoginTest() {

        UserDtoForCreate admin = userClient.getByLogin("admin");

        Assertions.assertNotNull(admin);
        Assertions.assertNotNull(admin.getRole());
        Assertions.assertNotNull(admin.getEmail());
    }

    @Test
    @Order(1)
    public void shouldCreateNewUserTest() {

        UserDtoForCreate userDto = new UserDtoForCreate("JoKo", "jo", "jo",
                "John@email.com", "John", "Konstantin",
                Date.valueOf("2000-03-10"), "ADMIN");

        UserDtoForCreate newUser = userClient.postUser(userDto);

        Assertions.assertNotNull(newUser);
        Assertions.assertEquals(userDto.getEmail(),
                userClient.getByLogin("JoKo").getEmail());
    }

    @Test
    @Order(2)
    public void shouldUpdateUserTest() {

        UserDtoForCreate userDtoForUpdate = new UserDtoForCreate("JoKo", "jo",
                "jo", "newemail@email.com", "NewName", "NewLastName",
                Date.valueOf("2000-03-10"), "USER");

        UserDtoForCreate updatedUser = userClient.putUser(userDtoForUpdate);

        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals(updatedUser.getEmail(),
                userClient.getByLogin("JoKo").getEmail());
        Assertions.assertEquals(updatedUser.getRole(),
                userClient.getByLogin("JoKo").getRole());
    }

    @Test
    @Order(5)
    public void shouldDeleteUserByLogin() {

        int lengthBeforeDelete = userClient.getAll().length;

        Response response = userClient.deleteUser("JoKo");
        Assertions.assertEquals(HttpStatus.OK_200, response.getStatus());

        int lengthAfterDelete = userClient.getAll().length;

        Assertions.assertNotEquals(lengthBeforeDelete, lengthAfterDelete);
    }
}