package PlatziFakeStore.models.response.products;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import PlatziFakeStore.models.shared.Category;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

   // @JsonProperty("id")
    private int id;

   // @JsonProperty("title")
    private String title;

    //@JsonProperty("slug")
    private String slug;

    //@JsonProperty("price")
    private double price;

   // @JsonProperty("description")
    private String description;

   // @JsonProperty("category")
    private Category category;  // Reuse the shared Category class

   // @JsonProperty("images")
    private List<String> images;

 //@JsonProperty("creationAt")
 private String creationAt;

 //@JsonProperty("updatedAt")
 private String updatedAt;

    // Getters and setters

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public List<String> getImages() { return images; }
    public void setImages(List<String> images) { this.images = images; }

    public String getCreationAt() { return creationAt; }
    public void setCreationAt(String creationAt) { this.creationAt = creationAt; }
    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt;}
}
