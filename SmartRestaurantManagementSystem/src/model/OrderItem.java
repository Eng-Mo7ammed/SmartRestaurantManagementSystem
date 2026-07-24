package model;
//Mohamed Mamdouh Al-Farani
//1320236401
public class OrderItem {

    private String itemName;
    private int quantity;
    private double price;
    private double total;

    public OrderItem(String itemName, int quantity, double price, double total) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
    this.quantity = quantity;
 } 
    
    public double getPrice() {
        return price;
    }

    public double getTotal() {
        return total;
    }
    
    public void setTotal(double total) {
    this.total = total;
    }
    
    
}