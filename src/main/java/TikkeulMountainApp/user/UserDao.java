package TikkeulMountainApp.user;

import TikkeulMountainApp.util.MySqlConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    // Create 회원가입 메서드
    public static void registerUser(User user) {
        Connection conn = null;
        String sql = "INSERT INTO USER (user_id, user_name, user_password, user_phone, user_account, user_account_balance, user_active) VALUES (?, ?, ?, ?, ?, ?, ?)";
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

    //로그인 메서드
    public static boolean loginUser(String userid, String password) {
        Connection conn = null;
        String sql = "SELECT * FROM user WHERE user_id = ? AND user_password = ?";

        try {
            conn = MySqlConnect.MySqlConnect();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, userid);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("로그인 성공!");
                return true;
            } else {
                System.out.println("로그인 실패: 아이디 또는 비밀번호가 잘못되었습니다.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    // Read 조회 메서드(사용자 정보 조회)
    public static User getUser(String userId) {
        Connection conn = null;
        String sql = "SELECT * FROM USER WHERE user_id=?";
        try {
            conn = MySqlConnect.MySqlConnect();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, userId);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String userName = rs.getString("user_name");
                String userPassword = rs.getString("user_password");
                String userPhone = rs.getString("user_phone");
                String userAccount = rs.getString("user_account");
                int userAccountBalance = rs.getInt("user_account_balance");
                String userActive = rs.getString("user_active");

                User user = new User(userId, userName, userPassword, userPhone, userAccount, userAccountBalance, userActive);
                System.out.println("사용자 정보 조회가 완료되었습니다!");
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 조회 메서드 (잔액 조회)
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
            System.out.println("잔액 조회가 완료되었습니다!");

            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    //Update 수정 메서드 (잔액 수정)
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

    //수정메서드 (비밀번호 수정)
    public static void updateUserPassword(String userId, String newPassword) {
        Connection conn = null;
        String sql = "UPDATE USER SET user_password = ? WHERE user_id = ?";

        try {
            conn = MySqlConnect.MySqlConnect();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, newPassword);
            pstmt.setString(2, userId);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("비밀번호가 성공적으로 수정되었습니다!");
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }

    }

    //Delete -> Update | User, Membership 활성화유무를 확인할 수 있는 메서드
    public static void updateUserActive(String userId, String userActive) {
        Connection conn = null; // Connection 객체 선언
        String sql1 = "UPDATE USER SET user_active = ? WHERE user_id = ?";
        String sql2 = "UPDATE MEMBERSHIP SET user_active = ? WHERE user_id = ?";
        try {
            conn = MySqlConnect.MySqlConnect();
            PreparedStatement pstmt1 = conn.prepareStatement(sql1);
            PreparedStatement pstmt2 = conn.prepareStatement(sql2);

            pstmt1.setString(1, userActive); // 활성화 0 or 1
            pstmt1.setString(2, userId); // 사용자 ID 설정

            pstmt2.setString(1, userActive);
            pstmt2.setString(2, userId);

           pstmt1.executeUpdate();
            pstmt2.executeUpdate();

           // conn.commit();
            System.out.println("사용자와 멤버십의 활성화 상태가 성공적으로 수정되었습니다!");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 리소스 정리
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // 자동 커밋 모드로 되돌리기
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }




        }



