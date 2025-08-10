package PlatziFakeStore.base;


import PlatziFakeStore.auth.TokenManager;
import PlatziFakeStore.config.ConfigManager;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.io.File;

/**
 * BaseTest
 * - Provides common setup and teardown logic for API tests.
 * - Initializes BaseAPI specs and handles cleanup after tests.
 * - Ensures consistent environment and reusable helpers for all test classes.
 */
public abstract class BaseTest {
// TODO : Edit file

    @BeforeSuite(alwaysRun = true) // Runs before all tests in the suite
    public void cleanAllureResults() {
        File allureResults = new File("allure-results");

        if (allureResults.exists() && allureResults.isDirectory()) {
            deleteDirectory(allureResults);
            System.out.println("✅ Cleared: target/allure-results");
        } else {
            System.out.println("ℹ No allure-results folder found to delete.");
        }
    }

    // Recursive delete method
    private void deleteDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                }
                file.delete();
            }
        }
        directory.delete();
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

}
