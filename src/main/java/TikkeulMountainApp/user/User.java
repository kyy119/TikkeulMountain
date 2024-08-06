package TikkeulMountainApp.user;

import TikkeulMountainApp.util.MySqlConnect;
import lombok.Data;
import lombok.Setter;

@Data
public class User {

    String userId;
    String userName;
    String userPassword;
    String userPhone;
    String userAccount;
    int userAccountBalance;
    String userActive;

    public User() {
    }

    public User(String userId, String userName, String userPassword, String userPhone,
        String userAccount, int userAccountBalance, String userActive) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userPhone = userPhone;
        this.userAccount = userAccount;
        this.userAccountBalance = userAccountBalance;
        this.userActive = userActive;


    }

    public User(String userId, String userName, String userPassword, String userPhone,
        String user_account) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userPhone = userPhone;
        this.userAccount = user_account;
    }

    public void printInfo() {
        System.out.println("아이디: " + userId);
        System.out.println("이름: " + userName);
        System.out.println("전화번호: " + userPhone);
        System.out.println("계좌번호: " + userAccount);
        System.out.println("계좌 잔액: " + userAccountBalance);

    }



}

