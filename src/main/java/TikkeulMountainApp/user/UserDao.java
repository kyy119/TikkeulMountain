package TikkeulMountainApp.user;

import TikkeulMountainApp.util.MySqlConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    // 회원가입 메서드
    public static void registerUser(User user) {
        Connection conn = null;
        String sql = "INSERT INTO USER (user_id, user_name, user_password, user_phone, user_account, user_account_balance, user_active) VALUES (?, ?, ?, ?, ?, ?,?)";
        try {
            conn = MySqlConnect.MySqlConnect();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, user.getUser_id());
            pstmt.setString(2, user.getUser_name());
            pstmt.setString(3, user.getUser_password());
            pstmt.setString(4, user.getUser_phone());
            pstmt.setString(5, user.getUser_account());
            pstmt.setInt(6, user.getUser_account_balance());
            pstmt.setString(7, user.getUser_active());

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("회원가입이 성공적으로 완료되었습니다!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateUserBalance(String userId, int amount) {
        Connection conn = null; // Connection 객체 선언
        String sql = "UPDATE USER SET user_account_balance = ? WHERE user_id = ?";
        try {
            conn = MySqlConnect.MySqlConnect();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, amount); // 잔액
            pstmt.setString(2, userId); // 아이디

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("계좌 잔액이 성공적으로 수정되었습니다!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static int getUserBalance(String userId) {
        Connection conn = null;
        String sql = "SELECT user_account_balance FROM user WHERE user_id = ?";
        try {
            conn = MySqlConnect.MySqlConnect();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, userId); // 아이디

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int balance = rs.getInt("user_account_balance");
                return balance;
            }
            System.out.println("조회가 완료되었습니다!");

            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void updateUserActive(String userId, String useractive) {
        Connection conn = null; // Connection 객체 선언
        String sql = "UPDATE USER SET user_active = ? WHERE user_id = ?";
        try {
            conn = MySqlConnect.MySqlConnect();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, useractive); // 활성화 0 or 1
            pstmt.setString(2, userId); // 사용자 ID 설정

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("비활성화 계정입니다!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


//user_account_balance 메서드


//    // 로그인 메서드
//    public static boolean loginUser(String userId, String userPassword) {
//        String sql = "SELECT * FROM USER WHERE user_id = ? AND user_password = ?";
//        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//
//            pstmt.setString(1, userId);
//            pstmt.setString(2, userPassword);
//
//            ResultSet rs = pstmt.executeQuery();
//            return rs.next(); // 결과가 있으면 로그인 성공
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    // 비밀번호 변경 메서드
//    public static void updateUserPassword(String userId, String newPassword) {
//        String sql = "UPDATE USER SET user_password = ? WHERE user_id = ?";
//        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//
//            pstmt.setString(1, newPassword);
//            pstmt.setString(2, userId);
//
//            int rowsUpdated = pstmt.executeUpdate();
//            if (rowsUpdated > 0) {
//                System.out.println("비밀번호가 성공적으로 변경되었습니다.");
//            } else {
//                System.out.println("해당 사용자 ID가 존재하지 않습니다.");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    // 사용자 삭제 메서드
//    public static void deleteUser(String userId) {
//        String sql = "DELETE FROM USER WHERE user_id = ?";
//        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//
//            pstmt.setString(1, userId);
//
//            int rowsDeleted = pstmt.executeUpdate();
//            if (rowsDeleted > 0) {
//                System.out.println("사용자가 성공적으로 삭제되었습니다.");
//            } else {
//                System.out.println("해당 사용자 ID가 존재하지 않습니다.");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//
//    }
//}
