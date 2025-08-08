package PlatziFakeStore.base;


import PlatziFakeStore.auth.TokenManager;
import PlatziFakeStore.config.ConfigManager;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

/**
 * BaseTest
 * - Provides common setup and teardown logic for API tests.
 * - Initializes BaseAPI specs and handles cleanup after tests.
 * - Ensures consistent environment and reusable helpers for all test classes.
 */
public abstract class BaseTest {
// TODO : Edit file
    /**
     * Executes once before the entire test suite.
     * Ideal for global initializations (e.g., logging config, env info).
     */
    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        // Print environment info for reference
        System.out.println("=== Starting API Test Suite ===");
        //System.out.println("Environment: " + ConfigManager.getEnvironment());
        System.out.println("Base URL: " + ConfigManager.getBaseUrl());
    }

    /**
     * Executes before each test class.
     * Initializes reusable request/response specifications.
     */
    @BeforeClass(alwaysRun = true)
    public void setup() {
        // Ensure BaseAPI specs are initialized
        BaseAPI.getRequestSpec();
        BaseAPI.ok200();

        // Pre-fetch token (optional) so first authorized call doesn’t wait
        TokenManager.getAccessToken();
    }

    /**
     * Executes after each test class.
     * Clears cached specs if configuration may change between tests.
     */
    @AfterClass(alwaysRun = true)
    public void teardown() {
        // Clear BaseAPI cached specs to avoid stale configurations
        BaseAPI.resetSpecs();
    }

    /**
     * Helper to get an authorized RequestSpecification.
     * Can be used in tests to avoid calling TokenManager directly.
     */
    protected static io.restassured.specification.RequestSpecification authSpec() {
        return BaseAPI.getAuthorizedRequestSpec();
    }

    /**
     * Helper to get a basic (unauthorized) RequestSpecification.
     */
    protected static io.restassured.specification.RequestSpecification baseSpec() {
        return BaseAPI.getRequestSpec();
    }
}
