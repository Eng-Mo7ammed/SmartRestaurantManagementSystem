package smartrestaurantmanagementsystem;
//Mohamed Mamdouh Al-Farani
//1320236401
import database.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SmartRestaurantManagementSystem extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        
        DBConnection.getConnection();

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/view/LoginViews.fxml"));

        Scene scene = new Scene(loader.load());

        stage.setTitle("Smart Restaurant Management System");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}