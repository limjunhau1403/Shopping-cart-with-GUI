public class Product {
    private String name;
    private String category;
    private String description;
    private String imagePath;
    private double price;

    public Product(String name, String category, String description, String imagePath, double price) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.imagePath = imagePath;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public double getPrice() {
        return price;
    }
}
