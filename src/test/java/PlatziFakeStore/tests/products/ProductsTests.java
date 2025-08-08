package PlatziFakeStore.tests.products;

import PlatziFakeStore.base.BaseAPI;
import PlatziFakeStore.clients.ProductsClient;
import PlatziFakeStore.models.response.products.Product;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Test class to validate /products endpoint via ProductsClient.
 * - Covers positive and negative scenarios.
 * - Uses POJOs for response deserialization.
 */
public class ProductsTests {

    private ProductsClient productsClient;

    @BeforeClass
    public void setup() {
        productsClient = new ProductsClient();
    }

    /**
     * Positive Test: Verify getting all products returns 200 and a non-empty list.
     */
    @Test(description = "GET /products - Positive - returns list of products with 200")
    public void testGetAllProductsReturnsList() {
        Response response = productsClient.getAllProducts();

        // Assert status code 200
        Assert.assertEquals(response.statusCode(), 200, "Expected HTTP 200 OK");

        // Deserialize response JSON array into list of Product POJOs
        List<Product> products = response.jsonPath().getList("", Product.class);

        // Assert that list is not empty (expecting at least 50 products as per docs)
        Assert.assertTrue(products.size() >= 50, "Expected at least 50 products");

        // Optionally check fields of first product
        Product firstProduct = products.get(0);
        Assert.assertTrue(firstProduct.getId() > 0, "Product ID should be positive");
        Assert.assertNotNull(firstProduct.getTitle(), "Product title should not be null");
        Assert.assertNotNull(firstProduct.getCategory(), "Product category should not be null");
    }

    /**
     * Negative Test: Simulate hitting an invalid endpoint returns 404.
     */
    @Test(description = "GET /products-invalid - Negative - 404 Not Found")
    public void testGetInvalidEndpointReturns404() {
        Response response = productsClient
                .getAllProducts() // We'll simulate by calling invalid URL using BaseAPI directly

                // But since ProductsClient doesn't have invalid endpoint method,
                // here is direct RestAssured call for demonstration:
                .thenReturn();

        // Actually to test invalid endpoint, do this:
        Response invalidResponse = io.restassured.RestAssured.given()
                .spec(BaseAPI.getRequestSpec())
                .when()
                .get("/products-invalid") // intentionally invalid endpoint
                .then()
                .extract()
                .response();

        Assert.assertEquals(invalidResponse.statusCode(), 404, "Expected HTTP 404 Not Found");
    }

    @Test(description = "Verify products have mandatory fields")
    public void testProductsContainMandatoryFields() {
        Response response = productsClient.getAllProducts();
        List<Product> products = response.jsonPath().getList("", Product.class);

        Assert.assertFalse(products.isEmpty(), "Products list should not be empty");

        Product firstProduct = products.get(0);

        Assert.assertTrue(firstProduct.getId() > 0, "Product id should be greater than 0");
        Assert.assertNotNull(firstProduct.getTitle(), "Product title should not be null");
        Assert.assertTrue(firstProduct.getPrice() >= 0, "Product price should be >= 0");
        Assert.assertNotNull(firstProduct.getDescription(), "Product description should not be null");
        Assert.assertNotNull(firstProduct.getCategory(), "Product category should not be null");
        Assert.assertNotNull(firstProduct.getImages(), "Product images list should not be null");
    }


    @Test(description = "Verify response time is within acceptable limits")
    public void testGetAllProductsResponseTime() {
        Response response = productsClient.getAllProducts();

        Assert.assertTrue(response.time() < 5000, "Response time should be less than 5 seconds");
    }



}

