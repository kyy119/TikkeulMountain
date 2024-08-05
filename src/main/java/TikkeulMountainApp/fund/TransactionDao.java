package TikkeulMountainApp.fund;

import TikkeulMountainApp.util.MySqlConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionDao {

    public static void addTransaction(Transaction transaction) {
        Connection conn = null;
        String sql =
            "INSERT INTO ACCOUNT_HISTORY (transfer_date,transfer_amount,transfer_balance,transfer_index,transfer_memo,user_id,party_id) "
                + "VALUES (?,?,?,?,?,?,?)";
        try {
            conn = MySqlConnect.MySqlConnect();
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setTimestamp(1, transaction.getTransferDate());
            pstmt.setInt(2, transaction.getTransferAmount());
            pstmt.setInt(3, transaction.getTransferBalance());
            pstmt.setString(4, transaction.getTransferIndex());
            pstmt.setString(5, transaction.getTransferMemo());
            pstmt.setString(6, transaction.getUserId());
            pstmt.setInt(7, transaction.getPartyId());
            int rows = pstmt.executeUpdate();
            if (rows == 1) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    int bno = rs.getInt(1);
                }
                rs.close();
            }
            pstmt.close();
        } catch (Exception e) {
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

    public static Map<String, Integer> getMemberContributions(int partyId) {
        Connection conn = null;
        String sql = "SELECT user_id,SUM(transfer_amount) FROM ACCOUNT_HISTORY WHERE PARTY_ID = ? AND TRANSFER_INDEX='1' GROUP BY user_id";

        try {
            conn = MySqlConnect.MySqlConnect();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, partyId);

            ResultSet rs = pstmt.executeQuery();

            Map<String, Integer> map = new HashMap<>();
            while (rs.next()) {
                map.put(rs.getString("user_id"), rs.getInt("SUM(transfer_amount)"));

            }

            return map;

        } catch (Exception e) {
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

    public static List<Transaction> getPartyTransaction(int partyId) {
        Connection conn = null;
        String sql = "select transfer_date,transfer_amount,transfer_balance,transfer_index,transfer_memo,user_id from account_history where party_id=? ";

        try {
            conn = MySqlConnect.MySqlConnect();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, partyId);

            ResultSet rs = pstmt.executeQuery();

            List<Transaction> transactionList = new ArrayList<>();
            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransferDate(rs.getTimestamp("transfer_date"));
                transaction.setTransferAmount(rs.getInt("transfer_amount"));
                transaction.setTransferBalance(rs.getInt("transfer_balance"));
                transaction.setTransferIndex(rs.getString("transfer_index"));
                transaction.setTransferMemo(rs.getString("transfer_memo"));
                transaction.setUserId(rs.getString("user_id"));

                transactionList.add(transaction);
            }

            return transactionList;

        } catch (Exception e) {
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

    public static List<String> getDailyContribution(int partyId) {
        Connection conn = null;
        String sql = "select user_id from account_history where date(transfer_date) = curdate() and party_id = ? group by user_id";

        try {
            conn = MySqlConnect.MySqlConnect();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, partyId);

            ResultSet rs = pstmt.executeQuery();
            List<String> userList = new ArrayList<>();
            while (rs.next()) {
                userList.add(rs.getString("user_id"));
            }

            return userList;
        } catch (Exception e) {
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

    public static String getRole(String userId,int partyId){
        Connection conn = null;
        String sql = "select role from membership where user_id = ? and party_id = ?";
        try {
            conn = MySqlConnect.MySqlConnect();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, userId);
            pstmt.setInt(2, partyId);

            ResultSet rs = pstmt.executeQuery();
            String userRole = null;
            if (rs.next()) {
                userRole = rs.getString("role");
            }

            return userRole;
        } catch (Exception e) {
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
}