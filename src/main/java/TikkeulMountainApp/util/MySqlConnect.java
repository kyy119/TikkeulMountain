package TikkeulMountainApp.util;

import TikkeulMountainApp.Main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnect {

    public static String url = "jdbc:mysql://localhost:3306/TikkeulMountain?useSSL=false";
    public static String user = "root";
    public static String password = "1234";

    public static Connection MySqlConnect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url,user,password);
            if (conn != null) {
                return conn;
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return conn;
    }
}
