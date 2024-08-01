package TikkeulMountainApp.party;

import TikkeulMountainApp.util.MySqlConnect;
import java.util.Scanner;

public class PartyApp {
    public static void main(String[] args) {

        PartyService ps = new PartyService();
        System.out.println(
            MySqlConnect.url + " \nuser : " + MySqlConnect.user + "\npw : "
                + MySqlConnect.password);
//        Scanner sc = new Scanner(System.in);
//        System.out.print("모임 이름 : ");
//        String name = sc.next();
//        System.out.print("계좌 비밀 번호 : ");
//        String pw = sc.next();
//        System.out.print("카테고리 : ");
//        String cate = sc.next();
//        PartyService.createParty(name, pw, cate);
        PartyService.createParty("중국여행", "1211", "여행");
        ps.updatePartyBalance(3,55500);

        int balance = ps.getPartyBalance(3);
        System.out.println(balance);
    }
}