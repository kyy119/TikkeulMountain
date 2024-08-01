package TikkeulMountainApp.user;

import TikkeulMountainApp.util.MySqlConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    Connection conn = null;
    // 회원가입 메서드
    public static void registerUser(User user) {
        String sql = "INSERT INTO USER (user_id, user_name, user_password, user_phone, user_account, user_account_balance) VALUES (?, ?, ?, ?, ?, ?)";
        try ( conn = MySqlConnect.MySqlConnect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUser_id());
            pstmt.setString(2, user.getUser_name());
            pstmt.setString(3, user.getUser_password());
            pstmt.setString(4, user.getUser_phone());
            pstmt.setString(5, user.getUser_account());
            pstmt.setInt(6, user.getUser_account_balance());

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("회원가입이 성공적으로 완료되었습니다!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
//    public static void main(String[] args) {
//        // 예시: 회원가입
//        registerUser("user1", "홍길동", "password123", "010-1234-5678", "account1", 10000);
//
//
//
//    }
//}
