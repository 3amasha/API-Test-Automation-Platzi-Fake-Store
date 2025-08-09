package PlatziFakeStore.tests.products;

import PlatziFakeStore.base.BaseAPI;
import PlatziFakeStore.clients.ProductsClient;
import PlatziFakeStore.models.response.Product;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Instant;
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
    private int updateProductId = 1;


    @BeforeClass
    public void setup() {
        productsClient = new ProductsClient();
    }

    /* ***********************************************************************************************
     * Get all products
     **************************************************************************************************/
    @Test(priority = 0)
    public void aTestGetAllProductsReturnsList() {
        Response response = productsClient.getAllProducts();

        Assert.assertEquals(response.statusCode(), 200, "Expected HTTP 200 OK");

        List<Product> products = response.jsonPath().getList("", Product.class);

        Assert.assertTrue(products.size() >= 10, "Expected at least 10 products");

        Product firstProduct = products.get(0);
        validProductId = firstProduct.getId();
        validProductSlug = products.get(1).getSlug();
        deleteProductId = products.get(2).getId();
        updateProductId= products.get(3).getId();

        Assert.assertTrue(firstProduct.getId() > 0, "Product ID should be positive");
        Assert.assertNotNull(firstProduct.getTitle(), "Product title should not be null");
        Assert.assertNotNull(firstProduct.getCategory(), "Product category should not be null");
    }

    @Test
    public void testGetInvalidEndpointReturns404() {
        Response response = productsClient
                .getAllProducts()
                .thenReturn();


        Response invalidResponse = io.restassured.RestAssured.given()
                .spec(BaseAPI.getRequestSpec())
        .when()
                .get("/products-invalid") // intentionally invalid endpoint
        .then()
                .extract()
                .response();

        Assert.assertEquals(invalidResponse.statusCode(), 404, "Expected HTTP 404 Not Found");
    }

    @Test
    public void testProductsContainMandatoryFields() {
        Response response = productsClient.getAllProducts();
        List<Product> products = response.jsonPath().getList("", Product.class);

        Assert.assertFalse(products.isEmpty(), "Products list should not be empty");

        Product firstProduct = products.get(0);
        validProductId = firstProduct.getId();
        validProductSlug = products.get(1).getSlug();
        deleteProductId = products.get(2).getId();
        updateProductId= products.get(3).getId();

        Assert.assertTrue(firstProduct.getId() > 0, "Product id should be greater than 0");
        Assert.assertNotNull(firstProduct.getTitle(), "Product title should not be null");
        Assert.assertTrue(firstProduct.getPrice() >= 0, "Product price should be >= 0");
        Assert.assertNotNull(firstProduct.getDescription(), "Product description should not be null");
        Assert.assertNotNull(firstProduct.getCategory(), "Product category should not be null");
        Assert.assertNotNull(firstProduct.getImages(), "Product images list should not be null");
    }

    @Test
    public void testGetAllProductsResponseTime() {
        Response response = productsClient.getAllProducts();

        List<Product> products = response.jsonPath().getList("", Product.class);

        Product firstProduct = products.get(0);
        validProductId = firstProduct.getId();
        validProductSlug = products.get(1).getSlug();
        deleteProductId = products.get(2).getId();
        updateProductId= products.get(3).getId();

        Assert.assertTrue(response.time() < 5000, "Response time should be less than 5 seconds");
    }

    /* *************************************************************************************************
    * Get a single product by id
     ***************************************************************************************************/

    @Test
    public void testGetProductByValidId() {
        Response response = productsClient.getProductById(validProductId);

        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");

        Product product = response.as(Product.class);

        Assert.assertEquals(product.getId(), validProductId, "Product ID should match requested ID");
        Assert.assertNotNull(product.getTitle(), "Product title should not be null");
        Assert.assertTrue(product.getPrice() > 0, "Product price should be greater than 0");
        Assert.assertNotNull(product.getCategory(), "Product category should not be null");
        Assert.assertNotNull(product.getImages(), "Product images should not be null");
    }

    @Test
    public void testGetProductByInvalidId() {
        int invalidProductId = 999999; // assuming this ID does not exist

        Response response = productsClient.getProductByInvalidId(invalidProductId);

        Assert.assertEquals(response.getStatusCode(), 400, "Status code should be 400, not find any entity Product matching id");
    }

    @Test
    public void testGetProductByNegativeId() {
        int negativeProductId = -1;

        Response response = productsClient.getProductByInvalidId(negativeProductId);

        Assert.assertEquals(response.getStatusCode(), 400, "Status code should be 400, not find any entity Product matching id");
    }

    /* ***************************************************************************************************
     * Get a single product by slug
     *****************************************************************************************************/

    @Test(priority = 2 )
    public void testGetProductBySlugReturnsProduct() {
        Product product = productsClient.getProductBySlug(validProductSlug).as(Product.class);

        Assert.assertNotNull(product, "Product should not be null");
        Assert.assertEquals(product.getSlug(), validProductSlug, "Slug should match requested slug");
        Assert.assertTrue(product.getId() > 0, "Product ID should be positive");
        Assert.assertNotNull(product.getCategory(), "Product category should not be null");
        Assert.assertNotNull(product.getImages(), "Product images should not be null");
        Assert.assertFalse(product.getImages().isEmpty(), "Product should have at least one image");
    }

    @Test
    public void testGetProductBNotExistentSlug() {
        String notExistentSlug = "not-existent-slug";

        Response response = productsClient.getProductByInvalidSlug(notExistentSlug);
        Assert.assertEquals(response.getStatusCode(), 400, "Status code should be 400, not find any entity Product matching id");
    }

    /* ***************************************************************************************************
     * Update a new product
     **************************************************************************************************/

    @Test
    public void testUpdateProductSuccess() {
        Product updateRequest = new Product();

        updateRequest.setTitle("REST-update-product-title" + Instant.now());
        updateRequest.setPrice(99.99);
        updateRequest.setImages(List.of("https://placehold.co/600x400"));

        Response response = productsClient.updateProductById(updateProductId,updateRequest);
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");

        Product updateResponse = response.as(Product.class);

        Assert.assertEquals(updateRequest.getTitle(), updateResponse.getTitle(), "Title should be updated");
        Assert.assertEquals(updateRequest.getPrice(), updateResponse.getPrice(), "Price should be updated");

    }

    @Test
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

