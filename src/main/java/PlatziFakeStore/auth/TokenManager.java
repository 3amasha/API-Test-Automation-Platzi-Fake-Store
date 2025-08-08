package PlatziFakeStore.auth;

import PlatziFakeStore.base.APIResources;
import PlatziFakeStore.base.BaseAPI;
import PlatziFakeStore.config.ConfigManager;
import PlatziFakeStore.models.request.auth.LoginRequest;
import PlatziFakeStore.models.response.auth.TokenResponse;
import io.restassured.response.Response;

import java.util.Map;

/**
 * TokenManager
 * -------------------------------------------------
 * - Manages JWT authentication for Escuela JS API
 * - Handles login, token caching, and refresh
 * - Integrated with ConfigManager for credentials
 * - Uses BaseAPI for consistent Rest Assured setup
 */
public final class TokenManager {

    private static String accessToken;
    private static String refreshToken;

    private TokenManager() {
        // Prevent instantiation
    }

    /**
     * Get a valid access token (login if missing).
     */
    public static synchronized String getAccessToken() {
        if (accessToken == null) {
            loginAndCacheTokens();
        }
        return accessToken;
    }

    /**
     * Perform login and store both access & refresh tokens.
     */
    private static void loginAndCacheTokens() {
        LoginRequest loginPayload = new LoginRequest(
                ConfigManager.getDefaultEmail(),
                ConfigManager.getDefaultPassword()
        );

        Response res = BaseAPI
                .withAnonymous()
                .body(loginPayload)
                .post(APIResources.LOGIN.getResource());

        TokenResponse tokens = res.then()
                .spec(BaseAPI.ok200()) // Escuela API returns 200 for login
                .extract()
                .as(TokenResponse.class);

        accessToken = tokens.getAccessToken();
        refreshToken = tokens.getRefreshToken();
    }

    /**
     * Refresh the access token using the refresh token.
     * Falls back to login if refresh fails.
     */
    public static synchronized void refreshAccessToken() {
        if (refreshToken == null) {
            loginAndCacheTokens();
            return;
        }

        Response res = BaseAPI
                .withAnonymous()
                .body(Map.of("refreshToken", refreshToken))
                .post(APIResources.REFRESH_TOKEN.getResource());

        if (res.statusCode() == 200) {
            TokenResponse tokens = res.as(TokenResponse.class);
            accessToken = tokens.getAccessToken();
            refreshToken = tokens.getRefreshToken();
        } else {
            loginAndCacheTokens();
        }
    }

    /**
     * Clears cached tokens (useful for test resets).
     */
    public static void clearTokens() {
        accessToken = null;
        refreshToken = null;
    }
}
