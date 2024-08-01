package TikkeulMountainApp.party;


import lombok.Data;

@Data
public class MemberShip {
    //  `role` VARCHAR(5) NOT NULL,
    //  `user_id` VARCHAR(10) NOT NULL,
    //  `party_id` INT NOT NULL,
    //  `user_active` CHAR(1) NULL,
    //  `party_active` CHAR(1) NULL,
    private int membershipId;
    private String role;
    private String userId;
    private int partyId;
    private String userActive;
    private String partyActive;

    public MemberShip() {
    }

    public MemberShip(String role, String userId, int partyId, String userActive,
        String partyActive) {
        this.role = role;
        this.userId = userId;
        this.partyId = partyId;
        this.userActive = userActive;
        this.partyActive = partyActive;
    }
}
