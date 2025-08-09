package PlatziFakeStore.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Category {

    private int id;
    private String name;
    private String slug;
    private String image;
    private String creationAt;
    private String updatedAt;


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

