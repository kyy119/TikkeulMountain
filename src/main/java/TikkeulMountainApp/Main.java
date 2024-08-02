package TikkeulMountainApp;

import TikkeulMountainApp.util.MySqlConnect;

public class Main {

    public static void main(String[] args) {

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
            }else if(page==1){ //수동 이체 페이지
                page = PrintPage.transactionPage();
            }
        }
    }
}