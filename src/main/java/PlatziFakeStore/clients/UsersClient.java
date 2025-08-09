package PlatziFakeStore.clients;

import PlatziFakeStore.base.APIResources;
import PlatziFakeStore.base.BaseAPI;
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

}
