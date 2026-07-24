package controller;
//Mohamed Mamdouh Al-Farani
//1320236401
import database.DBConnection;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.MenuItem;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.ResultSet;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Hyperlink;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MenuManagementController implements Initializable {

    @FXML
    private TextField txtItemName;

    @FXML
    private ComboBox<String> cmbCategory;
    
    @FXML
    private ComboBox<String> cmbFilterCategory;

    @FXML
    private TextField txtPrice;
    
    @FXML
    private TextField txtSearch;

    @FXML
    private TextArea txtDescription;

    @FXML
    private VBox btnUploadImage;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnClear;
    
    @FXML
    private Button btnAddNewItem;

    @FXML
    private Hyperlink linkHome;

    @FXML
    private Hyperlink linkOrder;
    
    @FXML
    private Hyperlink btnLogout;
    
    @FXML
    private TableView<MenuItem> tblMenuItems;

    @FXML
    private TableColumn<MenuItem, Integer> colItemId;

    @FXML
    private TableColumn<MenuItem, String> colItemName;

    @FXML
    private TableColumn<MenuItem, String> colCategory;

    @FXML
    private TableColumn<MenuItem, Double> colPrice;

    @FXML
    private TableColumn<MenuItem, String> colStatus;
    
    @FXML
    private TableColumn<MenuItem, String> colDescription;

    private Connection connection;
    
    private String imagePath = "";
    
    private MenuItem selectedItem;


    private ObservableList<MenuItem> menuList = FXCollections.observableArrayList();
    
    private FilteredList<MenuItem> filteredData;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        connection = DBConnection.getConnection();

        loadCategories();
        loadFilterCategories();
        initializeTable();
        loadMenuItems();
        
        cmbFilterCategory.valueProperty().addListener((observable, oldValue, newValue) -> {

        filterByCategory(newValue);

});
        
        tblMenuItems.setOnMouseClicked(event -> {

        selectedItem = tblMenuItems.getSelectionModel().getSelectedItem();

       if(selectedItem != null){

        txtItemName.setText(selectedItem.getItemName());

        cmbCategory.setValue(selectedItem.getCategory());

        txtPrice.setText(String.valueOf(selectedItem.getPrice()));

        txtDescription.setText(selectedItem.getDescription());

        imagePath = selectedItem.getImagePath();

     }

  });

     }

    private void loadCategories() {

        cmbCategory.setItems(FXCollections.observableArrayList(

                "Pizza",
                "Burger",
                "Pasta",
                "Main Course",
                "Salad",
                "Appetizer",
                "Dessert",
                "Drinks"

        ));

    }
    
    private void initializeTable() {

    colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
    colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
    colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
    colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

}
    
    private void loadMenuItems() {

    menuList.clear();

    try {

        String sql = "SELECT * FROM menu_items";

        PreparedStatement ps = connection.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            MenuItem item = new MenuItem(

                    rs.getInt("item_id"),
                    rs.getString("item_name"),
                    rs.getString("category"),
                    rs.getDouble("price"),
                    rs.getString("description"),
                    rs.getString("image_path"),
                    rs.getString("status")

            );

            menuList.add(item);

        }

        tblMenuItems.setItems(menuList);
        
        filteredData = new FilteredList<>(menuList, b -> true);

       txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {

       filteredData.setPredicate(item -> {

        if (newValue == null || newValue.isEmpty()) {
            return true;
        }

        String keyword = newValue.toLowerCase();

        if (item.getItemName().toLowerCase().contains(keyword))
            return true;

        if (item.getCategory().toLowerCase().contains(keyword))
            return true;

        return false;

    });

});

      SortedList<MenuItem> sortedData = new SortedList<>(filteredData);

      sortedData.comparatorProperty().bind(tblMenuItems.comparatorProperty());

      tblMenuItems.setItems(sortedData);

        rs.close();
        ps.close();

    } catch (Exception e) {

        e.printStackTrace();

    }

}
    
    @FXML
    private void handleSave(ActionEvent event) {

    String itemName = txtItemName.getText().trim();
    String category = cmbCategory.getValue();
    String priceText = txtPrice.getText().trim();
    String description = txtDescription.getText().trim();

    if (itemName.isEmpty() || category == null || priceText.isEmpty()) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText("Please fill in all required fields.");
        alert.showAndWait();

        return;
    }

    try {

        double price = Double.parseDouble(priceText);

        String sql = "INSERT INTO menu_items (item_name, category, price, description, image_path, status) VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, itemName);
        ps.setString(2, category);
        ps.setDouble(3, price);
        ps.setString(4, description);
        ps.setString(5, imagePath);
        ps.setString(6, "Available");

        ps.executeUpdate();
        
        refreshTable();
        
        ps.close();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Menu item added successfully.");
        alert.showAndWait();

    } catch (NumberFormatException e) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Price");
        alert.setHeaderText(null);
        alert.setContentText("Please enter a valid price.");
        alert.showAndWait();

    } catch (SQLException e) {

        e.printStackTrace();

    }

}
    
    @FXML
    private void handleAddNewItem(ActionEvent event) {

    }

    @FXML
    private void handleClear(ActionEvent event) {

    clearFields();

}

    @FXML
    private void handleUploadImage(ActionEvent event) {

    }
    
    @FXML
    private void handleUpdate(ActionEvent event) {

    if (selectedItem == null) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText("Please select an item first.");
        alert.showAndWait();

        return;
    }

    try {

        String sql = "UPDATE menu_items SET item_name=?, category=?, price=?, description=?, image_path=?, status=? WHERE item_id=?";

        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, txtItemName.getText().trim());
        ps.setString(2, cmbCategory.getValue());
        ps.setDouble(3, Double.parseDouble(txtPrice.getText().trim()));
        ps.setString(4, txtDescription.getText().trim());
        ps.setString(5, imagePath);
        ps.setString(6, "Available");
        ps.setInt(7, selectedItem.getItemId());

        ps.executeUpdate();

        ps.close();

        loadMenuItems();

        clearFields();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Item updated successfully.");
        alert.showAndWait();

    } catch (Exception e) {

        e.printStackTrace();

    }

}

    @FXML
    private void handleDelete(ActionEvent event) {

    if (selectedItem == null) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText("Please select an item first.");
        alert.showAndWait();

        return;
    }

    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);

    confirm.setTitle("Delete");

    confirm.setHeaderText(null);

    confirm.setContentText("Are you sure you want to delete this item?");

    if (confirm.showAndWait().get() == javafx.scene.control.ButtonType.OK) {

        try {

            String sql = "DELETE FROM menu_items WHERE item_id=?";

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, selectedItem.getItemId());

            ps.executeUpdate();

            ps.close();

            loadMenuItems();

            clearFields();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Deleted");

            alert.setHeaderText(null);

            alert.setContentText("Item deleted successfully.");

            alert.showAndWait();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}
    
    private void clearFields() {

    txtItemName.clear();

    cmbCategory.setValue(null);

    txtPrice.clear();

    txtDescription.clear();

    imagePath = "";

    selectedItem = null;

    tblMenuItems.getSelectionModel().clearSelection();

 }
    
     private void loadFilterCategories() {

     cmbFilterCategory.getItems().add("All");

     cmbFilterCategory.getItems().addAll(

            "Pizza",
            "Burger",
            "Pasta",
            "Main Course",
            "Salad",
            "Appetizer",
            "Dessert",
            "Drinks"

    );

     cmbFilterCategory.setValue("All");

 
     }
     
     private void filterByCategory(String category) {

    if (category.equals("All")) {

        tblMenuItems.setItems(menuList);

        return;

    }

    ObservableList<MenuItem> filteredList = FXCollections.observableArrayList();

    for (MenuItem item : menuList) {

        if (item.getCategory().equals(category)) {

            filteredList.add(item);

        }

    }

    tblMenuItems.setItems(filteredList);

}
     
     private void refreshTable() {

    loadMenuItems();

    clearFields();

}
     
    @FXML
    private void handleHome(ActionEvent event) {

    try {

        Parent root = FXMLLoader.load(getClass().getResource("/view/HomeViews.fxml"));

        Stage stage = (Stage) linkHome.getScene().getWindow();

        stage.setScene(new Scene(root));

        stage.setTitle("Home");
        
        stage.setMaximized(true);

        stage.show();

    } catch (Exception e) {

        e.printStackTrace();

    }

}
    
    
    @FXML
    private void handleOrder(ActionEvent event) {

    try {

        Parent root = FXMLLoader.load(getClass().getResource("/view/OrderManagementViews.fxml"));

        Stage stage = (Stage) linkOrder.getScene().getWindow();

        stage.setScene(new Scene(root));

        stage.setTitle("Order Management");
        
        stage.setMaximized(true);

        stage.show();

    } catch (Exception e) {

        e.printStackTrace();

    }

}
    
     @FXML
    private void handleAddNewItem() {

    txtItemName.clear();

    txtPrice.clear();

    txtDescription.clear();

    imagePath = "";

    cmbCategory.getSelectionModel().clearSelection();

    tblMenuItems.getSelectionModel().clearSelection();

    selectedItem = null;

    txtItemName.requestFocus();

}
    
    
     @FXML
     private void handleLogout(ActionEvent event) {

    try {

        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginViews.fxml"));

        Stage stage = (Stage) btnLogout.getScene().getWindow();

        stage.setScene(new Scene(root));

        stage.setTitle("Smart Restaurant Management System");
        
        stage.setMaximized(true);

        stage.show();

    } catch (Exception e) {

        e.printStackTrace();

    }

}
    
    
    
 
}