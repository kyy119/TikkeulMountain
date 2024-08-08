package TikkeulMountainApp.user;

import TikkeulMountainApp.util.MySqlConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;


public class UserDao {


    // Create 회원가입
    public static void registerUser(User user) {
        Connection conn = null;
        String sql = "INSERT INTO USER (user_id, user_name, user_password, user_phone, user_account, user_account_balance, user_active) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            conn = MySqlConnect.MySqlConnect();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getUserName());
            pstmt.setString(3, user.getUserPassword());
            pstmt.setString(4, user.getUserPhone());
            pstmt.setString(5, user.getUserAccount());
            pstmt.setInt(6, 100000);
            pstmt.setString(7, "1");

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("회원가입이 성공적으로 완료되었습니다!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
    }


    // 비밀번호 유효성 검사
    public static boolean isValidPassword(String password) {
        // 최소 6자 이상, 하나 이상의 문자, 하나의 숫자, 하나의 특수문자 포함
        String passwordPattern = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$";
        return Pattern.matches(passwordPattern, password);
    }

    // 아이디 중복
    public static boolean isUserIdExists(String userId) {
        return checkExists("SELECT COUNT(*) FROM USER WHERE user_id = ?", userId);
    }

    // 폰 번호 중복
    public static boolean isUserPhoneExists(String userPhone) {
        return checkExists("SELECT COUNT(*) FROM USER WHERE user_phone = ?", userPhone);
    }

    // 계좌 번호 중복
    public static boolean isUserAccountExists(String userAccount) {
        return checkExists("SELECT COUNT(*) FROM USER WHERE user_account = ?", userAccount);
    }

    // 공통 중복 확인
    private static boolean checkExists(String query, String value) {
        try (Connection conn = MySqlConnect.MySqlConnect();
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, value);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //로그인
    public static boolean loginUser(String userid, String password) {
        Connection conn = null;
        String sql = "SELECT * FROM user WHERE user_id = ? AND user_password = ? AND user_active='1'";

        try {
            conn = MySqlConnect.MySqlConnect();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, userid);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getString("user_id"));
                user.setUserName(rs.getString("user_name"));
                user.setUserPassword(rs.getString("user_password"));
                user.setUserPhone(rs.getString("user_phone"));
                user.setUserAccount(rs.getString("user_account"));
                user.setUserAccountBalance(rs.getInt("user_account_balance"));
                user.setUserActive(rs.getString("user_active"));
                LoginChecker.setUser(user);
                return true;
            } else {

                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }

    }

    // Read 조회 메서드 (사용자 정보 조회)
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

                User user = new User(userId, userName, userPassword, userPhone, userAccount,
                    userAccountBalance, userActive);

                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
        return null;
    }

    // 조회 (잔액 조회)
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

            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
        return 0;
    }


    //Update 수정 메서드 (잔액 수정)
    public static void updateUserBalance(String userId, int amount) {
        Connection conn = null;
        String sql = "UPDATE USER SET user_account_balance = ? WHERE user_id = ?";

        try {
            conn = MySqlConnect.MySqlConnect();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, amount); // 잔액
            pstmt.setString(2, userId); // 아이디

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }

    }

    //수정 (비밀번호 수정)
    public static void updateUserPassword(String userId, String Password) {
        Connection conn = null;
        String sql = "UPDATE USER SET user_password = ? WHERE user_id = ?";



        try {
            conn = MySqlConnect.MySqlConnect();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, Password);
            pstmt.setString(2, userId);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("비밀번호가 성공적으로 수정되었습니다!");
            }
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
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

            System.out.println("회원탈퇴가 완료 되었습니다!");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // db에서 활성화된 사용자 목록 가져옴
    public static ArrayList<String> getUserList() throws SQLException {
        Connection conn = null;
        conn = MySqlConnect.MySqlConnect();
        String sql = "SELECT user_id FROM user WHERE user_active = '1'";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ArrayList<String> arr = new ArrayList<>();
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            arr.add(rs.getString("user_id"));
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
            }
        }
        return arr;
    }

}
