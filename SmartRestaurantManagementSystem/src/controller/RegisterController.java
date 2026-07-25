
package controller;
//Mohamed Mamdouh Al-Farani
//1320236401
import database.DBConnection;
import java.sql.Connection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RegisterController {

    @FXML
    private TextField txtFullName;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPhone;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private ComboBox<String> cmbRole;

    @FXML
    private CheckBox chkTerms;

    @FXML
    private Button btnRegister;

    @FXML
    private Hyperlink linkLogin;

    private Connection connection;
    
    @FXML
    private void initialize() {

    connection = DBConnection.getConnection();

    cmbRole.setItems(FXCollections.observableArrayList(
            "Admin",
            "Manager",
            "Cashier"
    ));

  }
    
    
    @FXML
    private void handleRegister() {

    if (txtFullName.getText().trim().isEmpty()
            || txtUsername.getText().trim().isEmpty()
            || txtEmail.getText().trim().isEmpty()
            || txtPhone.getText().trim().isEmpty()
            || txtPassword.getText().isEmpty()
            || txtConfirmPassword.getText().isEmpty()
            || cmbRole.getValue() == null) {

        showAlert(
                Alert.AlertType.WARNING,
                "Missing Information",
                "Please fill in all fields."
        );

        return;
    }

    if (!txtPassword.getText().equals(txtConfirmPassword.getText())) {

        showAlert(
                Alert.AlertType.WARNING,
                "Password Error",
                "Passwords do not match."
        );

        return;
    }

    if (!chkTerms.isSelected()) {

        showAlert(
                Alert.AlertType.WARNING,
                "Terms Required",
                "Please accept the Terms and Conditions."
        );

        return;
    }

    if (!chkTerms.isSelected()) {

     showAlert(
            Alert.AlertType.WARNING,
            "Terms Required",
            "Please accept the Terms and Conditions."
    );

    return;
}
    try {

    String checkSql = "SELECT * FROM users WHERE username = ?";

    PreparedStatement check = connection.prepareStatement(checkSql);

    check.setString(1, txtUsername.getText());

    ResultSet rs = check.executeQuery();

    if (rs.next()) {

        showAlert(
                Alert.AlertType.WARNING,
                "Username Exists",
                "This username is already taken."
        );

        return;
    }

   } catch (Exception e) {

    e.printStackTrace();

    return;

}
    
    try {

    String sql = "INSERT INTO users (full_name, username, password, role, email, phone_number) VALUES (?, ?, ?, ?, ?, ?)";

    PreparedStatement ps = connection.prepareStatement(sql);

    ps.setString(1, txtFullName.getText());
    ps.setString(2, txtUsername.getText());
    ps.setString(3, txtPassword.getText());
    ps.setString(4, cmbRole.getValue());
    ps.setString(5, txtEmail.getText());
    ps.setString(6, txtPhone.getText());

    int rows = ps.executeUpdate();

    if (rows > 0) {

    showAlert(
        Alert.AlertType.INFORMATION,
        "Registration Successful",
        "Your account has been created successfully.\nYou can now log in to the system."
        );
        
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginViews.fxml"));

        Stage stage = (Stage) btnRegister.getScene().getWindow();

        stage.setScene(new Scene(root));

        stage.setTitle("Login");
        
        stage.setMaximized(true);

        stage.show();

    }

} catch (Exception e) {

    e.printStackTrace();

}

}
    
    private void showAlert(Alert.AlertType type, String title, String message) {

    Alert alert = new Alert(type);

    alert.setTitle(title);

    alert.setHeaderText(null);

    alert.setContentText(message);

    alert.showAndWait();

  }
    
    @FXML
    private void handleLogin(ActionEvent event) {

    try {

        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginViews.fxml"));

        Stage stage = (Stage) linkLogin.getScene().getWindow();

        stage.setScene(new Scene(root));

        stage.setTitle("Login");
        
        stage.setMaximized(true);

        stage.show();

    } catch (Exception e) {

        e.printStackTrace();

    }

}
    
 

}

