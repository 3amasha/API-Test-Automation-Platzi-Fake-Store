package PlatziFakeStore.tests.users;

import PlatziFakeStore.base.APIResources;
import PlatziFakeStore.base.BaseAPI;
import PlatziFakeStore.clients.ProductsClient;
import PlatziFakeStore.clients.UsersClient;
import PlatziFakeStore.models.request.CreateUserRequest;
import PlatziFakeStore.models.response.Users;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Instant;
import java.util.List;

import static io.restassured.RestAssured.given;

public class UsersTests {
    private UsersClient usersClient;
    private int validUserId = 1;
    private int updateUserId = 90;

    @BeforeClass
    public void setup() {
        usersClient = new UsersClient();
    }

    /* ***********************************************************************
     * Get all users
     ************************************************************************/
    @Test
    public void testGetAllUsersSuccess() {
        Response response = usersClient.getAllUsers();

        List<Users> users = response.jsonPath().getList("", Users.class);

        Assert.assertNotNull(users, "Users list should not be null");
        Assert.assertFalse(users.isEmpty(), "Users list should not be empty");

        Users firstUser = users.getFirst();
        validUserId = users.getFirst().getId();
        updateUserId = users.get(4).getId();

        Assert.assertNotNull(firstUser.getId(), "User ID should not be null");
        Assert.assertNotNull(firstUser.getEmail(), "User email should not be null");
    }

