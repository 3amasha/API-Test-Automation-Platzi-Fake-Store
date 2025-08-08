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
    // INTERNAL HELPER
    // ======================================================
    private static String getProperty(String key) {
        String value = System.getProperty(key, properties.getProperty(key));
        if (value == null || value.isBlank()) {
            throw new RuntimeException("Missing configuration key: " + key);
        }
        return value.trim();
    }
}
