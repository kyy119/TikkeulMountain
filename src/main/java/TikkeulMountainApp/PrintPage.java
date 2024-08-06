package TikkeulMountainApp;

import static TikkeulMountainApp.party.PartyService.checkCate;
import static TikkeulMountainApp.party.PartyService.createParty;
import static TikkeulMountainApp.party.PartyService.showPartyList;
import static TikkeulMountainApp.user.UserDao.isUserIdExists;

import TikkeulMountainApp.fund.FundService;
import TikkeulMountainApp.fund.Transaction;
import TikkeulMountainApp.fund.TransactionDao;
import TikkeulMountainApp.party.Party;
import TikkeulMountainApp.party.PartyChecker;
import TikkeulMountainApp.party.PartyService;
import TikkeulMountainApp.party.PrivacyPolicy;
import TikkeulMountainApp.user.LoginChecker;
import TikkeulMountainApp.user.User;
import TikkeulMountainApp.user.UserDao;
import TikkeulMountainApp.user.UserMainApp;
import TikkeulMountainApp.util.ASCII;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class PrintPage {

    static Scanner sc = new Scanner(System.in);

    public static int logInPage() throws InterruptedException { //로그인 페이지, page=1

//        ASCII.printTikkeulMountain();
        System.out.println("============================ 로그인 화면 ============================");
        System.out.println("(1)로그인");
        System.out.println("(2)회원가입");
        System.out.println("=====================================================================");
        System.out.print("원하는 메뉴키를 입력하세요:");
        String in = sc.nextLine();
        switch (in) {
            case "1": //로그인
                while (true) {
                    System.out.println("(B)뒤로가기");
                    System.out.print("아이디를 입력하세요: ");
                    String id = sc.nextLine();
                    if (id.equals("B") || id.equals("b")) {
                        return 1;
                    }

                    System.out.print("비밀번호를 입력하세요: ");
                    String password = sc.nextLine();
                    boolean isLoggedIn = UserDao.loginUser(id, password);
                    if (isLoggedIn) {
                        System.out.println("로그인 성공!");
                        return 2;
                    } else {
                        System.out.println("로그인 실패: 아이디 또는 비밀번호가 잘못되었습니다.");
                        System.out.println();
                    }
                }
            case "2": //회원가입 페이지로 이동
                return 9;
            default:
                System.out.println("다시 압력하세요.");
                return 1;
        }
    }

    public static int mainPage() throws SQLException { //메인 페이지, page=2
        ArrayList<Party> partyArrayList = showPartyList(LoginChecker.getUser().getUser_id());
        System.out.println("======================== 나의 모임 ========================");
        for (int i = 0; i < partyArrayList.size(); i++) {
            System.out.print(i + 1 + ". ");
            System.out.println(partyArrayList.get(i).getPartyName() + "   " + partyArrayList.get(i)
                .getPartyAccountBalance() + "원");
        }
        System.out.println("======================== 모임 메뉴 ========================");
        System.out.println("                      (C) 모임생성");
        System.out.println("(M) 마이페이지           (O) 로그아웃");
        System.out.println("원하는 메뉴키를 입력하세요: ");
        String in = sc.nextLine();
        switch (in) {
            case "M":
            case "m"://마이 페이지로 이동
                return 3;
            case "O":
            case "o"://로그아웃 후, 로그인 페이지로 이동
                LoginChecker.setUser(null);
                return 1;
            case "C":
            case "c"://모임 생성 페이지로 이동
                return 6;
            default: //모임 계좌 페이지로 이동 (구현예정)
                try {
                    int intIn = Integer.parseInt(in);
                    PartyChecker.setParty(partyArrayList.get(intIn - 1));
                    return 4;
                } catch (NumberFormatException e){
                    System.out.println("올바르지 않은 값입니다. 다시 입력하세요.");
                    System.out.println();
                    return 2;
                }
        }
    }

    public static int myPage() throws SQLException { //마이 페이지, page=3
        User user = UserDao.getUser(LoginChecker.getUser().getUser_id());
        user.printInfo();

        System.out.println("(B) 뒤로가기                  (U)비밀번호 수정 (D)탈퇴");
        System.out.print("원하는 메뉴키를 입력하세요: ");
        String in = sc.nextLine();
        switch (in) {
            case "B":
            case "b":
                return 2;
            case "U":
            case "u":
                System.out.print("본인 확인을 위해 비밀번호를 입력하세요:");
                String passWord = sc.nextLine();
                if (passWord.equals(LoginChecker.getUser().getUser_password())) {
                    System.out.print("바꿀 비밀번호를 입력하세요.");
                    String newPassword = sc.nextLine();
                    UserDao.updateUserPassword(LoginChecker.getUser().getUser_id(), newPassword);
                } else {
                    System.out.println("비밀번호가 틀렸습니다.");
                }
                return 3;
            case "D":
            case "d": //회원 탈퇴: delete가 아닌 userActive를 0으로 수정
                List<Party> partyList = PartyService.showPartyList(LoginChecker.getUser()
                    .getUser_id());
                if(partyList.size()==0) {
                    System.out.print("정말 탈퇴하시겠습니까?[y/n]");
                    String in2 = sc.nextLine();
                    if (in2.equals("y")) {
                        String compareString = LoginChecker.getUser().getUser_id() + " 회원탈퇴 하겠습니다.";
                        System.out.println("따라치세요");
                        System.out.print(compareString);
                        String compareStringIn = sc.nextLine();
                        if (compareString.equals(compareStringIn)) {
                            UserDao.updateUserActive(LoginChecker.getUser().getUser_id(),
                                "0"); //userActive를 0으로 바꿔줌
                            LoginChecker.setUser(null);
                            return 1;
                        }
                    } else if (in2.equals("n")) {
                    } else {
                        System.out.println("다시 입력해주세요.");
                    }
                } else{
                    System.out.println("회원 탈퇴하려면 가입한 모임을 다 탈퇴해주세요.");
                }
            default:
                return 3;
        }
    }

    public static int accountPage() { //모임계좌 페이지, page=4
        Party party = PartyChecker.getParty();
        party.printParty();
        System.out.print("당일 납부자: ");
        List<String> userList = TransactionDao.getDailyContribution(party.getPartyId());

        if(userList.size()==0){
            System.out.println("없음.");
        } else {
            for (int i = 0; i < userList.size(); i++) {
                if (i == userList.size() - 1) {
                    System.out.println(userList.get(i));
                } else {
                    System.out.print(userList.get(i) + ", ");
                }
            }
        }
        System.out.println("---------------");
        List<Transaction> transactionList = TransactionDao.getPartyTransaction(party.getPartyId());
        transactionList.stream().forEach(n -> n.printTransaction());

        System.out.println("(D)입금하기  (W)출금하기");
        System.out.println("(I) 계좌정보보기     (B) 뒤로가기");
        System.out.print("원하는 메뉴키를 입력하세요:");
        String in = sc.nextLine();
        switch (in) {
            case "B":
            case "b"://메인 페이지로 이동
                return 2;
            case "I":
            case "i"://계좌정보 페이지로 이동
                return 5;
            case "D":
            case "d":
                return 7;
            case "W":
            case "w":
                return 8;
            default: //
                return 4;
        }
    }

    public static int accountInfoPage() throws SQLException { //계좌 정보 페이지, page=5
        Party party = PartyChecker.getParty();
        List<Party> partyList = PartyService.showPartyDetail(party.getPartyId());
        Map<String, Integer> map = TransactionDao.getMemberContributions(party.getPartyId());
        for (int i = 0; i < partyList.size(); i++) {
            if(i == 0){
                System.out.print(i + 1 + "번 멤버(방장):");
            }else{
                if(partyList.get(i).getPartyActive().equals("0")){
                    System.out.print("(휴면 계정) ");
                }
                System.out.print(i + 1 + "번 멤버:");
            }
            System.out.print(partyList.get(i).getUserId());
            Integer v = map.get(partyList.get(i).getUserId());
            if (v == null) {
                System.out.println(" -> 납부 금액 : 0원");
            } else {
                System.out.println(" -> 납부 금액 : " + v);
            }
        }

        System.out.println("파티 이름 : " + partyList.get(0).getPartyName());
        System.out.println("파티 종륲 : " + partyList.get(0).getCategory());
        System.out.println("파티 계좌번호 : " + partyList.get(0).getPartyAccount());
        System.out.println("파티 계좌 잔액 : " + partyList.get(0).getPartyAccountBalance());
        System.out.println("파티 생성일 : " + partyList.get(0).getPartyAccountCreatedAt());

        System.out.println("(D) 모임 삭제        (B) 뒤로가기");
        System.out.print("원하는 메뉴키를 입력하세요:");
        String in = sc.nextLine();
        switch (in) {
            case "B":
            case "b":
                return 4;
            case "d":
            case "D":
                //방장일때
                if(TransactionDao.getRole(LoginChecker.getUser().getUser_id(),party.getPartyId()).equals("1")){
                    if (PartyService.deleteParty(party.getPartyId())) {
                        return 2;
                    }
                    else{
                        return 5;
                    }
                }else{
                    PartyService.updatePartyActive(LoginChecker.getUser().getUser_id(), party.getPartyId());
                    return 2;
                }

            default:
                System.out.println("다시 맞는 키를 입력해주세요");
                return 5;

        }
    }

    //모임계좌생성, page=6
    public static int createAccountPage() throws IOException, SQLException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("-----");
        ArrayList<String> arr = PartyService.showCategory();
        for (int i = 0; i < arr.size(); i++) {
            System.out.print(i + 1 + ". ");
            System.out.println(arr.get(i));
        }
        System.out.print("카테고리 선택 : ");
        String cate = br.readLine();
        cate = PartyService.checkCate(cate);
        System.out.print("모임 이름 : ");
        String name = br.readLine();
        System.out.print("매일 납부 예정 금액 : ");
        String dailyPay = br.readLine();
        dailyPay = PartyService.checkDailyPay(dailyPay);

        System.out.print("계좌 비밀 번호 : ");
        String pw = br.readLine();
        pw = PartyService.checkPw(pw);

        ArrayList<String> friendIDs = new ArrayList<>();
        List<String> userList = UserDao.getUserList();

        while (true) {
            System.out.print("친구를 추가하시겠습니까?[y/n]:");
            String in = sc.nextLine();
            if (in.equals("y")) {
                System.out.print("친구 ID를 입력하세요:");
                String friendId = sc.nextLine();
                int index = 1;
                for (int i = 0; i < userList.size(); i++) {
                    if (friendId.equals(LoginChecker.getUser().getUser_id())) {
                        System.out.println("본인은 입력이 불가능합니다.");
                        index = 0;
                        break;
                    }
                    if (userList.get(i).equals(friendId)) {
                        int idx = 1;
                        index = 0;
                        for(String str : friendIDs){
                            if(str.equals(friendId)){
                                idx = 0;
                                break;
                            }
                        }
                        if(idx == 0){
                            System.out.println("전에 이미 추가하였습니다");
                            break;
                        }
                        friendIDs.add(friendId);
                        System.out.println("친구 아이디 추가 완료!");
                        break;
                    }
                }
                if (index == 1) {
                    System.out.println("없는 아이디입니다.");
                }

            } else if (in.equals("n")) {
                break;
            } else {
                System.out.println("다시 입력해주세요.");
            }
        }
        PrivacyPolicy.PrivacyTerms();
        System.out.print("동의하십니까?(y/n)");
        String agree = sc.nextLine();
        switch (agree) {
            case "y":
            case "Y":
                int dailyPayInt = Integer.parseInt(dailyPay);
                ArrayList<String> arrayList = new ArrayList<>(friendIDs);
                PartyService.createParty(arr.get(Integer.parseInt(cate) - 1), name, dailyPayInt, pw,
                    arrayList);
                return 2;
            case "n":
            case "N":
                System.out.println("미동의 계설 불가 메인페이지도 이동합니다.");
                return 2;
        }
        return 2;

    }

    //입금페이지, page=7
    public static int depositPage() {

        int newPartyBalance = FundService.deposit(LoginChecker.getUser().getUser_id(),
            PartyChecker.getParty().getPartyId());
        PartyChecker.getParty().setPartyAccountBalance(newPartyBalance);
        return 4;
    }

    public static int withdrawPage() throws SQLException {

        String userRole = TransactionDao.getRole(LoginChecker.getUser().getUser_id(),
            PartyChecker.getParty()
                .getPartyId());
        if (userRole.equals("0")) {
            System.out.println("방장만 출금 가능합니다.");
        } else {
            int newPartyBalance = FundService.withdraw(LoginChecker.getUser().getUser_id(),
                PartyChecker.getParty().getPartyId());
            PartyChecker.getParty().setPartyAccountBalance(newPartyBalance);

        }
        return 4;
    }


    public static int signUpPage() {

        // 중복 및 유효성 검사
        String userId = null;
        String userName = null;
        String userPassword = null;
        String userPhone = null;
        String userAccount = null;

        while (true) {
            System.out.print("아이디를 입력하세요: ");
            userId = sc.nextLine();
            if (isUserIdExists(userId)) {
                System.out.println("이미 존재하는 아이디입니다.");

            } else {
                System.out.println("사용 가능한 아이디입니다.");
                break;
            }
        }

        while (true) {
            System.out.print("비밀번호를 입력하세요: ");
            userPassword = sc.nextLine();
            if (!UserDao.isValidPassword(userPassword)) {
                System.out.println("비밀번호는 최소 6자 이상, 영문, 특수문자 포함해야 합니다.");
            } else {
                System.out.println("사용 가능한 비밀번호입니다.");
                break;
            }
        }

        System.out.print("이름을 입력하세요: ");
        userName = sc.nextLine();

        //비밀번호

        while (true) {
            System.out.print("전화번호를 입력하세요 (010-xxxx-xxxx): ");
            userPhone = sc.nextLine();
            if (!userPhone.matches("010-\\d{4}-\\d{4}") || UserDao.isUserPhoneExists(userPhone)) {
                System.out.println("전화번호가 잘못되었거나 중복됩니다.");
            } else {
                System.out.println("사용 가능한 전화번호입니다.");
                break;
            }
        }

        while (true) {
            System.out.print("계좌번호를 입력하세요 (3333-xx-xxxxxxx): ");
            userAccount = sc.nextLine();
            if (!userAccount.matches("\\d{4}-\\d{2}-\\d{7}") || UserDao.isUserAccountExists(
                userAccount)) {
                System.out.println("계좌번호가 잘못되었거나 중복됩니다.");
            } else {
                System.out.println("사용 가능한 계좌번호입니다.");
                break;
            }
        }
        UserDao.registerUser(new User(userId, userName, userPassword, userPhone, userAccount));
        return 1;

    }
}
