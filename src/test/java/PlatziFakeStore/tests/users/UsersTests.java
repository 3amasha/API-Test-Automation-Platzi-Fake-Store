package PlatziFakeStore.tests.users;

import PlatziFakeStore.base.APIResources;
import PlatziFakeStore.base.BaseAPI;
import PlatziFakeStore.clients.ProductsClient;
import PlatziFakeStore.clients.UsersClient;
import PlatziFakeStore.models.response.Users;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class UsersTests {
    private UsersClient usersClient;
    private int validUserId = 1;

    @BeforeClass
    public void setup() {
        usersClient = new UsersClient();
    }

    /* ***********************************************************************
     * Get all users
     ************************************************************************/
    @Test
    public void atestGetAllUsersSuccess() {
        Response response = usersClient.getAllUsers();

        List<Users> users = response.jsonPath().getList("", Users.class);

        Assert.assertNotNull(users, "Users list should not be null");
        Assert.assertFalse(users.isEmpty(), "Users list should not be empty");

        Users firstUser = users.getFirst();
        validUserId = users.getFirst().getId();

        Assert.assertNotNull(firstUser.getId(), "User ID should not be null");
        Assert.assertNotNull(firstUser.getEmail(), "User email should not be null");
    }

    @Test
    public void testAllUsersHaveValidEmails() {
        Response response = usersClient.getAllUsers();
        List<Users> users = response.jsonPath().getList("", Users.class);
        validUserId = users.getFirst().getId();

        Assert.assertFalse(users.isEmpty(), "Users list should not be empty");

        for (Users user : users) {
            Assert.assertTrue(user.getEmail().contains("@"),
                    "Invalid email format for user ID: " + user.getId());
        }
    }

    @Test
    public void testAllUsersHaveNonEmptyNames() {
        Response response = usersClient.getAllUsers();
        List<Users> users = response.jsonPath().getList("", Users.class);
        validUserId = users.getFirst().getId();

        for (Users user : users) {
            Assert.assertNotNull(user.getName(), "User name should not be null");
            Assert.assertFalse(user.getName().trim().isEmpty(), "User name should not be empty");
        }
    }

    @Test
    public void testAllUsersHaveValidRoles() {
        Response response = usersClient.getAllUsers();
        List<Users> users = response.jsonPath().getList("", Users.class);
        validUserId = users.getFirst().getId();

        List<String> allowedRoles = List.of("customer", "admin");

        for (Users user : users) {
            Assert.assertTrue(allowedRoles.contains(user.getRole()),
                    "Invalid role for user ID: " + user.getId());
        }
    }

    @Test
    public void testInvalidEndpointReturns404() {
        Response response = given()
                .spec(BaseAPI.getRequestSpec())
        .when()
                .get("/invalid-endpoint")
        .then()
                .extract()
                .response();

        Assert.assertEquals(response.getStatusCode(), 404, "Expected 404 Not Found");
    }

    @Test
    public void testUsersEndpointSupportsJsonResponse() {
        Response response = given()
                .spec(BaseAPI.getRequestSpec())
                .header("Accept", "application/json")
        .when()
                .get(APIResources.GET_ALL_USERS.getResource())
        .then()
                .extract()
                .response();

        Assert.assertEquals(response.getContentType(), "application/json; charset=utf-8",
                "Expected JSON content type");
    }

}
