package TikkeulMountainApp.party;


import lombok.Data;

@Data
public class MemberShip {

    private int membershipId;
    private String role;
    private String userId;
    private int partyId;
    private String userActive;
    private String partyActive;
    private int dailyPay;

    public MemberShip() {
    }

    public MemberShip(String role, String userId, int partyId, String userActive,
        String partyActive, int dailyPay) {
        this.role = role;
        this.userId = userId;
        this.partyId = partyId;
        this.userActive = userActive;
        this.partyActive = partyActive;
        this.dailyPay = dailyPay;
    }
}
