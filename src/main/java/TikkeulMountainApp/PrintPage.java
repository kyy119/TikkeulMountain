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
import TikkeulMountainApp.user.UserMainApp;
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

//        ASCII.printTikkeulMountain();
        System.out.println("===========1================= 로그인 화면 ============================");
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
            System.out.println(partyArrayList.get(i).getPartyName()+"   "+partyArrayList.get(i).getPartyAccountBalance()+"원");
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

//                Party party = PartyService.getParty(intIn);
//                party.setPartyId(intIn);
//                PartyChecker.setParty(party);

                PartyChecker.setParty(partyArrayList.get(intIn-1));
                return 4;
        }
    }

    public static int myPage() { //마이 페이지, page=3
        User user = UserDao.getUser(LoginChecker.getUser().getUser_id());
        user.printInfo();

        System.out.println("(B) 뒤로가기                  (U)비밀번호 수정 (D)탈퇴");
        System.out.print("원하는 메뉴키를 입력하세요: ");
        String in = sc.nextLine();
        switch (in) {
            case "B": case "b":
                return 2;
            case "U": case "u":
                System.out.print("본인 확인을 위해 비밀번호를 입력하세요:");
                String passWord = sc.nextLine();
                if (passWord.equals(LoginChecker.getUser().getUser_password())) {
                    System.out.print("바꿀 비밀번호를 입력하세요.");
                    String newPassword = sc.nextLine();
                    UserDao.updateUserPassword(LoginChecker.getUser().getUser_id(),newPassword);
                } else{
                    System.out.println("비밀번호가 틀렸습니다.");
                }
                return 3;
            case "D": case "d":
                System.out.println("정말 탈퇴하시겠습니까?");
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

    public static int createAccountPage() throws IOException, SQLException {  //모임계좌생성
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

        List<String> friendIDs = new ArrayList<>();
        List<String> userList = UserDao.getUserList();

        while(true){
            System.out.print("친구를 추가하시겠습니까?[y/n]:");
            String in = sc.nextLine();
            if(in.equals("y")){
                System.out.print("친구 ID를 입력하세요:");
                String friendId = sc.nextLine();
                int index = 1;
                for(int i = 0; i < userList.size(); i ++){
                    if(userList.get(i).equals(friendId)){
                        friendIDs.add(friendId);
                        index = 0;
                        break;
                    }
                }
                if(index == 1){
                    System.out.println("없는 아이디입니다.");
                }

            } else if(in.equals("n")){
                break;
            } else{
                System.out.println("다시 입력해주세요.");
            }
        }
        int dailyPayInt = Integer.parseInt(dailyPay);
        PartyService.createParty(cate, name, dailyPayInt, pw, friendIDs);


        return 2; //ㄱ
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
