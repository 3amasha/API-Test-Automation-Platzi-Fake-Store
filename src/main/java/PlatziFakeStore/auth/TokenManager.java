package PlatziFakeStore.auth;

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
/*
    /**
     * Get a valid access token (From Login).
     */
   /* public static synchronized String getAccessToken(String clientEmail, String clientPassword) {
        if (accessToken == null) {
            loginAndCacheTokens(clientEmail, clientPassword);
        }
        return accessToken;
    }*/

    /**
     * Perform login and store both access & refresh tokens.
     */
  /*  private static void loginAndCacheTokens(String clientEmail, String clientPassword) {
        LoginRequest loginPayload = new LoginRequest(clientEmail, clientPassword);

        Response res = AuthClient

        TokenResponse tokens = res.then()
                .spec(BaseAPI.ok200()) // Escuela API returns 200 for login
                .extract()
                .as(TokenResponse.class);

        accessToken = tokens.getAccessToken();
        refreshToken = tokens.getRefreshToken();
    }*/

    /**
     * Refresh the access token using the refresh token.
     * Falls back to login if refresh fails.
     */
   /* public static synchronized void refreshAccessToken() {
        if (refreshToken == null) {
            loginAndCacheTokens();
            return;
        }

        Response res = BaseAPI
                .spec(getRequestSpec())
                .body(Map.of("refreshToken", refreshToken))
                .post(APIResources.REFRESH_TOKEN.getResource());

        if (res.statusCode() == 200) {
            TokenResponse tokens = res.as(TokenResponse.class);
            accessToken = tokens.getAccessToken();
            refreshToken = tokens.getRefreshToken();
        } else {
            loginAndCacheTokens();
        }
    }*/

    /**
     * Clears cached tokens (useful for test resets).
     */
    public static void clearTokens() {
        accessToken = null;
        refreshToken = null;
    }
}
