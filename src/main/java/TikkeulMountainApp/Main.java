package TikkeulMountainApp;

import TikkeulMountainApp.util.MySqlConnect;

public class Main {

    public static void main(String[] args) {
        MySqlConnect.MySqlConnct();
        System.out.println(
            MySqlConnect.url + " \nuser : " + MySqlConnect.user + "\npw : "
                + MySqlConnect.password);
    }
}