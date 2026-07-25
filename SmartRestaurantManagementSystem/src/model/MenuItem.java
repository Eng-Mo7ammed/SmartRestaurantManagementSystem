package model;
//Mohamed Mamdouh Al-Farani
//1320236401
public class MenuItem {
    
    private int itemId;
    private String itemName;
    private String category;
    private double price;
    private String description;
    private String imagePath;
    private String status;
    
    public MenuItem() {
    }
    
    public MenuItem(int itemId, String itemName, String category,
                    double price, String description,
                    String imagePath, String status) {

        this.itemId = itemId;
        this.itemName = itemName;
        this.category = category;
        this.price = price;
        this.description = description;
        this.imagePath = imagePath;
        this.status = status;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}