package PlatziFakeStore.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * ConfigManager
 * - Loads environment-specific properties
 * - Provides dedicated getters for each configuration key
 * - Supports system property overrides (Maven/CI)
 */
public final class ConfigManager {
    // TODO : Edit file
    private static final Properties properties = new Properties();


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

    // -----------------------------------------------------------------
    // Dedicated Getters for Each Key
    // -----------------------------------------------------------------

    public static String getBaseUrl() {
        return getProperty("base.url");
    }

    public static String getBackupBaseUrl() {
        return getProperty("base.url.backup");
    }

    public static String getClientName() {
        return getProperty("client.name");
    }

    public static String getClientEmail() {
        return getProperty("client.email");
    }

    public static String getDefaultCustomerName() {
        return getProperty("default.customer.name");
    }

    public static int getDefaultProductId() {
        return Integer.parseInt(getProperty("default.product.id"));
    }

    public static int getDefaultQuantity() {
        return Integer.parseInt(getProperty("default.quantity"));
    }

    public static String getDefaultComment() {
        return getProperty("default.comment");
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

    public static String getEnvironment() {
        return getProperty("env.name");
    }

    public static int getMaxRetryAttempts() {
        return Integer.parseInt(getProperty("retry.max.attempts"));
    }

    public static int getRetryDelay() {
        return Integer.parseInt(getProperty("retry.delay.ms"));
    }

    public static boolean isRetryEnabled() {
        return Boolean.parseBoolean(getProperty("retry.enabled"));
    }

    // -----------------------------------------------------------------
    // Internal helper to get property with system override
    // -----------------------------------------------------------------
    private static String getProperty(String key) {
        String value = System.getProperty(key, properties.getProperty(key));
        if (value == null) {
            throw new RuntimeException("❌ Missing configuration key: " + key);
        }
        return value;
    }
}
