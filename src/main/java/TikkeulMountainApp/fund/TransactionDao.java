package TikkeulMountainApp.fund;

import TikkeulMountainApp.util.MySqlConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class TransactionDao {

    public void addTransaction(Transaction transaction){
        String sql = "INSERT INTO ACCOUNT_HISTORY (transfer_date,transfer_amount,transfer_balance,user_id,party_id)"
            + "VALUES (?,?,?,?,?)";

        try{
            Connection conn = MySqlConnect.MySqlConnect();
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.set
        }

    }
}
