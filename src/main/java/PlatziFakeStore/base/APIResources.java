package PlatziFakeStore.base;

/**
 * APIResources
 * -------------
 * Central registry of EscuelaJS API endpoints.
 * Each enum constant represents a specific endpoint.
 * Includes helpers for path params and query params construction.
 */
public enum APIResources {
    // ------------------ Products ------------------
    CREATE_PRODUCT("/products"),
    GET_ALL_PRODUCTS("/products"),
    GET_PRODUCT_BY_ID("/products/{id}"),
    GET_PRODUCT_BY_SLUG("/products/slug/{slug}"),
    UPDATE_PRODUCT("/products/{id}"),
    DELETE_PRODUCT("/products/{id}"),
    GET_PRODUCTS_PAGINATED("/products?offset={offset}&limit={limit}"),
    GET_RELATED_BY_ID("/products/{id}/related"),
    GET_RELATED_BY_SLUG("/products/slug/{slug}/related"),

    // ------------------ Categories ------------------
    GET_ALL_CATEGORIES("/categories"),
    GET_CATEGORY_BY_ID("/categories/{id}"),
    CREATE_CATEGORY("/categories"),
    UPDATE_CATEGORY("/categories/{id}"),
    DELETE_CATEGORY("/categories/{id}"),

    // ------------------ Users ------------------
    GET_ALL_USERS("/users"),
    GET_USER_BY_ID("/users/{id}"),
    CREATE_USER("/users"),
    UPDATE_USER("/users/{id}"),
    DELETE_USER("/users/{id}"),

    // ------------------ Authentication ------------------
    LOGIN("/auth/login"),
    REFRESH_TOKEN("/auth/refresh-token"),
    PROFILE("/auth/profile"),

    // ------------------ Files / Upload ------------------
    UPLOAD_FILE("/files/upload"),
    GET_FILE("/files/{id}");

    private final String resource;

    APIResources(String resource) {
        this.resource = resource;
    }

    /** Returns the raw endpoint string as defined in Swagger. */
    public String getResource() {
        return resource;
    }

    /**
     * Injects path parameter values into the endpoint definition.
     * Example:
     *   GET_PRODUCT_BY_ID.withParams("123") -> "/products/123"
     */
    public String withParams(String... params) {
        String formatted = resource;
        for (String param : params) {
            formatted = formatted.replaceFirst("\\{[^/]+}", param);
        }
        return formatted;
    }

    /**
     * Appends query parameters to the endpoint.
     * Example:
     *   GET_ALL_PRODUCTS.withQueryParams("offset=0", "limit=10") -> "/products?offset=0&limit=10"
     */
    public String withQueryParams(String... queryParams) {
        StringBuilder sb = new StringBuilder(resource);
        if (queryParams.length > 0) {
            sb.append("?");
            for (int i = 0; i < queryParams.length; i++) {
                sb.append(queryParams[i]);
                if (i < queryParams.length - 1) {
                    sb.append("&");
                }
            }
        }
        return sb.toString();
    }
}
