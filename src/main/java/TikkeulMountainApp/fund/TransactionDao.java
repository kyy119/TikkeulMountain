package TikkeulMountainApp.fund;

import TikkeulMountainApp.util.MySqlConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
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
            System.out.println("저장된 행 수: " + rows);
            if (rows == 1) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    int bno = rs.getInt(1);
                    System.out.println("저장된bno: " + bno);
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

    public static Map<String, Integer> getMemberContributions(int partyId){
        Connection conn = null;
        String sql = "SELECT user_id,SUM(transfer_amount) FROM ACCOUNT_HISTORY WHERE PARTY_ID = ? GROUP BY user_id";

        try{
            conn = MySqlConnect.MySqlConnect();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,partyId);

            ResultSet rs = pstmt.executeQuery();

            Map<String, Integer> map = new HashMap<>();
            while(rs.next()){
                map.put(rs.getString("user_id"),rs.getInt("SUM(transfer_amount)"));

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
}