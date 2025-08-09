package PlatziFakeStore.clients;

import PlatziFakeStore.base.APIResources;
import PlatziFakeStore.base.BaseAPI;
import PlatziFakeStore.models.request.categories.CreateCategoryRequest;
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

    public Response getCategoryBySlug(String categorySlug) {
        return given()
                .spec(BaseAPI.withPathParams(
                        java.util.Map.of("slug", String.valueOf(categorySlug))
                ))
        .when()
                .get(APIResources.GET_CATEGORY_BY_SLUG.getResource())
        .then()
                .spec(BaseAPI.ok200())
                .extract()
                .response();
    }

    public Response getCategoryByNonExistentSlug(String categorySlug) {
        return given()
                .spec(BaseAPI.withPathParams(
                        java.util.Map.of("slug", String.valueOf(categorySlug))
                ))
        .when()
                .get(APIResources.GET_CATEGORY_BY_SLUG.getResource())
                .then()
        .spec(BaseAPI.badRequest400())
                .extract()
                .response();
    }

    public Response createCategory(CreateCategoryRequest categoryPayload) {
        return given()
                .spec(BaseAPI.getRequestSpec())
                .body(categoryPayload)
        .when()
                .post(APIResources.CREATE_CATEGORY.getResource())
        .then()
                .spec(BaseAPI.created201())
                .extract()
                .response();
    }

    public Response updateCategoryById (int categoryId, CreateCategoryRequest updatePayload) {
        return given()
                .spec(BaseAPI.withPathParams(
                        java.util.Map.of("id", String.valueOf(categoryId))
                ))
                .body(updatePayload)
        .when()
                .put(APIResources.UPDATE_CATEGORY.getResource())
        .then()
                .spec(BaseAPI.ok200())
                .extract()
                .response();
    }

}
