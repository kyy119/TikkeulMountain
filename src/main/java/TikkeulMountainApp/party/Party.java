package TikkeulMountainApp.party;

import lombok.Data;

@Data
public class Party {
    private int party_id;
    private String party_name;
    private int daily_pay;
    private String party_account;
    private String party_account_password;
    private int party_account_balance;
    private String party_account_created_at;
    private String category;

    public Party(String party_name, int daily_pay, String party_account,
        String party_account_password,
        int party_account_balance, String party_account_created_at, String category) {
        this.party_name = party_name;
        this.daily_pay = daily_pay;
        this.party_account = party_account;
        this.party_account_password = party_account_password;
        this.party_account_balance = party_account_balance;
        this.party_account_created_at = party_account_created_at;
        this.category = category;
    }
}
