package TikkeulMountainApp;

import static TikkeulMountainApp.party.PartyService.createParty;
import static TikkeulMountainApp.party.PartyService.showPartyList;

import TikkeulMountainApp.fund.Transaction;
import TikkeulMountainApp.fund.TransactionDao;
import TikkeulMountainApp.party.Party;
import TikkeulMountainApp.party.PartyChecker;
import TikkeulMountainApp.party.PartyService;
import TikkeulMountainApp.user.LoginChecker;
import TikkeulMountainApp.user.User;
import TikkeulMountainApp.user.UserDao;
import TikkeulMountainApp.util.ASCII;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PrintPage {

    static Scanner sc = new Scanner(System.in);

    public static int logInPage() throws InterruptedException { //로그인 페이지, page=1

        ASCII.printTikkeulMountain();
        System.out.println("============================ 로그인 화면 ============================");
        System.out.println("(1)로그인");
        System.out.println("(2)회원가입");
        System.out.println("=====================================================================");
        System.out.print("원하는 메뉴키를 입력하세요:");
        String in = sc.nextLine();
        switch (in) {
            case "1": //로그인
                while (true) {
                 //   System.out.println("UserDao");
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
        for(int i = 0; i < partyArrayList.size(); i ++){
            System.out.print(i+1 + ". ");
            System.out.println(partyArrayList.get(i).getPartyName());
        }
//        partyArrayList.stream().forEach(
//            n -> System.out.println(n.getPartyId() +". "+n.getPartyName()));
        System.out.println("======================== 모임 메뉴 ========================");
        System.out.println("                      (C) 모임생성");
        System.out.println("(M) 마이페이지           (O) 로그아웃");
        System.out.println("원하는 메뉴키를 입력하세요: ");
        String in = sc.nextLine();
        switch (in) {
            case "M": case "m"://마이 페이지로 이동
                return 3;
            case "O": case "o"://로그아웃 후, 로그인 페이지로 이동
                LoginChecker.setUser(null);
                return 1;
            case "C": case "c"://모임 생성 페이지로 이동 (구현예정)
                return 6;
            default: //모임 계좌 페이지로 이동 (구현예정)
                int intIn = Integer.parseInt(in);
                Party party = PartyService.getParty(intIn); //partyId 잘못 입력했을 때 제약조건 설정해야함
                party.setPartyId(intIn);
                PartyChecker.setParty(party);
                partyArrayList.get(intIn-1).getPartyId();
                return 4;
        }
    }

    public static int myPage() { //마이 페이지, page=3
        User user = UserDao.getUser(LoginChecker.getUser().getUser_id());
        user.printInfo();
        System.out.println("(B) 뒤로가기");
        System.out.print("원하는 메뉴키를 입력하세요: ");
        String in = sc.nextLine();
        switch (in) {
            case "B": case "b":
                return 2;
            default:
                return 3;
        }
    }

    public static int accountPage() { //모임계좌 페이지, page=4
        Party party = PartyChecker.getParty();
        party.printParty();
        System.out.println("---------------");
        List<Transaction> transactionList = TransactionDao.getPartyTransaction(party.getPartyId());
        transactionList.stream().forEach(n->n.printTransaction());

        System.out.println("(I) 계좌정보보기     (B) 뒤로가기");
        System.out.print("원하는 메뉴키를 입력하세요:");
        String in = sc.nextLine();
        switch (in) {
            case "B": case "b"://메인 페이지로 이동
                return 2;
            case "I": case "i"://계좌정보 페이지로 이동
                return 5;
            default: //
                return 4;
        }
    }

    public static int accountInfoPage() { //계좌 정보 페이지, page=5
      Party party = PartyChecker.getParty();
        party.printParty();
        System.out.println("                     (B) 뒤로가기");
        System.out.print("원하는 메뉴키를 입력하세요:");
        String in = sc.nextLine();
        switch(in) {
            case "B" : case "b":
                return 4;
            default:
                System.out.println("다시 맞는 키를 입력해주세요");
                return  5;

        }
    }

    public static int createAccountPage() throws IOException {  //모임계좌생성
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("-----");
        ArrayList<String> arr = PartyService.showCategory();
        for (int i = 0; i < arr.size(); i++) {
            System.out.print(i + 1 + ". ");
            System.out.println(arr.get(i));
        }
        System.out.print("카테고리 선택 : ");
        String cate = br.readLine();



        System.out.print("모임 이름 : ");
        String name = br.readLine();
        System.out.print("매일 납부 예정 금액 : ");
        String dailyPay = br.readLine();
        dailyPay = PartyService.checkDailyPay(dailyPay);


        System.out.print("계좌 비밀 번호 : ");
        String pw = br.readLine();
        pw = PartyService.checkPw(pw);
        int dailyPayInt = Integer.parseInt(dailyPay);
        PartyService.createParty(cate, name, dailyPayInt, pw);


        return 3; //ㄱ
    }

    public static int depositPage() {

//        FundService.deposit(LoginChecker.getUser().getUser_id(), );
        return 4;
    }

    public static int withdrawPage() {
        return 4;
    }

    public static int signUpPage() {
        return 1;
    }
}
