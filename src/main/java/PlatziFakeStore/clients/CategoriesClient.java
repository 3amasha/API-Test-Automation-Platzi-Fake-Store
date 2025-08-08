package PlatziFakeStore.clients;

import PlatziFakeStore.base.APIResources;
import PlatziFakeStore.base.BaseAPI;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CategoriesClient {

    public Response getAllCategories() {
        return given()
                .spec(BaseAPI.getRequestSpec())
        .when()
                .get(APIResources.GET_ALL_CATEGORIES.getResource())
        .then()
                .spec(BaseAPI.ok200())
                .extract()
                .response();
    }

    public Response getCategoryById(int categoryId) {
        return given()
                .spec(BaseAPI.withPathParams(
                        java.util.Map.of("id", String.valueOf(categoryId))
                ))
        .when()
                .get(APIResources.GET_CATEGORY_BY_ID.getResource())
        .then()
                .spec(BaseAPI.ok200())
                .extract()
                .response();
    }

    public Response getCategoryByInvalidId(int categoryId) {
        return given()
                .spec(BaseAPI.withPathParams(
                        java.util.Map.of("id", String.valueOf(categoryId))
                ))
        .when()
                .get(APIResources.GET_CATEGORY_BY_ID.getResource())
        .then()
                .spec(BaseAPI.badRequest400())
                .extract()
                .response();
    }

}
