package controller;
//Mohamed Mamdouh Al-Farani
//1320236401
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import database.DBConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Session;

public class LoginController implements Initializable {
    
    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private CheckBox chkRemember;

    @FXML
    private Button btnLogin;

    @FXML
    private Hyperlink linkRegister;

    @FXML
    private Hyperlink linkForgotPassword;
    

    @FXML
    private void handleLogin(ActionEvent event) {

    String username = txtUsername.getText().trim();
    String password = txtPassword.getText().trim();

    if (username.isEmpty() || password.isEmpty()) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText("Please enter username and password.");
        alert.showAndWait();

        return;
    }

    try {

        Connection con = DBConnection.getConnection();

        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, username);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            
        Session.setUserId(rs.getInt("user_id"));

        Session.setFullName(rs.getString("full_name"));

        Session.setUsername(rs.getString("username"));

        Session.setRole(rs.getString("role"));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/HomeViews.fxml"));

        Parent root = loader.load();
 
        Stage stage = (Stage) btnLogin.getScene().getWindow();

        stage.setScene(new Scene(root));

        stage.setTitle("Smart Restaurant Management System");
        
        stage.setMaximized(true);

        stage.show();
        
        stage.setMaximized(true);

        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("Invalid Username or Password.");
            alert.showAndWait();

        }

        rs.close();
        ps.close();
        con.close();

    } catch (Exception e) {

        e.printStackTrace();

    }

}

    @FXML
    private void handleRegister() {

    }

    @FXML
    private void handleForgotPassword() {

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

  }
    
    @FXML
    private void handleRegister(ActionEvent event) {

    try {

        Parent root = FXMLLoader.load(getClass().getResource("/view/RegisterViews.fxml"));

        Stage stage = (Stage) linkRegister.getScene().getWindow();

        stage.setScene(new Scene(root));

        stage.setTitle("Create Account");
        
        stage.setMaximized(true);

        stage.show();
        
        stage.setMaximized(true);

    } catch (Exception e) {

        e.printStackTrace();

    }

}
    
    @FXML
    private void handleForgotPassword(ActionEvent event) {

    showAlert(
            Alert.AlertType.INFORMATION,
            "Forgot Password",
            "This feature is not available in the current version.\nPlease contact the system administrator."
    );

}
    
    private void showAlert(Alert.AlertType type, String title, String message) {

    Alert alert = new Alert(type);

    alert.setTitle(title);

    alert.setHeaderText(null);

    alert.setContentText(message);

    alert.showAndWait();

}

}