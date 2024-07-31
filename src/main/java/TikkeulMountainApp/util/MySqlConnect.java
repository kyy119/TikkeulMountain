package TikkeulMountainApp.util;

import TikkeulMountainApp.Main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnect {

    public static String url = "jdbc:mysql://localhost:3306/prac?useSSL=false";
    public static String user = "root";
    public static String password = "duddbs";

    public static void MySqlConnct() {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            if (conn != null) {
                System.out.println("연결 성공");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
