package database;
//Mohamed Mamdouh Al-Farani
//1320236401
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/smart_restaurant_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {

        try {

            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.println("Database Connected Successfully.");

            return connection;

        } catch (SQLException e) {

            System.out.println("Database Connection Failed.");

            e.printStackTrace();

            return null;

        }

    }

}