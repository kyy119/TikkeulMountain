package TikkeulMountainApp.party;

import java.sql.PreparedStatement;
import TikkeulMountainApp.util.MySqlConnect;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PartyService {


    //모임 생성 메소드
    public static void createParty(String cate, String name, int dailyPay, String pw) {
        Connection conn = null;

        try {
            conn = MySqlConnect.MySqlConnect();
            String sql = " " +
                "INSERT INTO party (party_name, daily_pay, party_account, party_account_password, party_account_balance, party_account_created_at, category, party_active) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            String pa = createPartyAccount();
            String ad = partyAccountDate();
            Party party = new Party(name, dailyPay, pa, pw, 0, ad,
                cate, "1");
            //PreparedStatement 얻기 및 값 지정
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, party.getPartyName());
            pstmt.setInt(2, party.getDailyPay());
            pstmt.setString(3, party.getPartyAccount());
            pstmt.setString(4, party.getPartyAccountPassword());
            pstmt.setInt(5, party.getPartyAccountBalance());
            pstmt.setString(6, party.getPartyAccountCreatedAt());
            pstmt.setString(7, party.getCategory());
            pstmt.setString(8, party.getPartyActive());

            System.out.println(party.getPartyName());
            party.getDailyPay();
            party.getPartyAccount();
            party.getPartyAccountPassword();
            party.getPartyAccountBalance();
            party.getPartyAccountCreatedAt();
            party.getCategory();

            //SQL 문 실행
            int rows = pstmt.executeUpdate();
            System.out.println("저장된 행 수: " + rows);

            if (rows == 1) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    int bno = rs.getInt(1);
                    System.out.println("저장된 bno: " + bno);
                    party.setPartyId(bno);
                }
                rs.close();
            }
            insertMemberShip(party.getPartyId(),dailyPay);
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

    public static String checkAccount(){
        Connection conn = MySqlConnect.MySqlConnect();
        String str = partyAccountDate();
        String sql = "";
        return str;
    }

    public static String createPartyAccount() {
        String str = "3333-10-388";
        int a = (int) (Math.random() * 8999) + 1000;
        str += Integer.toString(a);
        return str;
    }

    public static String partyAccountDate() {
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = simpleDateFormat.format(now);
        return str;
    }

    public static void insertMemberShip(int id, int dailyPay) {
        Connection conn = null;
        try {
            conn = MySqlConnect.MySqlConnect();
            String sql = "insert into MEMBERSHIP ( role, user_id, party_id, user_active, party_active, daily_pay) values ( ? , ? , ? ,? ,?, ?)";
            MemberShip memberShip = new MemberShip("방장", "duddbs", id, "1", "1",dailyPay);
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, memberShip.getRole());
            pstmt.setString(2, memberShip.getUserId());
            pstmt.setInt(3, memberShip.getPartyId());
            pstmt.setString(4, memberShip.getUserActive()); //10만
            pstmt.setString(5, memberShip.getPartyActive());
            pstmt.setInt(6,memberShip.getDailyPay());

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

    public static ArrayList<String> showCategory() {///////////////////카테고리 만드는 중
        Connection conn = null;
        ArrayList<String> arr = new ArrayList<>();
        try {
            conn = MySqlConnect.MySqlConnect();
            String sql = "select category from PARTY_CATEGORY ";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                arr.add(rs.getString("category"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arr;
    }


    public static void updatePartyBalance(int partyId, int amount) {
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

    public static int getPartyBalance(int partyId) {
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

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static ArrayList<Party> showPartyList(String userId) throws SQLException {
        Connection conn = MySqlConnect.MySqlConnect();
        String sql = "SELECT party_id, party_name, party_active " + "FROM PARTY " +
            "WHERE party_id IN " +
            "(SELECT party_id FROM MEMBERSHIP WHERE user_id IN " +
            "(SELECT user_id FROM `USER` WHERE user_id = ?))";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, userId);
        ResultSet rs = pstmt.executeQuery();
        ArrayList<Party> arr = new ArrayList<>();
        while (rs.next()) {
            Party party = new Party();
            party.setPartyId(rs.getInt("party_id"));
            party.setPartyName(rs.getString("party_name"));
            String active = rs.getString("party_active");
            if (active.equals("1")) {
                party.setPartyActive(active);
                arr.add(party);
            } else {
                continue;
            }
        }
        return arr;
    }

    public static void searchParty(String userId, String str) throws SQLException {
        Connection conn = MySqlConnect.MySqlConnect();
        ArrayList<Party> arr = showPartyList(userId);
        ArrayList<String> arr2 = new ArrayList<>();
        for (Party party : arr) {
            if (party.getPartyName().contains(str)) {
                arr2.add(party.getPartyName());
            }
        }
        if (arr2.size() == 0) {
            System.out.println("검색 내용이 없습니다.");
        } else {
            for (String str1 : arr2) {
                System.out.println(str1);
            }
        }
    }

    public static void showPartyDetail(int partyId) throws SQLException {
        Connection conn = MySqlConnect.MySqlConnect();
        String sql = "SELECT a.user_id, "
            + "b.party_name, b.category, b.party_account, b.party_account_created_at "
            + "from MEMBERSHIP as a"
            + " INNER JOIN PARTY as b ON a.party_id = b.party_id"
            + " WHERE b.party_id = ?;";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, partyId);
        ResultSet rs = pstmt.executeQuery();
        ArrayList<Party> arrayList = new ArrayList<>();
        while (rs.next()) {
            String userId = rs.getString("user_id");
            String partyName = rs.getString("party_name");
            String category = rs.getString("category");
            String partyAccount = rs.getString("party_account");
            String partyAccountCreatedAt = rs.getString("party_account_created_at");
            Party party = new Party(userId, partyName, category, partyAccount,
                partyAccountCreatedAt);
            arrayList.add(party);
        }
        for (Party party : arrayList) {
            System.out.println(party.getUserId());
        }
        System.out.println(arrayList.get(0).getPartyName());
        System.out.println(arrayList.get(0).getCategory());
        System.out.println(arrayList.get(0).getPartyAccount());
        System.out.println(arrayList.get(0).getPartyAccountCreatedAt());
    }

    public static void deleteParty(String account) throws SQLException {
        Connection conn = MySqlConnect.MySqlConnect();
        if (checkZero(account)) {
            System.out.println("잔액이 있습니다. 삭제 불가");
            return;
        }
        String sql = "DELETE FROM PARTY WHERE party_account = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, account);
        pstmt.executeUpdate();
        System.out.println("삭제 완료");
    }

    public static boolean checkZero(String account) throws SQLException {
        Connection conn = MySqlConnect.MySqlConnect();
        //select party_account_balance from party where party_name = '김나나';
        String sql = "SELECT party_account_balance FROM party WHERE party_account = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, account);
        int price = 0;
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            price = rs.getInt("party_account_balance");
        }
        if (price == 0) {
            return false;
        }
        return true;
    }

    public static void showParty(int id) throws SQLException {
        Connection conn = MySqlConnect.MySqlConnect();
        String sql = "SELECT party_name, party_account, party_account_balance FROM PARTY WHERE party_id = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        ArrayList<String> arr = new ArrayList<>();
        while (rs.next()) {
            arr.add(rs.getString("party_name"));
            arr.add(rs.getString("party_account"));
            arr.add(Integer.toString(rs.getInt("party_account_balance")));
        }

        System.out.println(arr.get(0));
        System.out.println(arr.get(1));
        System.out.println(arr.get(2));
    }

    public static ArrayList<MemberShip> getMemberList() throws SQLException {
        Connection conn = MySqlConnect.MySqlConnect();
        String sql = "SELECT user_id, party_id, daily_pay FROM MEMBERSHIP WHERE user_active = '1' AND party_active = '1'";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        ArrayList<MemberShip> arr = new ArrayList<>();
        while (rs.next()) {
            MemberShip memberShip = new MemberShip();
            memberShip.setUserId(rs.getString("user_id"));
            memberShip.setPartyId(rs.getInt("party_id"));
            memberShip.setDailyPay(rs.getInt("daily_pay"));
            arr.add(memberShip);
        }
        return arr;
    }
}