    @Test
    public void testAllUsersHaveValidEmails() {
        Response response = usersClient.getAllUsers();
        List<Users> users = response.jsonPath().getList("", Users.class);
        validUserId = users.getFirst().getId();
        updateUserId = users.get(4).getId();

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
        updateUserId = users.get(4).getId();

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
        updateUserId = users.get(4).getId();

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

    /* ***********************************************************************
     * Get user by ID
     ************************************************************************/

    @Test
    public void testGetUserByValidId() {
        //int validUserId = 1; // validUserId: Use an existing user ID from your test data

        Response response = usersClient.getUserById(validUserId);
        Assert.assertEquals(response.statusCode(), 200, "Status code should be 200");

        Users user = response.as(Users.class);

        Assert.assertNotNull(user.getId(), "User ID should not be null");
        Assert.assertEquals(user.getId(), validUserId, "User ID should match the requested ID");
        Assert.assertNotNull(user.getEmail(), "User email should not be null");
        Assert.assertTrue(user.getEmail().contains("@"), "User email should be valid");
        Assert.assertNotNull(user.getName(), "User name should not be null");
        Assert.assertNotNull(user.getRole(), "User role should not be null");
        Assert.assertTrue(user.getAvatar().startsWith("http"), "Avatar should be a valid URL");
    }

    @Test
    public void testGetUserByNonExistingId() {
        int nonExistingUserId = 999999;

        Response response = usersClient.getUserByInvalidId(nonExistingUserId);
        Assert.assertEquals(response.statusCode(), 400, "Status code should be 400");

        String errorMessage = response.jsonPath().getString("message");
        Assert.assertNotNull(errorMessage, "Error message should be returned");
    }

    @Test
    public void testGetUserByInvalidIdFormat() {
        Response response = usersClient.getUserByIdString("abc"); // You'd overload client method for string IDs
        Assert.assertEquals(response.statusCode(), 400, "Status code should be 400");

        String errorMessage = response.jsonPath().getString("message");
        Assert.assertNotNull(errorMessage, "Error message should be returned");
    }

    /* ***********************************************************************
     * Create user
     ************************************************************************/
    @Test
    public void atestCreateUserSuccess() {
        CreateUserRequest requestPayload = new CreateUserRequest();
        requestPayload.setName("Create user Nicolas" + System.currentTimeMillis());
        requestPayload.setEmail("nicolas" + System.currentTimeMillis() + "@mail.com");
        requestPayload.setPassword("1234");
        requestPayload.setAvatar("https://api.lorem.space/image/face?w=640&h=480");

        Response response = usersClient.createUser(requestPayload);
        Assert.assertEquals(response.getStatusCode(), 201, "Status code should be 201");

        Users createdUser = response.as(Users.class);

        Assert.assertNotNull(createdUser.getId(), "User ID should not be null");
        Assert.assertEquals(createdUser.getName(), requestPayload.getName(), "Name should match");
        Assert.assertEquals(createdUser.getEmail(), requestPayload.getEmail(), "Email should match");
        Assert.assertEquals(createdUser.getAvatar(), requestPayload.getAvatar(), "Avatar should match");
    }

    @Test
    public void testCreateUserPasswordTooShort() {
        CreateUserRequest request = new CreateUserRequest();
        request.setName("Short password user");
        request.setEmail("shortpass" + System.currentTimeMillis() + "@mail.com");
        request.setPassword("123");
        request.setAvatar("https://api.lorem.space/image/face?w=640&h=480");

        Response response = usersClient.createUserWithBadReq(request);
        Assert.assertEquals(response.getStatusCode(), 400, "Status code should be 400");

        String message = response.jsonPath().getString("message");
        Assert.assertTrue(message.contains("password must be longer than or equal to 4 characters"),
                "Expected password length validation message");
    }

    @Test(description = "Negative: Name is empty")
    public void testCreateUserEmptyName() {
        CreateUserRequest request = new CreateUserRequest();
        request.setName("");
        request.setEmail("emptyname" + System.currentTimeMillis() + "@mail.com");
        request.setPassword("1234");
        request.setAvatar("https://api.lorem.space/image/face?w=640&h=480");

        Response response = usersClient.createUserWithBadReq(request);
        Assert.assertEquals(response.getStatusCode(), 400, "Status code should be 400");

        String message = response.jsonPath().getString("message");
        Assert.assertTrue(message.contains("name should not be empty"),
                "Expected name empty validation message");
    }

    @Test
    public void testCreateUserInvalidAvatar() {
        CreateUserRequest request = new CreateUserRequest();
        request.setName("Invalid avatar user");
        request.setEmail("invalidavatar" + System.currentTimeMillis() + "@mail.com");
        request.setPassword("1234");
        request.setAvatar("invalid-url");

        Response response = usersClient.createUserWithBadReq(request);
        Assert.assertEquals(response.getStatusCode(), 400, "Status code should be 400");

        String message = response.jsonPath().getString("message[0]");
        Assert.assertTrue(message.contains("avatar must be a URL address"),
                "Expected avatar URL validation message");
    }

    /* ***********************************************************************
     * Update user
     ************************************************************************/

    @Test
    public void testUpdateUserSuccess() {
        int userId = updateUserId; //updateUserId:  existing user
        CreateUserRequest requestBody = new CreateUserRequest();
        requestBody.setName("updated name"+Instant.now());
        requestBody.setEmail("john" + System.currentTimeMillis() + "@mail.com");
        requestBody.setPassword("1234");

        Response response = usersClient.updateUser(userId, requestBody);

        Assert.assertEquals(response.statusCode(), 200, "Status code should be 200");

        Users updatedUser = response.as(Users.class);
        Assert.assertNotNull(updatedUser.getId(), "User ID should not be null");
        Assert.assertEquals(updatedUser.getName(), requestBody.getName(), "Name should match updated value");
        Assert.assertEquals(updatedUser.getEmail(), requestBody.getEmail(), "Email should match updated value");
        Assert.assertEquals(updatedUser.getPassword(), requestBody.getPassword(), "Password should match updated value");
    }

    @Test
    public void testUpdateUserInvalidEmail() {
        int userId = updateUserId; //updateUserId:  existing user
        CreateUserRequest requestBody = new CreateUserRequest();
        requestBody.setName("Valid Name "+Instant.now());
        requestBody.setEmail("invalidEmail"+ System.currentTimeMillis() );
        requestBody.setPassword("1234");

        Response response = usersClient.updateUserBadRequest(userId, requestBody);

        Assert.assertEquals(response.statusCode(), 400, "Status code should be 400");

        String errorMessage = response.jsonPath().getString("message");
        Assert.assertTrue(errorMessage.contains("email must be an email"),
                "Expected validation error for invalid email");
    }

    @Test
    public void testUpdateUserShortPassword() {
        int userId = updateUserId; //updateUserId:  existing user
        CreateUserRequest requestBody = new CreateUserRequest();
        requestBody.setName("Valid Name"+Instant.now());
        requestBody.setEmail("valid"+ System.currentTimeMillis() +"@mail.com");
        requestBody.setPassword("123"); // too short

        Response response = usersClient.updateUserBadRequest(userId, requestBody);

        Assert.assertEquals(response.statusCode(), 400, "Status code should be 400");

        String errorMessage = response.jsonPath().getString("message");
        Assert.assertTrue(errorMessage.contains("password must be longer than or equal to 4 characters"),
                "Expected validation error for short password");
    }

    @Test
    public void testUpdateUserNotFound() {
        // Arrange
        int nonExistingUserId = 999999;
        CreateUserRequest requestBody = new CreateUserRequest();
        requestBody.setName("Does Not Exist");
        requestBody.setEmail("nonexistent@mail.com");
        requestBody.setPassword("1234");

        // Act
        Response response = usersClient.updateUserBadRequest(nonExistingUserId, requestBody);

        // Assert
        Assert.assertEquals(response.statusCode(), 400, "Status code should be 400");

        String errorMessage = response.jsonPath().getString("message");
        Assert.assertNotNull(errorMessage, "Error message should be present for not found user");
    }

}
