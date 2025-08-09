package PlatziFakeStore.clients;

import PlatziFakeStore.base.APIResources;
import PlatziFakeStore.base.BaseAPI;
import PlatziFakeStore.models.request.CreateCategoryRequest;
import PlatziFakeStore.models.request.CreateUserRequest;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UsersClient {

    public Response getAllUsers() {
        return given()
                .spec(BaseAPI.getRequestSpec())
        .when()
                .get(APIResources.GET_ALL_USERS.getResource())
        .then()
                .spec(BaseAPI.ok200())
                .extract()
                .response();
    }

    public Response getUserById(int userId) {
        return given()
                .spec(BaseAPI.withPathParams(
                        java.util.Map.of("id", String.valueOf(userId))
                ))
        .when()
                .get(APIResources.GET_USER_BY_ID.getResource())
        .then()
                .spec(BaseAPI.ok200())
                .extract()
                .response();
    }

    public Response getUserByInvalidId(int userId) {
        return given()
                .spec(BaseAPI.withPathParams(
                        java.util.Map.of("id", String.valueOf(userId))
                ))
        .when()
                .get(APIResources.GET_USER_BY_ID.getResource())
        .then()
                .spec(BaseAPI.badRequest400())
                .extract()
                .response();
    }

    public Response getUserByIdString(String userId) {
        return given()
                .spec(BaseAPI.withPathParams(
                        java.util.Map.of("id", String.valueOf(userId))
                ))
        .when()
                .get(APIResources.GET_USER_BY_ID.getResource())
        .then()
                .spec(BaseAPI.badRequest400())
                .extract()
                .response();
    }

    public Response createUser(CreateUserRequest userPayload) {
        return given()
                .spec(BaseAPI.getRequestSpec())
                .body(userPayload)
        .when()
                .post(APIResources.CREATE_USER.getResource())
        .then()
                .spec(BaseAPI.created201())
                .extract()
                .response();
    }

    public Response createUserWithBadReq(CreateUserRequest userPayload) {
        return given()
                .spec(BaseAPI.getRequestSpec())
                .body(userPayload)
        .when()
                .post(APIResources.CREATE_USER.getResource())
        .then()
                .spec(BaseAPI.badRequest400())
                .extract()
                .response();
    }

    public Response createUserWithServerError(CreateUserRequest userPayload) {
        return given()
                .spec(BaseAPI.getRequestSpec())
                .body(userPayload)
        .when()
                .post(APIResources.CREATE_USER.getResource())
        .then()
                .spec(BaseAPI.serverError500())
                .extract()
                .response();
    }

    public Response updateUser(int userId, CreateUserRequest updatePayload) {
        return given()
                .spec(BaseAPI.withPathParams(
                        java.util.Map.of("id", String.valueOf(userId))
                ))
                .body(updatePayload)
        .when()
                .put(APIResources.UPDATE_USER.getResource())
        .then()
                .spec(BaseAPI.ok200())
                .extract()
                .response();
    }

    public Response updateUserBadRequest(int userId, CreateUserRequest updatePayload) {
        return given()
                .spec(BaseAPI.withPathParams(
                        java.util.Map.of("id", String.valueOf(userId))
                ))
                .body(updatePayload)
        .when()
                .put(APIResources.UPDATE_USER.getResource())
        .then()
                .spec(BaseAPI.badRequest400())
                .extract()
                .response();
    }

}
