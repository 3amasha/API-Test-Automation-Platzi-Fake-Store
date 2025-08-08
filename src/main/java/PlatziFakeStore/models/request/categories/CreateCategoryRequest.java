package PlatziFakeStore.models.request.categories;

public class CreateCategoryRequest {

    private String name;
    private String image;

    public CreateCategoryRequest(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
}
