package TikkeulMountainApp.util;

import TikkeulMountainApp.Main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnect {

    public static String url = "jdbc:mysql://localhost:3306/TikkeulMountain?useSSL=false";
    public static String user = "root";
    public static String password = "duddbs";

    public static Connection MySqlConnect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url,user,password);
            if (conn != null) {
                System.out.println("연결 성공");
                return conn;
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return conn;
    }
}
