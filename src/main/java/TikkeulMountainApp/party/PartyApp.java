package TikkeulMountainApp.party;

import TikkeulMountainApp.util.MySqlConnect;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class PartyApp {
    public static void main(String[] args) throws SQLException {

        PartyService ps = new PartyService();
        System.out.println(
            MySqlConnect.url + " \nuser : " + MySqlConnect.user + "\npw : "
                + MySqlConnect.password);
//        Scanner sc = new Scanner(System.in);
//        System.out.print("카테고리 : ");
//        String cate = sc.next();
//        System.out.print("모임 이름 : ");
//        String name = sc.next();
//        System.out.print("dailypay : ");
//        String dailypay = sc.next();
//        System.out.print("계좌 비밀 번호 : ");
//        String pw = sc.next();
//
//        PartyService.createParty(cate, name, Integer.parseInt(dailypay), pw);
////        PartyService.createParty("여행","중국여행", 400, "1211");
//        ps.updatePartyBalance(3,55500);
//
//        int balance = ps.getPartyBalance(3);
//        System.out.println(balance);
        ArrayList<MemberShip> arr = PartyService.getMemberList();
        for(MemberShip memberShip : arr){
            System.out.println(memberShip.getUserId());
            System.out.println(memberShip.getPartyId());
            System.out.println(memberShip.getDailyPay());
        }
    }
}