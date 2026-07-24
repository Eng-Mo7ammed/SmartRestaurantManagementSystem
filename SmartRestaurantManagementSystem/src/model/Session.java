package model;
//Mohamed Mamdouh Al-Farani
//1320236401
public class Session {

    private static int userId;

    private static String fullName;

    private static String username;

    private static String role;

    public static int getUserId() {
        return userId;
    }

    public static void setUserId(int userId) {
        Session.userId = userId;
    }

    public static String getFullName() {
        return fullName;
    }

    public static void setFullName(String fullName) {
        Session.fullName = fullName;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        Session.username = username;
    }

    public static String getRole() {
        return role;
    }

    public static void setRole(String role) {
        Session.role = role;
    }

}