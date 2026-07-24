package controller;
//Mohamed Mamdouh Al-Farani
//1320236401
import database.DBConnection;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import model.OrderItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;
import javafx.scene.control.Alert;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import model.Order;

public class OrderManagementController implements Initializable {

    @FXML
    private ComboBox<String> cmbOrderType;

    @FXML
    private ComboBox<String> cmbCustomer;

    @FXML
    private DatePicker dpOrderDate;

    @FXML
    private TextField txtOrderTime;

    @FXML
    private TextField txtSearch;
    
    @FXML
    private TextField txtSearchOrders;

    @FXML
    private ComboBox<String> cmbCategory;

    @FXML
    private Button btnClearOrder;

    @FXML
    private Button btnPlaceOrder;

    @FXML
    private Label lblTotalAmount;

    @FXML
    private Label lblTotalOrders;

    @FXML
    private Label lblPendingOrders;

    @FXML
    private Label lblCompletedOrders;

    @FXML
    private Label lblTodayRevenue;
    
    @FXML
    private Label lblSubtotal;

    @FXML
    private Label lblTax;

    @FXML
    private Label lblDiscount;
    
    @FXML
    private Label lblPizzaQty;

    @FXML
    private Label lblBurgerQty;

    @FXML
    private Label lblChickenQty;

    @FXML
    private Label lblSaladQty;

    @FXML
    private Label lblSpaghettiQty;
    
    @FXML
    private Label lblPizzaPrice;

    @FXML
    private Label lblBurgerPrice;

    @FXML
    private Label lblChickenPrice;

    @FXML
    private Label lblSaladPrice;
    
    @FXML
    private Label lblSpaghettiPrice;
    
    @FXML
    private Button btnPizzaAdd;

    @FXML
    private Button btnBurgerAdd;

    @FXML
    private Button btnChickenAdd;

    @FXML
    private Button btnSaladAdd;

    @FXML
    private Button btnSpaghettiAdd;
    
    @FXML
    private Hyperlink linkHome;

    @FXML
    private Hyperlink linkMenu;
    
    @FXML
    private Hyperlink btnLogout;
    
    @FXML
    private TableView<Order> tableRecentOrders;

    @FXML
    private TableColumn<Order, Integer> colRecentOrderID;

    @FXML
    private TableColumn<Order, String> colRecentCustomer;

    @FXML
    private TableColumn<Order, String> colRecentStatus;

    @FXML
    private TableColumn<Order, Double> colRecentTotal;

    @FXML
    private TableView<OrderItem> tblOrderItems;

    @FXML
    private TableColumn<OrderItem, String> colItemName;

    @FXML
    private TableColumn<OrderItem, Integer> colQuantity;

    @FXML
    private TableColumn<OrderItem, Double> colPrice;

    @FXML
    private TableColumn<OrderItem, Double> colTotal;

    Connection connection;
    
    private ObservableList<OrderItem> orderList = FXCollections.observableArrayList();
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        connection = DBConnection.getConnection();
        
        loadTodayOrdersCount();
        loadPendingOrdersCount();
        loadCompletedOrdersCount();
        loadTodayRevenue();
        loadRecentOrders();
        
        colRecentOrderID.setCellValueFactory(new PropertyValueFactory<>("orderId"));

        colRecentCustomer.setCellValueFactory(new PropertyValueFactory<>("customerName"));

