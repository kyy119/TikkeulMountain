package TikkeulMountainApp.party;

import lombok.Data;

@Data
public class Party {
    private String userId;
    private int partyId;
    private String partyName;
    private int dailyPay;
    private String partyAccount;
    private String partyAccountPassword;
    private int partyAccountBalance;
    private String partyAccountCreatedAt;
    private String category;
    private String partyActive;

    public Party(String partyName, int dailyPay, String partyAccount,
        String partyAccountPassword,
        int partyAccountBalance, String partyAccountCreatedAt, String category, String partyActive) {
        this.partyName = partyName;
        this.dailyPay = dailyPay;
        this.partyAccount = partyAccount;
        this.partyAccountPassword = partyAccountPassword;
        this.partyAccountBalance = partyAccountBalance;
        this.partyAccountCreatedAt = partyAccountCreatedAt;
        this.category = category;
        this.partyActive = partyActive;
    }

    public Party() {
    }
    public Party(String userId, String partyName, String category, String partyAccount, String partyAccountCreatedAt) {
        this.userId = userId;
        this.partyName = partyName;
        this.category = category;
        this.partyAccount = partyAccount;
        this.partyAccountCreatedAt = partyAccountCreatedAt;
    }
}
