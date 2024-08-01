package TikkeulMountainApp.party;

import java.sql.PreparedStatement;
import TikkeulMountainApp.util.MySqlConnect;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

public class PartyService {


    //모임 생성 메소드
    public static void createParty() {
        // insert into party (party_name, daily_pay, party_account, party_account_password, party_account_balance, party_account_created_at, category) VALUES
        //   ('홍길동', 500, '3333-10-1234567','1234', 0, '2024-07-31', '여행');
        // int는       *daily_pay              *party_account_balance
        //매개변수화된 SQL 문 작성
        Connection conn = null;

        try {
            conn = MySqlConnect.MySqlConnect();
            String sql = " " +
                "INSERT INTO party (party_name, daily_pay, party_account, party_account_password, party_account_balance, party_account_created_at, category) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

            Party party = new Party("김나나", 200, "3333-10-1231234", "1313", 1000, "2024-07-01",
                "목돈");
            //PreparedStatement 얻기 및 값 지정
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, party.getParty_name());
            pstmt.setInt(2, party.getDaily_pay());
            pstmt.setString(3, party.getParty_account());
            pstmt.setString(4, party.getParty_account_password());
            pstmt.setInt(5, party.getParty_account_balance()); //10만
            pstmt.setString(6, party.getParty_account_created_at());
            pstmt.setString(7, party.getCategory());

            System.out.println(party.getParty_name());
            party.getDaily_pay();
            party.getParty_account();
            party.getParty_account_password();
            party.getParty_account_balance();
            party.getParty_account_created_at();
            party.getCategory();

            //SQL 문 실행
            int rows = pstmt.executeUpdate();
            System.out.println("저장된 행 수: " + rows);

            if (rows == 1) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    int bno = rs.getInt(1);
                    System.out.println("저장된 bno: " + bno);
                }
                rs.close();
            }

            //PreparedStatement 닫기
            pstmt.close();

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {

            if (conn != null) {
                try {
                    //연결 끊기
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public void updatePartyBalance(int partyId, int amount) {
        Connection conn = null;

        try {
            conn = MySqlConnect.MySqlConnect();
            String sql = " " +
                "update party set party_account_balance = ? where party_id = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, amount);
            pstmt.setInt(2, partyId);

            pstmt.executeUpdate();

            pstmt.close();

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {

            if (conn != null) {
                try {
                    //연결 끊기
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }

    }


    public int getPartyBalance(int partyId) {
        Connection conn = null;

        try {
            conn = MySqlConnect.MySqlConnect();
            String sql = " " +
                "select party_account_balance from party where party_id = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, partyId);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("party_account_balance");
            }

            pstmt.close();

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {

            if (conn != null) {
                try {
                    //연결 끊기
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
        return 0;

    }

}

