package TikkeulMountainApp;

import static TikkeulMountainApp.party.PartyService.showPartyList;

import TikkeulMountainApp.fund.FundService;
import TikkeulMountainApp.party.Party;
import TikkeulMountainApp.user.LoginChecker;
import TikkeulMountainApp.user.User;
import TikkeulMountainApp.user.UserDao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class PrintPage {

    static Scanner sc = new Scanner(System.in);

    public static int logInPage() { //로그인 페이지, page=1
        System.out.println("(1)로그인");
        System.out.println("(2)회원가입");
        System.out.print("원하는 메뉴키를 입력하세요:");
        String in = sc.nextLine();
        switch (in){
            case "1": //로그인
                while(true){
                    System.out.println("(B)뒤로가기");
                    System.out.print("아이디를 입력하세요: ");
                    String id = sc.nextLine();
                    if(id.equals("B")||id.equals("b"))
                        return 1;

                    System.out.print("비밀번호를 입력하세요: ");
                    String password = sc.nextLine();
                    boolean isLoggedIn = UserDao.loginUser(id,password);
                    if(isLoggedIn){
                        System.out.println("로그인 성공!");
                        return 2;
                    } else{
                        System.out.println("로그인 실패: 아이디 또는 비밀번호가 잘못되었습니다.");
                    }
                }
            case "9": //회원가입 페이지로 이동
                return 9;
            default:
                System.out.println("다시 압력하세요.");
                return 1;
        }
    }

    public static int mainPage() throws SQLException { //메인 페이지, page=2
        ArrayList<Party> partyArrayList = showPartyList(LoginChecker.getUser().getUser_id());
        partyArrayList.stream().forEach(n-> System.out.println(n.getPartyId()+n.getPartyName()+n.getPartyActive()));

        System.out.println("                       (C) 모임생성");
        System.out.println("(M) 마이페이지           (O) 로그아웃");
        System.out.print("원하는 메뉴키를 입력하세요:");
        String in = sc.nextLine();
        switch (in) {
            case "M": //마이 페이지로 이동
                return 3;
            case "O": //로그아웃 후, 로그인 페이지로 이동
                LoginChecker.setUser(null);
                return 1;
            case "C": //모임 생성 페이지로 이동
                return 6;
            default: //모임 계좌 페이지로 이동 (구현예정)
                return 4;
        }
    }

    public static int myPage() { //마이 페이지, page=3
        User user = UserDao.getUser(LoginChecker.getUser().getUser_id());
        return 1;
    }

    public static int accountPage() {
        return 1;
    }

    public static int accountInfoPage() {
        return 1;
    }

    public static int createAccountPage() {
        return 1;
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
