package PlatziFakeStore.tests.categories;

import PlatziFakeStore.base.APIResources;
import PlatziFakeStore.base.BaseAPI;
import PlatziFakeStore.clients.CategoriesClient;
import PlatziFakeStore.models.request.CreateCategoryRequest;
import PlatziFakeStore.models.response.Category;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Instant;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

public class CategoriesTests {

    private CategoriesClient categoriesClient = new CategoriesClient();
    private int validCategoryId = 1;
    private String validCategorySlug = "clothes";
    private int updateCategoryId = 1;


    @Test
    public void aTestGetAllCategories() {
        Response response = categoriesClient.getAllCategories();

        assertEquals(response.getStatusCode(), 200, "Expected status code 200");

        List<Category> categories = response.jsonPath().getList("", Category.class);
        validCategoryId = categories.get(0).getId();
        validCategorySlug = categories.get(1).getSlug();
        updateCategoryId = categories.get(2).getId();

        assertFalse(categories.isEmpty(), "Expected categories list to be non-empty");

        for (Category category : categories) {
            assertNotNull(category.getName(), "Category name should not be null");
            assertNotNull(category.getSlug(), "Category slug should not be null");
            assertNotNull(category.getImage(), "Category image should not be null");
            assertNotNull(category.getCreationAt(), "Category creationAt should not be null");
            assertNotNull(category.getUpdatedAt(), "Category updatedAt should not be null");
        }
    }

    @Test
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

    @Test
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

    @Test
    public void testGetCategoryById_ValidId() {
        Response response = categoriesClient.getCategoryById(validCategoryId);

        assertEquals(response.getStatusCode(), 200, "Expected status code 200");

        Category category = response.as(Category.class);

        assertNotNull(category.getName(), "Category 'name' should not be null");
        assertNotNull(category.getSlug(), "Category 'slug' should not be null");
        assertNotNull(category.getImage(), "Category 'image' should not be null");
        assertNotNull(category.getCreationAt(), "Category 'creationAt' should not be null");
        assertNotNull(category.getUpdatedAt(), "Category 'updatedAt' should not be null");

        Assert.assertEquals(category.getId(), validCategoryId,"Category ID mismatch");
    }

    @Test
    public void testGetCategoryById_NonExistentId() {
        int invalidCategoryId = 999999;

        Response response = categoriesClient.getCategoryByInvalidId(invalidCategoryId);

        assertEquals(response.getStatusCode(), 400, "Expected status code 400 Bad Request");
    }

    /* ******************************************************************************************************
     * Get a single category by Slug
     *******************************************************************************************************/

    @Test
    public void testGetCategoryByValidSlug() {
        Category category = categoriesClient.getCategoryBySlug(validCategorySlug).as(Category.class);

        Assert.assertEquals(category.getSlug(), validCategorySlug, "Slug should match requested slug");
        Assert.assertNotNull(category.getName(), "Category name should not be null");
        Assert.assertNotNull(category.getImage(), "Category image should not be null");
    }

    @Test
    public void testGetCategoryByNonExistentSlug() {
        String slug = "nonexistent-slug";
        Response response = categoriesClient.getCategoryByNonExistentSlug(slug);

        Assert.assertEquals(response.statusCode(), 400, "Status code should be 404 for non-existent slug");
    }

    /* ***********************************************************************************************************
    * Create a category
    *******************************************************************************************************/

    public void testCreateCategory_ValidData() {
        String name = "Sports Gear Cat 80";
        String image = "https://placehold.co/600x400" + Instant.now();
        CreateCategoryRequest createCategoryPayload = new CreateCategoryRequest(name, image);

        Response response = categoriesClient.createCategory(createCategoryPayload);

        Assert.assertEquals(response.statusCode(), 201, "Status code not 201 created");

        Category CategoryResponse = response.as(Category.class);

        Assert.assertTrue(CategoryResponse.getId() > 0, "ID should be greater than 0");
        Assert.assertEquals(CategoryResponse.getName(), name, "Category name mismatch");
        Assert.assertEquals(CategoryResponse.getImage(), image, "Image URL mismatch");

        String expectedSlug = name.toLowerCase().replaceAll("\\s+", "-");
        Assert.assertTrue(CategoryResponse.getSlug().contains(expectedSlug) , "Slug mismatch");
    }

    /* ***********************************************************************************************************
     * Update a category
     *******************************************************************************************************/

    @Test
    public void testUpdateCategoryById_Success() {
        // Arrange
        int categoryId = updateCategoryId; // Note updateCategoryId: Replace with a valid category ID
        String updatedName = "REST-Update category name " + Instant.now();
        String updatedImage = "https://placeimg.com/640/480/any";

        CreateCategoryRequest updatePayload = new CreateCategoryRequest(updatedName, updatedImage);

        Response response = categoriesClient.updateCategoryById(categoryId, updatePayload);

        Assert.assertEquals(response.getStatusCode(), 200, "Expected 200 OK");

        Category category = response.as(Category.class);

        Assert.assertEquals(category.getId(), categoryId, "Category ID should match the updated one");
        Assert.assertTrue(category.getName().contains("Update category name"), "Category name should match updated value");
        Assert.assertEquals(category.getImage(), updatedImage, "Category image should match updated value");
        Assert.assertNotNull(category.getSlug(), "Slug should not be null");
        Assert.assertFalse(category.getSlug().isEmpty(), "Slug should not be empty");
        Assert.assertNotNull(category.getCreationAt(), "creationAt timestamp should be present");
        Assert.assertNotNull(category.getUpdatedAt(), "updatedAt timestamp should be present");
    }
}
