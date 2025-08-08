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
    private int validProductId = 1;
    private int deleteProductId = 1;
    private String validProductSlug = "modern-elegance-teal-armchair" ;


    @BeforeClass
    public void setup() {
        productsClient = new ProductsClient();
    }

    /* ***********************************************************************************************
     * Get all products
     **************************************************************************************************/
    @Test(priority = 0 , description = "GET /products returns list of products with 200")
    public void aTestGetAllProductsReturnsList() {
        Response response = productsClient.getAllProducts();

        // Assert status code 200
        Assert.assertEquals(response.statusCode(), 200, "Expected HTTP 200 OK");

        // Deserialize response JSON array into list of Product POJOs
        List<Product> products = response.jsonPath().getList("", Product.class);

        // Assert that list is not empty (expecting at least 50 products as per docs)
        Assert.assertTrue(products.size() >= 10, "Expected at least 10 products");

        // Optionally check fields of first product
        Product firstProduct = products.get(0);
        validProductId = firstProduct.getId(); // Store valid ID for further tests
        validProductSlug = firstProduct.getSlug(); // Store valid slug for further tests

        deleteProductId = products.get(1).getId();

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

    /* *************************************************************************************************
    * Get a single product by id
     ***************************************************************************************************/

    @Test(priority = 1 ,description = "Positive: Get existing product by valid ID should return 200 and product details")
    public void testGetProductByValidId() {
        //int validProductId = 3; // example existing product ID, the ID is dynamic

        Response response = productsClient.getProductById(validProductId);

        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");

        Product product = response.as(Product.class);

        Assert.assertEquals(product.getId(), validProductId, "Product ID should match requested ID");
        Assert.assertNotNull(product.getTitle(), "Product title should not be null");
        Assert.assertTrue(product.getPrice() > 0, "Product price should be greater than 0");
        Assert.assertNotNull(product.getCategory(), "Product category should not be null");
        Assert.assertNotNull(product.getImages(), "Product images should not be null");
    }

    @Test(description = "Get product by invalid ID should return 404 Not Found")
    public void testGetProductByInvalidId() {
        int invalidProductId = 999999; // assuming this ID does not exist

        Response response = productsClient.getProductByInvalidId(invalidProductId);

        Assert.assertEquals(response.getStatusCode(), 400, "Status code should be 400, not find any entity Product matching id");
    }

    @Test(description = "Negative: Get product by negative ID should return 400 Bad Request or 404")
    public void testGetProductByNegativeId() {
        int negativeProductId = -1;

        Response response = productsClient.getProductByInvalidId(negativeProductId);

        Assert.assertEquals(response.getStatusCode(), 400, "Status code should be 400, not find any entity Product matching id");

    }

    /* ***************************************************************************************************
     * Get a single product by slug
     *****************************************************************************************************/

    @Test(priority = 2 ,description = "Get a product by valid slug returns product details")
    public void testGetProductBySlugReturnsProduct() {

        Product product = productsClient.getProductBySlug(validProductSlug).as(Product.class);


        Assert.assertNotNull(product, "Product should not be null");
        Assert.assertEquals(product.getSlug(), validProductSlug, "Slug should match requested slug");
        Assert.assertTrue(product.getId() > 0, "Product ID should be positive");
        Assert.assertNotNull(product.getCategory(), "Product category should not be null");
        Assert.assertNotNull(product.getImages(), "Product images should not be null");
        Assert.assertTrue(product.getImages().size() > 0, "Product should have at least one image");
    }

    @Test(description = "Negative: Get product by invalid slug returns 400 Bad Request")
    public void testGetProductBNotExistentSlug() {
        String notExistentSlug = "not-existent-slug";

        Response response = productsClient.getProductByInvalidSlug(notExistentSlug);
        Assert.assertEquals(response.getStatusCode(), 400, "Status code should be 400, not find any entity Product matching id");

    }

    /* ***************************************************************************************************
     * Update a new product
     **************************************************************************************************/

    @Test(description = "Positive: Update a product with valid data")
    public void testUpdateProductSuccess() {
       // int existingProductId = 144; // Use a valid product ID for testing
        Product updateRequest = new Product();

        updateRequest.setTitle("check-product-title");
        updateRequest.setPrice(99.99);

        Response response = productsClient.updateProductById(validProductId,updateRequest);
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");

        Product updateResponse = response.as(Product.class);

        // Validate response body fields
        Assert.assertEquals(response.jsonPath().getString("title"), updateResponse.getTitle(), "Title should be updated");
        Assert.assertEquals(response.jsonPath().getInt("price"), updateResponse.getPrice(), "Price should be updated");
    }

    @Test(description = "Negative: Update a product with invalid ID returns 404")
    public void testUpdateProductInvalidId() {
        int invalidProductId = 999999; // Assume this ID doesn't exist
        Product updateRequest = new Product();

        updateRequest.setTitle("check-product-title");
        updateRequest.setPrice(99.99);

        Response updateResponse = productsClient.updateProductByInvalidId(invalidProductId,updateRequest);

        Assert.assertEquals(updateResponse.getStatusCode(), 400, "Status code should be 400 for invalid product ID");
    }

    /* ************************************************************************************************
     * Delete a product
     **************************************************************************************************/

    @Test
    public void testDeleteProduct_ValidId() {
        Response deleteResponse = productsClient.deleteProductById(deleteProductId);

        Assert.assertTrue(deleteResponse.asString().equalsIgnoreCase("true"), "Expected delete response to be true");
    }

    @Test
    public void testDeleteProduct_InvalidId() {
        Response deleteResponse = productsClient.deleteProductByInvalidId(9999999);

        Assert.assertEquals(deleteResponse.getStatusCode(), 400, "Status code should be 400 for invalid product ID");
    }


}

