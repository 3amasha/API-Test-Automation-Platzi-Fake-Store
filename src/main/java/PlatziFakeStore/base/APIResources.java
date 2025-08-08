package PlatziFakeStore.base;

/**
 * Enum to manage all API resource paths centrally for the Simple Grocery Store API.
 * Each constant represents a specific endpoint (resource).
 * This ensures type safety, clean maintainability, and avoids hardcoding strings across tests.
 */
public enum APIResources {

    // ========================= Products =========================
    CREATE_PRODUCT("/products/"),
    GET_ALL_PRODUCTS("/products"),
    GET_SINGLE_PRODUCT("/products/{id}"),
    UPDATE_PRODUCT("/products/{id}"),
    DELETE_PRODUCT("/products/{id}"),


    // ========================= LOGIN =========================
    // TODO : edit it in TokenManager.java
    LOGIN_PRODUCT("/auth/login");


    // Path for the resource
    private final String resource;

    /**
     * Constructor to assign the endpoint path to the enum constant
     *
     * @param resource the API endpoint path
     */
    APIResources(String resource) {
        this.resource = resource;
    }

    /**
     * Method to retrieve the actual endpoint string
     *
     * @return the endpoint path
     */
    public String getResource() {
        return resource;
    }

    /**
     * Returns the resource path with placeholders replaced by provided values.
     * To avoids manual .replace() calls in every test.
     * Example:
     * APIResources.GET_PRODUCT_BY_ID.withParams("123") -> "/products/123"
     *
     * @param params the values to replace in order of appearance
     * @return formatted endpoint path
     */
    public String withParams(String... params) {
        String formattedResource = resource;
        for (String param : params) {
            formattedResource = formattedResource.replaceFirst("\\{[^/]+}", param);
        }
        return formattedResource;
    }





}
