package PlatziFakeStore.models.shared;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Category {

    //@JsonProperty("id")
    private int id;

   // @JsonProperty("name")
    private String name;

    private String slug;

    //@JsonProperty("image")
    private String image;

    //@JsonProperty("creationAt")
    private String creationAt;

    //@JsonProperty("updatedAt")
    private String updatedAt;

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getCreationAt() { return creationAt; }
    public void setCreationAt(String creationAt) { this.creationAt = creationAt; }

    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
}

