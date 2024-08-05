package TikkeulMountainApp;

import TikkeulMountainApp.fund.FundService;
import TikkeulMountainApp.fund.Scheduler;
import TikkeulMountainApp.util.MySqlConnect;
import java.io.IOException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, InterruptedException, IOException {

        //자동이체, hour과 minute 수정하면됨
        Scheduler scheduler = new Scheduler(); 
        scheduler.execute(()-> {
            try {
                FundService.depositAtOnce();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        },16,26,0);

        int page = 1;
        while(true){
            if(page==0){ //종료
                System.out.println("시스템을 종료합니다.");
                break;
            }else if(page==1){ //로그인 페이지
                page = PrintPage.logInPage();
            }else if(page==2){ //메인 페이지(모임리스트)
                page = PrintPage.mainPage();
            }else if(page==3){ //마이 페이지
                page = PrintPage.myPage();
            }else if(page==4){ //계좌페이지
                page = PrintPage.accountPage();
            }else if(page==5){ //계좌정보페이지
                page = PrintPage.accountInfoPage();
            }else if(page==6){ //모임통장 생성 페이지
                page = PrintPage.createAccountPage();
            }else if(page==7){ //수동 입금 페이지
                page = PrintPage.depositPage();
            }else if(page==8){ //출금 페이지
                page = PrintPage.withdrawPage();
            }else if(page==9){ //회원가입 페이
                page = PrintPage.signUpPage();
            }
        }
    }
}