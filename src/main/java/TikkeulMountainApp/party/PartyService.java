package TikkeulMountainApp.party;

import TikkeulMountainApp.user.LoginChecker;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import TikkeulMountainApp.util.MySqlConnect;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PartyService {

    //카테고리 선택 예외 처리
    public static String checkCate(String cate) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                if (Integer.parseInt(cate) < 1 || Integer.parseInt(cate) >= 5) {
                    System.out.println("1 ~ 4 숫자만 입력하세요");
                    System.out.print("카테고리 선택 : ");
                    cate = br.readLine();
                } else {
                    return cate;
                }
            } catch (NumberFormatException e) {
                System.out.println("1 ~ 4 숫자만 입력하세요");
                System.out.print("카테고리 선택 : ");
                cate = br.readLine();
            }
        }

    }

    //모임 생성 메소드
    public static void createParty(String cate, String name, int dailyPay, String pw,
        List<String> strings) {
        Connection conn = null;

        try {
            conn = MySqlConnect.MySqlConnect();
            String sql = " " +
                "INSERT INTO party (party_name, daily_pay, party_account, party_account_password, party_account_balance, party_account_created_at, category, party_active) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            String pa = checkAccount();
            String ad = partyAccountDate();
            Party party = new Party(name, dailyPay, pa, pw, 0, ad,
                cate, "1");
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, party.getPartyName());
            pstmt.setInt(2, party.getDailyPay());
            pstmt.setString(3, party.getPartyAccount());
            pstmt.setString(4, party.getPartyAccountPassword());
            pstmt.setInt(5, party.getPartyAccountBalance());
            pstmt.setString(6, party.getPartyAccountCreatedAt());
            pstmt.setString(7, party.getCategory());
            pstmt.setString(8, party.getPartyActive());

            party.getDailyPay();
            party.getPartyAccount();
            party.getPartyAccountPassword();
            party.getPartyAccountBalance();
            party.getPartyAccountCreatedAt();
            party.getCategory();

            int rows = pstmt.executeUpdate();

            if (rows == 1) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    int bno = rs.getInt(1);
                    party.setPartyId(bno);
                }
                rs.close();
            }
            insertMemberShip(party.getPartyId(), dailyPay);
            insertNormalMember(party.getPartyId(), strings, dailyPay);
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
    }
    //매일 납부 금액 확인
    public static String checkDailyPay(String dailyPay) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                int pay = Integer.parseInt(dailyPay);
                if (pay > 1000 || pay <= 0) {
                    System.out.println("매일 납부 금액은 1 ~ 1000원 사이로 지정 가능합니다.");
                    System.out.print("납부 금액 입력 : ");
                    dailyPay = br.readLine();
                } else {
                    return dailyPay;
                }
            } catch (NumberFormatException e) {
                System.out.println("숫자만 입력해주세요.");
                System.out.print("납부 금액 입력 : ");
                dailyPay = br.readLine();
            }
        }
    }
    //계좌 비밀번호 유효성 검사
    public static String checkPw(String pw) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (pw.length() != 4) {
            System.out.println("계좌 비밀 번호는 4자리로 입력해주세요.");
            System.out.print("계좌 비밀 번호 입력: ");
            pw = br.readLine();
        }
        System.out.print("계좌 비밀 번호 확인 : ");
        String cpw = br.readLine();
        while (!pw.equals(cpw)) {
            System.out.print("비밀 번호 동일 하게 입력 :");
            cpw = br.readLine();
        }
        System.out.println("비밀번호가 확인되었습니다.");
        return cpw;
    }
    //계좌 중복 검사
    public static String checkAccount() {
        Connection conn = null;
        String str = "";
        try {
            conn = MySqlConnect.MySqlConnect();
            str = createPartyAccount();
            String sql = "SELECT party_account FROM PARTY";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ArrayList<String> arr = new ArrayList<>();
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                arr.add(rs.getString("party_account"));
            }
            int index = 0;
            while (true) {
                index = 0;
                for (String str1 : arr) {
                    if (str1.equals(str)) {
                        str = createPartyAccount();
                        index = 1;
                        break;
                    }
                }
                if (index == 0) {
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
            }
        }
        return str;
    }
    //계좌 랜덤 생성
    public static String createPartyAccount() {
        String str = "3333-10-388";
        int a = (int) (Math.random() * 8999) + 1000;
        str += Integer.toString(a);
        return str;
    }
    //모임 계좌 생성일 생성
    public static String partyAccountDate() {
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = simpleDateFormat.format(now);
        return str;
    }
    //membership 테이블에 방장 넣기
    public static void insertMemberShip(int id, int dailyPay) {
        Connection conn = null;
        try {
            conn = MySqlConnect.MySqlConnect();
            String sql = "insert into MEMBERSHIP ( role, user_id, party_id, user_active, party_active, daily_pay) values ( ? , ? , ? ,? ,?, ?)";
            MemberShip memberShip = new MemberShip("1", LoginChecker.getUser().getUserId(), id,
                "1", "1", dailyPay);
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, memberShip.getRole());
            pstmt.setString(2, memberShip.getUserId());
            pstmt.setInt(3, memberShip.getPartyId());
            pstmt.setString(4, memberShip.getUserActive()); //10만
            pstmt.setString(5, memberShip.getPartyActive());
            pstmt.setInt(6, memberShip.getDailyPay());

            int rows = pstmt.executeUpdate();

            if (rows == 1) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    int bno = rs.getInt(1);
                }
                rs.close();
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
    }
    //membership 테이블에 일반 멤버 넣기
    public static void insertNormalMember(int id, List<String> strings, int dailyPay) {
        Connection conn = null;
        try {
            conn = MySqlConnect.MySqlConnect();
            for (int i = 0; i < strings.size(); i++) {
                String sql = "insert into MEMBERSHIP ( role, user_id, party_id, user_active, party_active, daily_pay) values ( ? , ? , ? ,? ,?, ?)";
                MemberShip memberShip = new MemberShip("0", strings.get(i), id, "1", "1", dailyPay);
                PreparedStatement pstmt = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, memberShip.getRole());
                pstmt.setString(2, memberShip.getUserId());
                pstmt.setInt(3, memberShip.getPartyId());
                pstmt.setString(4, memberShip.getUserActive());
                pstmt.setString(5, memberShip.getPartyActive());
                pstmt.setInt(6, memberShip.getDailyPay());
                int rows = pstmt.executeUpdate();
                if (rows == 1) {
                    ResultSet rs = pstmt.getGeneratedKeys();
                    if (rs.next()) {
                        int bno = rs.getInt(1);
                    }
                    rs.close();
                }
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //카테고리 선택 보여주기
    public static ArrayList<String> showCategory() {
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
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return arr;
    }

    //모임 통장 잔액 수정
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
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
    }
    //모임 계좌 잔액 가져오기
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
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
        return 0;

    }
    //들어가 있는 모임 리스트 가져오기
    public static ArrayList<Party> showPartyList(String userId) {
        Connection conn = null;
        ArrayList<Party> arr = new ArrayList<>();
        try {
            conn = MySqlConnect.MySqlConnect();
            String sql =
                "SELECT party_id, party_name, daily_pay, party_active, party_account, party_account_balance, category "
                    + "FROM PARTY " +
                    "WHERE party_id IN " +
                    "(SELECT party_id FROM MEMBERSHIP WHERE user_id = ? and party_active= '1')";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Party party = new Party();
                party.setPartyId(rs.getInt("party_id"));
                party.setPartyName(rs.getString("party_name"));
                String active = rs.getString("party_active");
                party.setDailyPay(rs.getInt("daily_pay"));
                party.setPartyAccount(rs.getString("party_account"));
                party.setPartyAccountBalance(rs.getInt("party_account_balance"));
                party.setCategory(rs.getString("category"));
                if (active.equals("1")) {
                    party.setPartyActive(active);
                    arr.add(party);
                } else {
                    continue;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return arr;
    }
    //모임 상세보기
    public static List<Party> showPartyDetail(int partyId) {
        Connection conn = null;
        ArrayList<Party> arrayList = new ArrayList<>();
        try {
            conn = MySqlConnect.MySqlConnect();
            String sql = "SELECT a.user_id, a.party_active, "
                + "b.party_name, b.category, b.party_account, b.party_account_created_at, b.party_account_balance "
                + "from MEMBERSHIP as a"
                + " INNER JOIN PARTY as b ON a.party_id = b.party_id"
                + " WHERE b.party_id = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, partyId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String userId = rs.getString("user_id");
                String partyActive = rs.getString("party_active");
                String partyName = rs.getString("party_name");
                String category = rs.getString("category");
                String partyAccount = rs.getString("party_account");
                String partyAccountCreatedAt = rs.getString("party_account_created_at");
                int partyAccountBalance = rs.getInt("party_account_balance");
                Party party = new Party(userId, partyActive, partyName, category, partyAccount,
                    partyAccountCreatedAt, partyAccountBalance);
                arrayList.add(party);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return arrayList;
    }
    //모임 삭제
    public static boolean deleteParty(int id) {
        Connection conn = null;
        try {
            int price = checkZero(id);
            if (price == -1) {
                System.out.println("비정상적인 접근.");
                return false;
            } else if (price > 0) {
                System.out.println("잔액이 있습니다. 모임 통장을 삭제하려면 통장의 잔액을 비워주세요.");
                return false;
            }
            conn = MySqlConnect.MySqlConnect();
            String sql = "UPDATE MEMBERSHIP SET party_active = '0' WHERE party_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            sql = "UPDATE PARTY SET party_active = '0' WHERE party_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    //잔액 0원 확인
    public static int checkZero(int id) {
        Connection conn = null;
        int price = -1;
        try {
            conn = MySqlConnect.MySqlConnect();
            String sql = "SELECT party_account_balance FROM party WHERE party_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                price = rs.getInt("party_account_balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return price;
    }

    //모임 정보 가져오기
    public static Party getParty(int id) {
        Connection conn = null;
        Party party = new Party();
        try {
            conn = MySqlConnect.MySqlConnect();
            String sql = "SELECT party_name, party_account, party_account_balance,party_account_password FROM PARTY WHERE party_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                party.setPartyName(rs.getString("party_name"));
                party.setPartyAccount(rs.getString("party_account"));
                party.setPartyAccountBalance(rs.getInt("party_account_balance"));
                party.setPartyAccountPassword(rs.getString("party_account_password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return party;

    }
    //모임 멤버 정보 가져오기
    public static ArrayList<MemberShip> getMemberList() {
        Connection conn = null;
        ArrayList<MemberShip> arr = new ArrayList<>();
        try {
            conn = MySqlConnect.MySqlConnect();
            String sql = "SELECT user_id, party_id, daily_pay FROM MEMBERSHIP WHERE user_active = '1' AND party_active = '1'";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                MemberShip memberShip = new MemberShip();
                memberShip.setUserId(rs.getString("user_id"));
                memberShip.setPartyId(rs.getInt("party_id"));
                memberShip.setDailyPay(rs.getInt("daily_pay"));
                arr.add(memberShip);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return arr;
    }
    //모임 비활성화
    public static void updatePartyActive(String userId, int partyId) {
        Connection conn = null; // Connection 객체 선언
        String sql2 = "UPDATE MEMBERSHIP SET party_active = '0' WHERE user_id = ? AND party_id = ?";
        try {
            conn = MySqlConnect.MySqlConnect();
            PreparedStatement pstmt = conn.prepareStatement(sql2);
            pstmt.setString(1, userId);
            pstmt.setInt(2, partyId);
            pstmt.executeUpdate();

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

}