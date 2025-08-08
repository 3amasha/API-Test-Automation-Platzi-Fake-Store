package PlatziFakeStore.auth;

import PlatziFakeStore.base.APIResources;
import PlatziFakeStore.base.BaseAPI;
import PlatziFakeStore.config.ConfigManager;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class TokenManager {

    // Cached token value to reuse across tests
    private static String token;
// TODO : Edit file
    /**
     * Returns a valid bearer token. Caches the token after the first retrieval.
     */
    //TODO : Add tokenExpiryTime, update using expiresIn from API if available, or configurable TTL
    // Add method isTokenExpired
    public static String getToken() {
        // If token already fetched, reuse it
        if (token == null) {
            // Trigger token request
            token = generateNewToken();
        }
        return token;
    }

    /**
     * Generates a new token from the API Authentication endpoint.
     * Reads clientName and clientEmail from config.properties.
     */
    private static String generateNewToken() {
        // Read client credentials from config
      String clientName = ConfigManager.getClientName();
     String clientEmail = ConfigManager.getClientEmail();

        // Create the request body as a map
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("clientName", clientName);
        requestBody.put("clientEmail", clientEmail);

        // Send  POST request to get a token
        Response response = given()
                .spec(BaseAPI.getRequestSpec())
                .body(requestBody)
        .when()
                .post(APIResources.LOGIN_PRODUCT.getResource())
        .then()
                .spec(BaseAPI.getCreatedResponseSpec())
                .extract()
                .response();

        // Extract token from response JSON
        return response.jsonPath().getString("accessToken");


    }

    public static String refreshToken() {
        token = generateNewToken();
        return token;
    }

}
