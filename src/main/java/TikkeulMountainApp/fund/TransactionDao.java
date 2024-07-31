package TikkeulMountainApp.fund;

import TikkeulMountainApp.util.MySqlConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TransactionDao {

    public void addTransaction(Transaction transaction){
        Connection conn = null;
        String sql = "INSERT INTO ACCOUNT_HISTORY (transfer_date,transfer_amount,transfer_balance,transfer_index,user_id,party_id) "
            + "VALUES (?,?,?,?,?,?)";

        try{
            conn = MySqlConnect.MySqlConnect();
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setTimestamp(1,transaction.getTransferDate());
            pstmt.setInt(2,transaction.getTransferAmount());
            pstmt.setInt(3,transaction.getTransferBalance());
            pstmt.setString(4,transaction.getTransferIndex());
            pstmt.setString(5,transaction.getUserId());
            pstmt.setString(6,transaction.getPartyId());

            int rows = pstmt.executeUpdate();
            System.out.println("저장된 행 수: "+rows);

            if(rows==1){
                ResultSet rs = pstmt.getGeneratedKeys();
                if(rs.next()) {
                    int bno = rs.getInt(1);
                    System.out.println("저장된 bno: " + bno);
                }
                rs.close();
            }

            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(conn != null){
                try{
                    conn.close();
                } catch (SQLException e) {}
            }
        }

    }
}
