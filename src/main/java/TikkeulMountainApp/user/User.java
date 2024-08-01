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


    public User(String user_id, String user_name, String user_password, String user_phone, String user_account, int user_account_balance) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_password = user_password;
        this.user_phone = user_phone;
        this.user_account = user_account;
        this.user_account_balance = user_account_balance;
    }
}
