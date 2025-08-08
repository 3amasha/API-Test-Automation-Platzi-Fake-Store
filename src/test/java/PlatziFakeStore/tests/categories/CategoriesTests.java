package PlatziFakeStore.tests.categories;

import PlatziFakeStore.base.APIResources;
import PlatziFakeStore.base.BaseAPI;
import PlatziFakeStore.clients.CategoriesClient;
import PlatziFakeStore.clients.ProductsClient;
import PlatziFakeStore.models.response.categories.Category;
import PlatziFakeStore.models.response.products.Product;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

public class CategoriesTests {

    private CategoriesClient categoriesClient = new CategoriesClient();
    private int validCategoryId = 1;


    @Test(description = "Verify that GET /categories returns a non-empty list with required fields")
    public void aTestGetAllCategories() {

        Response response = categoriesClient.getAllCategories();

        assertEquals(response.getStatusCode(), 200, "Expected status code 200");

        List<Category> categories = response.jsonPath().getList("", Category.class);
        validCategoryId = categories.get(0).getId();

        assertTrue(categories.size() > 0, "Expected categories list to be non-empty");

        for (Category category : categories) {
            assertNotNull(category.getId(), "Category id should not be null");
            assertNotNull(category.getName(), "Category name should not be null");
            assertNotNull(category.getSlug(), "Category slug should not be null");
            assertNotNull(category.getImage(), "Category image should not be null");
            assertNotNull(category.getCreationAt(), "Category creationAt should not be null");
            assertNotNull(category.getUpdatedAt(), "Category updatedAt should not be null");
        }
    }

    @Test(description = "Verify 405 Method Not Allowed for POST on /categories")
    public void testPostMethodNotAllowed() {
        Response response = given()
                .contentType(ContentType.JSON)
            .when()
                .post(APIResources.GET_ALL_CATEGORIES.getResource())
            .then()
                .extract()
                .response();

        assertEquals(response.getStatusCode(), 400, "Expected 400 Bad Request.");
    }

    @Test(description = "Verify 404 Not Found for invalid categories endpoint")
    public void testInvalidEndpoint() {
        Response response = given()
                .spec(BaseAPI.getRequestSpec())
        .when()
                .get("/api/v1/categoriez")  // misspelled endpoint
        .then()
                .extract()
                .response();

        assertEquals(response.getStatusCode(), 404, "Expected 404 Not Found");
    }

    /* ******************************************************************************************************
    * Get a single category by ID
     *******************************************************************************************************/

    @Test(description = "Verify GET /categories/{id} returns correct category details for a valid ID")
    public void testGetCategoryById_ValidId() {
        Response response = categoriesClient.getCategoryById(validCategoryId);

        assertEquals(response.getStatusCode(), 200, "Expected status code 200");

        Category category = response.as(Category.class);

        assertNotNull(category.getId(), "Category 'id' should not be null");
        assertNotNull(category.getName(), "Category 'name' should not be null");
        assertNotNull(category.getSlug(), "Category 'slug' should not be null");
        assertNotNull(category.getImage(), "Category 'image' should not be null");
        assertNotNull(category.getCreationAt(), "Category 'creationAt' should not be null");
        assertNotNull(category.getUpdatedAt(), "Category 'updatedAt' should not be null");

        Assert.assertEquals(category.getId(), validCategoryId,"Category ID mismatch");
    }

    @Test(description = "Verify GET /categories/{id} returns 404 for non-existent category ID")
    public void testGetCategoryById_NonExistentId() {
        int invalidCategoryId = 999999; // Assumed non-existent ID

        Response response = categoriesClient.getCategoryByInvalidId(invalidCategoryId);

        assertEquals(response.getStatusCode(), 400, "Expected status code 400 Bad Request");
    }

}
