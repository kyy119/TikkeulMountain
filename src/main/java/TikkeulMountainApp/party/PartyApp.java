package TikkeulMountainApp.party;

import TikkeulMountainApp.util.MySqlConnect;

public class PartyApp {
    public static void main(String[] args) {

        System.out.println(
            MySqlConnect.url + " \nuser : " + MySqlConnect.user + "\npw : "
                + MySqlConnect.password);

        PartyService.createParty();

    }
}