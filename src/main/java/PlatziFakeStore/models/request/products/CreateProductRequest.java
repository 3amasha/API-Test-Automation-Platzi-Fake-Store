package PlatziFakeStore.models.request.products;

public class CreateProductRequest {

    private String title;
    private double price;
    private String description;
    private int categoryId;
    private String[] images;



    // Parameterized constructor
    public CreateProductRequest(String title, double price, String description, int categoryId, String[] images) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.categoryId = categoryId;
        this.images = images;
    }

    // --- Getters and Setters ---

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String[] getImages() {
        return images;
    }
    public void setImages(String[] images) {
        this.images = images;
    }
}

