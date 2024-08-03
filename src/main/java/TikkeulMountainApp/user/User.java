package TikkeulMountainApp.user;

import TikkeulMountainApp.util.MySqlConnect;
import lombok.Data;
import lombok.Setter;

@Data
public class User {
    String user_id;
    String user_name;
    String user_password;
    String user_phone;
    String user_account;
    int user_account_balance;
    String user_active;

    public User() {
    }

    public User(String user_id, String user_name, String user_password, String user_phone, String user_account, int user_account_balance, String user_active) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_password = user_password;
        this.user_phone = user_phone;
        this.user_account = user_account;
        this.user_account_balance = user_account_balance;
        this.user_active = user_active;


        }
        public void printInfo(){
        System.out.println("아이디: " + user_id);
        System.out.println("이름: " + user_name);
        System.out.println("비밀번호: " + user_password);
        System.out.println("전화번호: " + user_phone);
        System.out.println("계좌번호: " + user_account);
        System.out.println("계좌 잔액: " + user_account_balance);
        System.out.println("활성상태: " + user_active);

        }
    }

