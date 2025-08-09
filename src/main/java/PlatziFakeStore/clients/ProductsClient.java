package PlatziFakeStore.clients;

import PlatziFakeStore.base.BaseAPI;
import PlatziFakeStore.models.request.products.CreateProductRequest;
import PlatziFakeStore.base.APIResources;
import PlatziFakeStore.models.response.products.Product;
import io.restassured.response.Response;

import java.security.PublicKey;

import static io.restassured.RestAssured.given;


public class ProductsClient {


    public Response createProduct(CreateProductRequest requestBody) {
        return given()
                .spec(BaseAPI.getRequestSpec())
                .body(requestBody)
        .when()
                .post(APIResources.CREATE_PRODUCT.getResource())
        .then()
                .spec(BaseAPI.created201())
                .extract()
                .response();
    }

    public Response getAllProducts() {
        return given()
                .spec(BaseAPI.getRequestSpec())
        .when()
                .get(APIResources.GET_ALL_PRODUCTS.getResource())
        .then()
                .spec(BaseAPI.ok200())
                .extract()
                .response();
    }

    public Response getProductById(int productId) {
        return given()
                .spec(BaseAPI.withPathParams(
                        java.util.Map.of("id", String.valueOf(productId))
                ))
        .when()
                .get(APIResources.GET_PRODUCT_BY_ID.getResource())
        .then()
                .spec(BaseAPI.ok200())
                .extract()
                .response();
    }

    public Response getProductByInvalidId(int productId) {
        return given()
                .spec(BaseAPI.withPathParams(
                        java.util.Map.of("id", String.valueOf(productId))
                ))
        .when()
                .get(APIResources.GET_PRODUCT_BY_ID.getResource())
        .then()
                .spec(BaseAPI.badRequest400())
                .extract()
                .response();
    }

    public Response getProductBySlug(String productSlug) {
        return given()
                .spec(BaseAPI.withPathParams(
                        java.util.Map.of("slug", String.valueOf(productSlug))
                ))
        .when()
                .get(APIResources.GET_PRODUCT_BY_SLUG.getResource())
        .then()
                .spec(BaseAPI.ok200())
                .extract()
                .response();
    }

    public Response getProductByInvalidSlug(String productSlug) {
        return given()
                .spec(BaseAPI.withPathParams(
                        java.util.Map.of("slug", String.valueOf(productSlug))
                ))
        .when()
                .get(APIResources.GET_PRODUCT_BY_SLUG.getResource())
        .then()
                .spec(BaseAPI.badRequest400())
                .extract()
                .response();
    }

    public Response updateProductById (int productId, Product updatePayload) {
        return given()
                .spec(BaseAPI.withPathParams(
                        java.util.Map.of("id", String.valueOf(productId))
                ))
                .body(updatePayload)
        .when()
                .put(APIResources.UPDATE_PRODUCT.getResource())
        .then()
                .spec(BaseAPI.ok200())
                .extract()
                .response();
    }
    public Response updateProductByInvalidId(int productId, Product updatePayload) {
        return given()
                .spec(BaseAPI.withPathParams(
                        java.util.Map.of("id", String.valueOf(productId))
                ))
                .body(updatePayload)
        .when()
                .put(APIResources.UPDATE_PRODUCT.getResource())
        .then()
                .spec(BaseAPI.badRequest400())
                .extract()
                .response();
    }

    public  Response deleteProductById(int productId) {
        return given()
                .spec(BaseAPI.withPathParams(
                        java.util.Map.of("id", String.valueOf(productId))
                ))
        .when()
                .delete(APIResources.DELETE_PRODUCT.getResource())
        .then()
               // .contentType("text/html")
                .extract()
                .response();
    }

    public  Response deleteProductByInvalidId(int productId) {
        return given()
                .spec(BaseAPI.withPathParams(
                        java.util.Map.of("id", String.valueOf(productId))
                ))
        .when()
                .delete(APIResources.DELETE_PRODUCT.getResource())
        .then()
                .spec(BaseAPI.badRequest400())
                .extract()
                .response();
    }
}
