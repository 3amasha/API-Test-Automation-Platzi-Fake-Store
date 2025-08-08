package PlatziFakeStore.models.response.products;

import PlatziFakeStore.models.shared.Category;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateProductResponse {

    @JsonProperty("id")
    private int id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("price")
    private double price;

    @JsonProperty("description")
    private String description;

    @JsonProperty("images")
    private String[] images;

    @JsonProperty("creationAt")
    private String creationAt;

    @JsonProperty("updatedAt")
    private String updatedAt;

    @JsonProperty("category")
    private Category category; // Shared model

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String[] getImages() { return images; }
    public void setImages(String[] images) { this.images = images; }

    public String getCreationAt() { return creationAt; }
    public void setCreationAt(String creationAt) { this.creationAt = creationAt; }

    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
}