        colRecentStatus.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));

        colRecentTotal.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        
        cmbCustomer.getItems().add("Walk-in Customer");

        cmbCustomer.setValue("Walk-in Customer");
        
        cmbOrderType.getItems().addAll(

        "Dine In",

        "Take Away",

        "Delivery"

);
        dpOrderDate.setValue(LocalDate.now());

        cmbOrderType.setValue("Dine In");
        
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        tblOrderItems.setItems(orderList);
        
        lblPizzaQty.setText("1");
        lblBurgerQty.setText("1");
        lblChickenQty.setText("1");
        lblSaladQty.setText("1");
        lblSpaghettiQty.setText("1");

    }

    @FXML
    private void handleAddItem(ActionEvent event) {

    Button button = (Button) event.getSource();

    if (button == btnPizzaAdd) {

        int qty = Integer.parseInt(lblPizzaQty.getText());

        double price = Double.parseDouble(
        lblPizzaPrice.getText().replace("$", "").trim()
);

        addItemToOrder("Margherita Pizza", qty, price);
    }
    
    if (button == btnBurgerAdd) {

    int qty = Integer.parseInt(lblBurgerQty.getText());

    double price = Double.parseDouble(
            lblBurgerPrice.getText().replace("$", "").trim()
    );

    addItemToOrder("Classic Burger", qty, price);

}
    
    if (button == btnChickenAdd) {

    int qty = Integer.parseInt(lblChickenQty.getText());

    double price = Double.parseDouble(
            lblChickenPrice.getText().replace("$", "").trim()
    );

    addItemToOrder("Grilled Chicken", qty, price);

}
    
    if (button == btnSaladAdd) {

    int qty = Integer.parseInt(lblSaladQty.getText());

    double price = Double.parseDouble(
            lblSaladPrice.getText().replace("$", "").trim()
    );

    addItemToOrder("Caesar Salad", qty, price);

}
    
    if (button == btnSpaghettiAdd) {

    int qty = Integer.parseInt(lblSpaghettiQty.getText());

    double price = Double.parseDouble(
            lblSpaghettiPrice.getText().replace("$", "").trim()
    );

    addItemToOrder("Spaghetti Bolognese", qty, price);

}
    
   
}

    @FXML
    private void handlePlaceOrder(ActionEvent event) {

    if (orderList.isEmpty()) {

        showAlert(
                Alert.AlertType.WARNING,
                "Warning",
                "Please add at least one item."
        );

        return;

    }
 
     try {

    String sql = "INSERT INTO orders (customer_name, phone_number, order_date, total_amount, order_status) VALUES (?, ?, ?, ?, ?)";

    PreparedStatement ps = connection.prepareStatement(
        sql,
        Statement.RETURN_GENERATED_KEYS
  );

    ps.setString(1, cmbCustomer.getValue().toString());

    ps.setString(2, "N/A");

    ps.setDate(3, java.sql.Date.valueOf(dpOrderDate.getValue()));

    double total = Double.parseDouble(
            lblTotalAmount.getText().replace("$", "")
    );

    ps.setDouble(4, total);

    ps.setString(5, "Pending");

    ps.executeUpdate();
    
    ResultSet rs = ps.getGeneratedKeys();

    int orderId = 0;

    if (rs.next()) {

    orderId = rs.getInt(1);

  }
    
    String itemSql = "INSERT INTO order_items (order_id, item_name, quantity, price, total) VALUES (?, ?, ?, ?, ?)";

    PreparedStatement itemPs = connection.prepareStatement(itemSql);

    for (OrderItem item : orderList) {

    itemPs.setInt(1, orderId);

    itemPs.setString(2, item.getItemName());

    itemPs.setInt(3, item.getQuantity());

    itemPs.setDouble(4, item.getPrice());

    itemPs.setDouble(5, item.getTotal());

    itemPs.executeUpdate();

  }

    showAlert(
            Alert.AlertType.INFORMATION,
            "Success",
            "Order saved in database."
    );

    } catch (Exception e) {

    e.printStackTrace();

   }
     
     
    showAlert(
            Alert.AlertType.INFORMATION,
            "Success",
            "Order is ready to save."
    );
    
    handleClearOrder();
    refreshDashboard();

} 

    @FXML
    private void handleClearOrder() {

    orderList.clear();

    lblSubtotal.setText("$0.00");
    lblTax.setText("$0.00");
    lblDiscount.setText("$0.00");
    lblTotalAmount.setText("$0.00");

    lblPizzaQty.setText("1");
    lblBurgerQty.setText("1");
    lblChickenQty.setText("1");
    lblSaladQty.setText("1");
    lblSpaghettiQty.setText("1");
    
    cmbCustomer.setValue("Walk-in Customer");

    cmbOrderType.setValue("Dine In");

    dpOrderDate.setValue(LocalDate.now());

}
    
    @FXML
    private void handleHome(ActionEvent event) {

    try {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/HomeViews.fxml"));

        Parent root = loader.load();

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
    private void handleMenu(ActionEvent event) {

    try {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MenuManagementViews.fxml"));

        Parent root = loader.load();

        Stage stage = (Stage) linkMenu.getScene().getWindow();

        stage.setScene(new Scene(root));

        stage.setTitle("Menu Management");
        
        stage.setMaximized(true);

        stage.show();

    } catch (Exception e) {

        e.printStackTrace();

    }

}
    
    @FXML
    private void handleIncreaseQuantity(ActionEvent event) {

}

    @FXML
    private void handleDecreaseQuantity(ActionEvent event) {

}
    
    
   @FXML
   private void handlePizzaPlus(ActionEvent event) {

    int qty = Integer.parseInt(lblPizzaQty.getText());

    qty++;

    lblPizzaQty.setText(String.valueOf(qty));

}

    @FXML
    private void handlePizzaMinus(ActionEvent event) {

    int qty = Integer.parseInt(lblPizzaQty.getText());

    if (qty > 1) {

        qty--;

        lblPizzaQty.setText(String.valueOf(qty));

    }

}
    
    @FXML
    private void handleBurgerPlus(ActionEvent event) {

    int qty = Integer.parseInt(lblBurgerQty.getText());

    qty++;

    lblBurgerQty.setText(String.valueOf(qty));

}

    @FXML
    private void handleBurgerMinus(ActionEvent event) {

    int qty = Integer.parseInt(lblBurgerQty.getText());

    if (qty > 1) {

        qty--;

        lblBurgerQty.setText(String.valueOf(qty));

    }

}

    @FXML
    private void handleChickenPlus(ActionEvent event) {

    int qty = Integer.parseInt(lblChickenQty.getText());

    qty++;

    lblChickenQty.setText(String.valueOf(qty));

}

    @FXML
    private void handleChickenMinus(ActionEvent event) {

    int qty = Integer.parseInt(lblChickenQty.getText());

    if (qty > 1) {

        qty--;

        lblChickenQty.setText(String.valueOf(qty));

    }

}
    
    @FXML
    private void handleSaladPlus(ActionEvent event) {

    int qty = Integer.parseInt(lblSaladQty.getText());

    qty++;

    lblSaladQty.setText(String.valueOf(qty));

}

    @FXML
    private void handleSaladMinus(ActionEvent event) {

    int qty = Integer.parseInt(lblSaladQty.getText());

    if (qty > 1) {

        qty--;

        lblSaladQty.setText(String.valueOf(qty));

    }

}
    
    @FXML
    private void handleSpaghettiPlus(ActionEvent event) {

    int qty = Integer.parseInt(lblSpaghettiQty.getText());

    qty++;

    lblSpaghettiQty.setText(String.valueOf(qty));

}

    @FXML
    private void handleSpaghettiMinus(ActionEvent event) {

    int qty = Integer.parseInt(lblSpaghettiQty.getText());

    if (qty > 1) {

        qty--;

        lblSpaghettiQty.setText(String.valueOf(qty));

    }

}
    
     private void updateTotalAmount() {

    double subtotal = 0;

    for (OrderItem item : orderList) {

        subtotal += item.getTotal();

    }

    double tax = subtotal * 0.08;

    double discount = 0;

    double grandTotal = subtotal + tax - discount;

    lblSubtotal.setText(String.format("$%.2f", subtotal));

    lblTax.setText(String.format("$%.2f", tax));

    lblDiscount.setText(String.format("$%.2f", discount));

    lblTotalAmount.setText(String.format("$%.2f", grandTotal));

    }

    
    private void addItemToOrder(String itemName, int qty, double price) {

    boolean found = false;

    for (OrderItem item : orderList) {

        if (item.getItemName().equals(itemName)) {

            int newQty = item.getQuantity() + qty;

            item.setQuantity(newQty);

            item.setTotal(newQty * price);

            tblOrderItems.refresh();

            found = true;

            break;

        }

    }

    if (!found) {

        OrderItem item = new OrderItem(
                itemName,
                qty,
                price,
                qty * price
        );

        orderList.add(item);

    }

    updateTotalAmount();

}
    
    private void showAlert(Alert.AlertType type, String title, String message) {

    Alert alert = new Alert(type);

    alert.setTitle(title);

    alert.setHeaderText(null);

    alert.setContentText(message);

    alert.showAndWait();

}
    
    private void loadTodayOrdersCount() {

    try {

        String sql = "SELECT COUNT(*) FROM orders WHERE order_date = CURDATE()";

        PreparedStatement ps = connection.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            lblTotalOrders.setText(String.valueOf(rs.getInt(1)));

        }

    } catch (Exception e) {

        e.printStackTrace();

    }

}
    
    private void loadPendingOrdersCount() {

    try {

        String sql = "SELECT COUNT(*) FROM orders WHERE order_status = ?";

        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, "Pending");

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            lblPendingOrders.setText(String.valueOf(rs.getInt(1)));

        }

    } catch (Exception e) {

        e.printStackTrace();

    }

}
    
    private void loadCompletedOrdersCount() {

    try {

        String sql = "SELECT COUNT(*) FROM orders WHERE order_status = ?";

        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, "Completed");

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            lblCompletedOrders.setText(String.valueOf(rs.getInt(1)));

        }

    } catch (Exception e) {

        e.printStackTrace();

    }

}
    
    private void loadTodayRevenue() {

    try {

        String sql = "SELECT SUM(total_amount) FROM orders WHERE order_date = CURDATE()";

        PreparedStatement ps = connection.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            double revenue = rs.getDouble(1);

            lblTodayRevenue.setText(String.format("$%.2f", revenue));

        }

    } catch (Exception e) {

        e.printStackTrace();

    }

}
    
    private void refreshDashboard() {

    loadTodayOrdersCount();

    loadPendingOrdersCount();

    loadCompletedOrdersCount();

    loadTodayRevenue();
    
    loadRecentOrders();

  } 
    
    private void loadRecentOrders() {

    try {

        ObservableList<Order> list = FXCollections.observableArrayList();

        String sql = "SELECT * FROM orders ORDER BY order_id DESC LIMIT 5";

        PreparedStatement ps = connection.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            list.add(new Order(

                    rs.getInt("order_id"),

                    rs.getString("customer_name"),

                    rs.getString("phone_number"),

                    rs.getString("order_date"),

                    rs.getDouble("total_amount"),

                    rs.getString("order_status")

            ));

        }

        tableRecentOrders.setItems(list);

    } catch (Exception e) {

        e.printStackTrace();

    }

}
    
    @FXML
    private void handleSearchOrders() {

    try {
        
        if (txtSearchOrders.getText().trim().isEmpty()) {

            loadRecentOrders();

               return;

          }

        ObservableList<Order> list = FXCollections.observableArrayList();

        String sql = "SELECT * FROM orders WHERE customer_name LIKE ? ORDER BY order_id DESC";

        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, "%" + txtSearchOrders.getText() + "%");

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            list.add(new Order(

                    rs.getInt("order_id"),

                    rs.getString("customer_name"),

                    rs.getString("phone_number"),

                    rs.getString("order_date"),

                    rs.getDouble("total_amount"),

                    rs.getString("order_status")

            ));

        }

        tableRecentOrders.setItems(list);

    } catch (Exception e) {

        e.printStackTrace();

    }

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