package PlatziFakeStore.clients;

import PlatziFakeStore.base.BaseAPI;
import PlatziFakeStore.models.request.products.CreateProductRequest;
import PlatziFakeStore.base.APIResources;
import PlatziFakeStore.models.response.products.Product;
import io.restassured.response.Response;

import java.security.PublicKey;

import static io.restassured.RestAssured.given;

/**
 * Client for interacting with /products endpoints of the Platzi Fake Store API.
 * Uses BaseAPI for request/response specs.
 */
public class ProductsClient {

    /**
     * Create a product (requires authorization).
     *
     * @param requestBody POJO containing product details.
     * @return Response from API.
     */
    public Response createProduct(CreateProductRequest requestBody) {
        return given()
                .spec(BaseAPI.getRequestSpec())              // authorized request spec
                .body(requestBody)                                     // POJO → JSON
        .when()
                .post(APIResources.CREATE_PRODUCT.getResource())       // POST /products
        .then()
                .spec(BaseAPI.created201())                            // expect 201 Created
                .extract()
                .response();
    }

    /**
     * Get all products (no authorization required).
     *
     * @return Response from API.
     */
    public Response getAllProducts() {
        return given()
                .spec(BaseAPI.getRequestSpec())                        // standard request spec
        .when()
                .get(APIResources.GET_ALL_PRODUCTS.getResource())      // GET /products
        .then()
                .spec(BaseAPI.ok200())                                 // expect 200 OK
                .extract()
                .response();
    }

    /**
     * Get a product by ID (no authorization required).
     *
     * @param productId ID of the product.
     * @return Response from API.
     */
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
                ))                                                     // inject {id}
                .when()
                .get(APIResources.GET_PRODUCT_BY_ID.getResource())     // GET /products/{id}
                .then()
                .spec(BaseAPI.badRequest400())
                .extract()
                .response();
    }

    public Response getProductBySlug(String productSlug) {
        return given()
                .spec(BaseAPI.withPathParams(
                        java.util.Map.of("slug", String.valueOf(productSlug))
                ))                                                     // inject {id}
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
                .get(APIResources.UPDATE_PRODUCT.getResource())
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
                .get(APIResources.UPDATE_PRODUCT.getResource())
        .then()
                .spec(BaseAPI.badRequest400())
                .extract()
                .response();
    }

    public  Response deleteProductById(int productId) {
        return given()
                .spec(BaseAPI.withPathParams(
                        java.util.Map.of("id", String.valueOf(productId))
                ))                                                     // inject {id}
        .when()
                .delete(APIResources.DELETE_PRODUCT.getResource())     // DELETE /products/{id}
        .then()
               // .contentType("text/html")                                 // expect 200 OK
                .extract()
                .response();
    }

    public  Response deleteProductByInvalidId(int productId) {
        return given()
                .spec(BaseAPI.withPathParams(
                        java.util.Map.of("id", String.valueOf(productId))
                ))
        .when()
                .delete(APIResources.DELETE_PRODUCT.getResource())     // DELETE /products/{id}
        .then()
                .spec(BaseAPI.badRequest400())
                .extract()
                .response();
    }
}
