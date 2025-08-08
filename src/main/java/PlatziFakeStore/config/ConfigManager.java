package PlatziFakeStore.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * ConfigManager
 * --------------------------------------------------
 * - Loads environment-specific properties at runtime
 * - Supports system property overrides (Maven/CI)
 * - Provides typed getters for configuration keys
 * - Fully integrated with APIResources & BaseAPI
 * - Centralized source of truth for all configuration
 *
 * Usage:
 *   ConfigManager.getBaseUrl()
 *   ConfigManager.getDefaultEmail()
 *
 * Environment Switching:
 *   mvn clean test -Denv=staging
 */
public final class ConfigManager {

    private static final Properties properties = new Properties();
    private static final String DEFAULT_ENV = "dev"; // fallback if not provided

    private ConfigManager() {
        // Prevent instantiation
    }

    static {
        try (InputStream input = new FileInputStream("src/test/resources/config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file: " + e.getMessage());
        }
    }



    // ======================================================
    // BASE & ENVIRONMENT CONFIG
    // ======================================================
    public static String getBaseUrl() {
        return getProperty("base.url");
    }

    public static String getEnvironmentName() {
        return getProperty("env.name");
    }

    public static boolean isRequestLoggingEnabled() {
        return Boolean.parseBoolean(getProperty("log.request"));
    }

    public static boolean isResponseLoggingEnabled() {
        return Boolean.parseBoolean(getProperty("log.response"));
    }

    public static int getConnectionTimeout() {
        return Integer.parseInt(getProperty("timeout.connection"));
    }

    public static int getReadTimeout() {
        return Integer.parseInt(getProperty("timeout.read"));
    }

    public static long getMaxResponseTimeout() {
        return Long.parseLong(getProperty("timeout.response"));
    }

    // ======================================================
    // AUTHENTICATION
    // ======================================================
    public static String getDefaultEmail() {
        return getProperty("default.email");
    }

    public static String getDefaultPassword() {
        return getProperty("default.password");
    }

    // ======================================================
    // API ENDPOINTS (SYNCED WITH APIResources)
    // ======================================================
    public static String getProductsEndpoint() {
        return getProperty("endpoint.products");
    }

    public static String getCategoriesEndpoint() {
        return getProperty("endpoint.categories");
    }

    public static String getUsersEndpoint() {
        return getProperty("endpoint.users");
    }

    public static String getLoginEndpoint() {
        return getProperty("endpoint.auth.login");
    }

    public static String getRefreshTokenEndpoint() {
        return getProperty("endpoint.auth.refresh-token");
    }

    public static String getProfileEndpoint() {
        return getProperty("endpoint.auth.profile");
    }

    public static String getFilesEndpoint() {
        return getProperty("endpoint.files");
    }

    // ======================================================
    // RETRY / STABILITY SETTINGS
    // ======================================================
    public static int getMaxRetryAttempts() {
        return Integer.parseInt(getProperty("retry.max.attempts"));
    }

    public static int getRetryDelayMs() {
        return Integer.parseInt(getProperty("retry.delay.ms"));
    }

    public static boolean isRetryEnabled() {
        return Boolean.parseBoolean(getProperty("retry.enabled"));
    }

    // ======================================================
    // INTERNAL HELPER
    // ======================================================
    private static String getProperty(String key) {
        String value = System.getProperty(key, properties.getProperty(key));
        if (value == null || value.isBlank()) {
            throw new RuntimeException("❌ Missing configuration key: " + key);
        }
        return value.trim();
    }
}
