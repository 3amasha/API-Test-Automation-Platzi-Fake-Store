package PlatziFakeStore.base;


import PlatziFakeStore.auth.TokenManager;
import PlatziFakeStore.config.ConfigManager;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.Map;

import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.lessThan;

/**
 * BaseAPI centralizes all Rest Assured configuration:
 * - Builds and caches Request/Response specifications per thread.
 * - Uses ConfigManager for dynamic configuration (Base URL, logging, timeouts).
 * - Provides helper methods for authorized/custom header/path parameter requests.
 *
 * NOTE: Clients should NOT extend this class (composition over inheritance).
 */
public class BaseAPI {
    // TODO : Edit file
    // ThreadLocal to ensure thread-safety in parallel executions
    private static final ThreadLocal<RequestSpecification> threadLocalRequestSpec = new ThreadLocal<>();

    // Cached ResponseSpecifications for common scenarios
    private static ResponseSpecification okResponseSpec;
    private static ResponseSpecification createdResponseSpec;
    private static ResponseSpecification noContentResponseSpec;
    private static ResponseSpecification badRequestResponseSpec;
    private static ResponseSpecification unauthorizedResponseSpec;
    private static ResponseSpecification notFoundResponseSpec;

    /* ****************************
     *   RequestSpecFactory
     * ****************************/

    /**
     * Build and return a reusable RequestSpecification for the current thread.
     * Automatically applies:
     * - Base URL from ConfigManager
     * - Content type JSON
     * - Configurable timeouts
     * - Optional request logging
     *
     * @return Thread-local RequestSpecification
     */
    public static RequestSpecification getRequestSpec() {
        if (threadLocalRequestSpec.get() == null) {
            // Build new RequestSpec
            RequestSpecBuilder builder = new RequestSpecBuilder()
                    .setBaseUri(ConfigManager.getBaseUrl())        // Base URL from config
                    .setContentType(ContentType.JSON)              // Default Content-Type
                    .setConfig(RestAssuredConfig.config()          // Apply timeouts
                            .httpClient(HttpClientConfig.httpClientConfig()
                                    .setParam("http.connection.timeout", ConfigManager.getConnectionTimeout())
                                    .setParam("http.socket.timeout", ConfigManager.getReadTimeout())
                            ));

            // Enable request logging if configured
            if (ConfigManager.isRequestLoggingEnabled()) {
                builder.log(LogDetail.ALL);
            }

            // Store in ThreadLocal
            threadLocalRequestSpec.set(builder.build());
        }

        return threadLocalRequestSpec.get();
    }

    /**
     * Build and return an authorized RequestSpecification with Bearer token.
     *
     * @return Authorized RequestSpecification
     */
    public static RequestSpecification getAuthorizedRequestSpec() {
        return with()
                .spec(getRequestSpec())
                .header("Authorization", "Bearer " + TokenManager.getToken());
    }

    /**
     * Build a RequestSpecification with custom headers.
     * Does NOT affect the cached base spec (safe for parallel use).
     *
     * @param headerName  Header key
     * @param headerValue Header value
     * @return RequestSpecification with custom header
     */
    public static RequestSpecification getCustomHeaderRequestSpec(String headerName, String headerValue) {
        return with()
                .spec(getRequestSpec())
                .header(headerName, headerValue);
    }

    /**
     * Build a RequestSpecification with multiple headers (dynamic map).
     *
     * @param headers Map of header key-value pairs
     * @return RequestSpecification with headers applied
     */
    public static RequestSpecification withHeaders(Map<String, String> headers) {
        return with()
                .spec(getRequestSpec())
                .headers(headers);
    }

    /**
     * Build a RequestSpecification with dynamic path parameters.
     *
     * @param pathParams Map of path parameter key-value pairs
     * @return RequestSpecification with path params applied
     */
    public static RequestSpecification withPathParams(Map<String, String> pathParams) {
        return with()
                .spec(getRequestSpec())
                .pathParams(pathParams);
    }

