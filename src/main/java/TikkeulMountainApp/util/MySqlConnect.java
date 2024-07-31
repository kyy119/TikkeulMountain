package TikkeulMountainApp.util;

import TikkeulMountainApp.Main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnect {

    public static String url = "jdbc:mysql://localhost:3306/TikkeulMountain?useSSL=false";
    public static String user = "root";
    public static String password = "1234";

    public static void MySqlConnect() {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            if (conn != null) {
                System.out.println("연결 성공");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
