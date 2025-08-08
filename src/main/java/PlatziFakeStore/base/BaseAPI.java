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
 * BaseAPI centralizes Rest Assured setup:
 * - Thread-safe RequestSpecification caching
 * - Common ResponseSpecifications for standard status codes
 * - Support for authorized, custom-header, path-parameter, and query-parameter requests
 * - Configurable timeouts and logging
 *
 * Clients should NOT extend this class — use composition.
 */
public class BaseAPI {

    private static final ThreadLocal<RequestSpecification> threadLocalRequestSpec = new ThreadLocal<>();

    // Cached ResponseSpecifications
    private static ResponseSpecification okSpec;
    private static ResponseSpecification createdSpec;
    private static ResponseSpecification badRequestSpec;
    private static ResponseSpecification unauthorizedSpec;
    private static ResponseSpecification forbiddenSpec;
    private static ResponseSpecification notFoundSpec;
    private static ResponseSpecification serverErrorSpec;

    /* ****************************
     *  Request Specification
     * ****************************/

    public static RequestSpecification getRequestSpec() {
        if (threadLocalRequestSpec.get() == null) {
            RequestSpecBuilder builder = new RequestSpecBuilder()
                    .setBaseUri(ConfigManager.getBaseUrl())
                   // .setContentType(ContentType.JSON)
                    .setConfig(RestAssuredConfig.config().httpClient(
                            HttpClientConfig.httpClientConfig()
                                    .setParam("http.connection.timeout", ConfigManager.getConnectionTimeout())
                                    .setParam("http.socket.timeout", ConfigManager.getReadTimeout())
                    ));

            if (ConfigManager.isRequestLoggingEnabled()) {
                builder.log(LogDetail.ALL);
            }

            threadLocalRequestSpec.set(builder.build());
        }
        return threadLocalRequestSpec.get();
    }

    /** Generic request with Authorization header */
    /*public static RequestSpecification getAuthorizedRequestSpec(String clientEmail, String clientPassword) {
        return with()
                .spec(getRequestSpec())
                .header("Authorization", "Bearer " + TokenManager.getAccessToken(clientEmail, clientPassword));
    }*/


    /** Request with custom headers */
    public static RequestSpecification withHeaders(Map<String, String> headers) {
        return with()
                .spec(getRequestSpec())
                .headers(headers);
    }

    /** Request with path parameters */
    public static RequestSpecification withPathParams(Map<String, String> pathParams) {
        return with()
                .spec(getRequestSpec())
                .pathParams(pathParams);
    }

    /* ****************************
     *  Response Specifications
     * ****************************/

    public static ResponseSpecification ok200() {
        if (okSpec == null) okSpec = buildResponseSpec(200);
        return okSpec;
    }

    public static ResponseSpecification created201() {
        if (createdSpec == null) createdSpec = buildResponseSpec(201);
        return createdSpec;
    }

    public static ResponseSpecification badRequest400() {
        if (badRequestSpec == null) badRequestSpec = buildResponseSpec(400);
        return badRequestSpec;
    }

    public static ResponseSpecification unauthorized401() {
        if (unauthorizedSpec == null) unauthorizedSpec = buildResponseSpec(401);
        return unauthorizedSpec;
    }

    public static ResponseSpecification forbidden403() {
        if (forbiddenSpec == null) forbiddenSpec = buildResponseSpec(403);
        return forbiddenSpec;
    }

    public static ResponseSpecification notFound404() {
        if (notFoundSpec == null) notFoundSpec = buildResponseSpec(404);
        return notFoundSpec;
    }

    public static ResponseSpecification serverError500() {
        if (serverErrorSpec == null) serverErrorSpec = buildResponseSpec(500);
        return serverErrorSpec;
    }

    private static ResponseSpecification buildResponseSpec(int statusCode) {
        ResponseSpecBuilder builder = new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .expectContentType(ContentType.JSON)
                .expectResponseTime(lessThan(ConfigManager.getMaxResponseTimeout()));

        if (ConfigManager.isResponseLoggingEnabled()) {
            builder.log(LogDetail.ALL);
        }
        return builder.build();
    }

    /* ****************************
     *  Utility
     * ****************************/
    public static void resetSpecs() {
        threadLocalRequestSpec.remove();
        okSpec = null;
        createdSpec = null;
        badRequestSpec = null;
        unauthorizedSpec = null;
        forbiddenSpec = null;
        notFoundSpec = null;
        serverErrorSpec = null;
    }
}