    /* ****************************
     *   ResponseSpecFactory
     * ****************************/

    /**
     * Response spec expecting 200 OK
     */
    public static ResponseSpecification getOkResponseSpec() {
        if (okResponseSpec == null) {
            ResponseSpecBuilder builder = new ResponseSpecBuilder()
                    .expectStatusCode(200)
                    .expectContentType(ContentType.JSON)
                    .expectResponseTime(lessThan(3000L)); // 3s max

            if (ConfigManager.isResponseLoggingEnabled()) {
                builder.log(LogDetail.ALL);
            }

            okResponseSpec = builder.build();
        }
        return okResponseSpec;
    }

    /**
     * Response spec expecting 201 Created
     */
    public static ResponseSpecification getCreatedResponseSpec() {
        if (createdResponseSpec == null) {
            ResponseSpecBuilder builder = new ResponseSpecBuilder()
                    .expectStatusCode(201)
                    .expectContentType(ContentType.JSON)
                    .expectResponseTime(lessThan(3000L));

            if (ConfigManager.isResponseLoggingEnabled()) {
                builder.log(LogDetail.ALL);
            }

            createdResponseSpec = builder.build();
        }
        return createdResponseSpec;
    }

    /**
     * Response spec expecting 204 No Content
     */
    public static ResponseSpecification getNoContentResponseSpec() {
        if (noContentResponseSpec == null) {
            ResponseSpecBuilder builder = new ResponseSpecBuilder()
                    .expectStatusCode(204)
                    .expectResponseTime(lessThan(3000L));

            if (ConfigManager.isResponseLoggingEnabled()) {
                builder.log(LogDetail.ALL);
            }

            noContentResponseSpec = builder.build();
        }
        return noContentResponseSpec;
    }

    /**
     * Response spec expecting 400 Bad Request
     */
    public static ResponseSpecification getBadRequestResponseSpec() {
        if (badRequestResponseSpec == null) {
            ResponseSpecBuilder builder = new ResponseSpecBuilder()
                    .expectStatusCode(400)
                    .expectContentType(ContentType.JSON)
                    .expectResponseTime(lessThan(3000L));

            if (ConfigManager.isResponseLoggingEnabled()) {
                builder.log(LogDetail.ALL);
            }

            badRequestResponseSpec = builder.build();
        }
        return badRequestResponseSpec;
    }

    /**
     * Response spec expecting 401 Unauthorized
     */
    public static ResponseSpecification getUnauthorizedResponseSpec() {
        if (unauthorizedResponseSpec == null) {
            ResponseSpecBuilder builder = new ResponseSpecBuilder()
                    .expectStatusCode(401)
                    .expectContentType(ContentType.JSON)
                    .expectResponseTime(lessThan(3000L));

            if (ConfigManager.isResponseLoggingEnabled()) {
                builder.log(LogDetail.ALL);
            }

            unauthorizedResponseSpec = builder.build();
        }
        return unauthorizedResponseSpec;
    }

    /**
     * Response spec expecting 404 Not Found
     */
    public static ResponseSpecification getNotFoundResponseSpec() {
        if (notFoundResponseSpec == null) {
            ResponseSpecBuilder builder = new ResponseSpecBuilder()
                    .expectStatusCode(404)
                    .expectContentType(ContentType.JSON)
                    .expectResponseTime(lessThan(3000L));

            if (ConfigManager.isResponseLoggingEnabled()) {
                builder.log(LogDetail.ALL);
            }

            notFoundResponseSpec = builder.build();
        }
        return notFoundResponseSpec;
    }

    /**
     * Utility to clear cached specs (useful if config changes at runtime)
     */
    public static void resetSpecs() {
        threadLocalRequestSpec.remove();
        okResponseSpec = null;
        createdResponseSpec = null;
        noContentResponseSpec = null;
        badRequestResponseSpec = null;
        unauthorizedResponseSpec = null;
        notFoundResponseSpec = null;
    }
}
