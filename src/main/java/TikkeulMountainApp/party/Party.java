package TikkeulMountainApp.party;

import lombok.Data;

@Data
public class Party {

    private String party_name;
    private int daily_pay;
    private String party_account;
    private String party_account_password;
    private int party_account_balance;
    private String party_account_created_at;
    private String category;

}
