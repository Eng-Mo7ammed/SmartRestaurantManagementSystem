package controller;
//Mohamed Mamdouh Al-Farani
//1320236401
import database.DBConnection;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.Session;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class HomeController implements Initializable {

    @FXML
    private Label lblWelcome;
    
    @FXML
    private Button btnLoginRegister;
    
    @FXML
    private Button btnExploreNow;
    
    @FXML
    private Hyperlink linkMenu;
    
    @FXML
    private Hyperlink linkViewFullMenu;
    
    @FXML
    private Label lblDailyOrders;
    
    @FXML
    private Label lblMenuItems;
    
    @FXML
    private Label lblHappyCustomers;
    
    private Connection connection;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connection = DBConnection.getConnection();
        
        loadDailyOrders();
        loadMenuItems();
        loadHappyCustomers();
        lblWelcome.setText("Welcome, " + Session.getFullName());

    }

    
    @FXML
    private void handleHome(ActionEvent event) {

    }

    @FXML
    private void handleAboutUs(ActionEvent event) {

    }

    @FXML
    private void handleServices(ActionEvent event) {

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
        
        stage.setMaximized(true);

        } catch (Exception e) {

           e.printStackTrace();

    }

}

    @FXML
    private void handleContact(ActionEvent event) {

    }

    @FXML
    private void handleExploreNow(ActionEvent event) {

    try {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/OrderManagementViews.fxml"));

        Parent root = loader.load();

        Stage stage = (Stage) btnExploreNow.getScene().getWindow();

        stage.setScene(new Scene(root));

        stage.setTitle("Order Management");
        
        stage.setMaximized(true);

        stage.show();
        
        stage.setMaximized(true);

    } catch (Exception e) {

        e.printStackTrace();

    }

}

    @FXML
    private void handleLoginRegister(ActionEvent event) {

    try {

        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginViews.fxml"));

        Stage stage = (Stage) btnLoginRegister.getScene().getWindow();

        stage.setScene(new Scene(root));

        stage.setTitle("Login");
        
        stage.setMaximized(true);

        stage.show();
        
        stage.setMaximized(true);

    } catch (Exception e) {

        e.printStackTrace();

    }

}
    
    private void loadDailyOrders() {

    try {

        String sql = "SELECT COUNT(*) FROM orders WHERE order_date = CURDATE()";

        PreparedStatement ps = connection.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            lblDailyOrders.setText(String.valueOf(rs.getInt(1)));

        }

    } catch (Exception e) {

        e.printStackTrace();

    }

}
    
    private void loadMenuItems() {

    try {

        String sql = "SELECT COUNT(*) FROM menu_items";

        PreparedStatement ps = connection.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            lblMenuItems.setText(String.valueOf(rs.getInt(1)));

        }

    } catch (Exception e) {

        e.printStackTrace();

    }

}
    
    private void loadHappyCustomers() {

    try {

        String sql = "SELECT COUNT(*) FROM users";

        PreparedStatement ps = connection.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            lblHappyCustomers.setText(String.valueOf(rs.getInt(1)));

        }

    } catch (Exception e) {

        e.printStackTrace();

    }

}

    @FXML
    private void handleViewFullMenu(ActionEvent event) {

    try {

        Parent root = FXMLLoader.load(getClass().getResource("/view/MenuManagementViews.fxml"));

        Stage stage = (Stage) linkViewFullMenu.getScene().getWindow();

        stage.setScene(new Scene(root));

        stage.setTitle("Menu Management");
         
        stage.setMaximized(true);
        
        stage.show();

    } catch (Exception e) {

        e.printStackTrace();

    }

}
    
}