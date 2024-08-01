package TikkeulMountainApp.party;

import TikkeulMountainApp.util.MySqlConnect;

public class PartyApp {
    public static void main(String[] args) {

        PartyService ps = new PartyService();
        System.out.println(
            MySqlConnect.url + " \nuser : " + MySqlConnect.user + "\npw : "
                + MySqlConnect.password);




        PartyService.createParty();

        ps.updatePartyBalance(3,55500);

        int balance = ps.getPartyBalance(3);
        System.out.println(balance);
    }
}