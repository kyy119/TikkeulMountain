package TikkeulMountainApp.party;

import TikkeulMountainApp.util.MySqlConnect;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class PartyApp {
    public static void main(String[] args) throws SQLException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("-----");
        ArrayList<String> arr = PartyService.showCategory();
        for(int i = 0; i < arr.size(); i ++){
            System.out.print(i+1+". ");
            System.out.println(arr.get(i));
        }
        System.out.print("카테고리 선택 : ");
        int cate = Integer.parseInt(br.readLine());
        System.out.print("모임 이름 : ");
        String name = br.readLine();
        System.out.print("dailypay : ");
        String dailypay = br.readLine();
        System.out.print("계좌 비밀 번호 : ");
        String pw = br.readLine();

        PartyService.createParty(arr.get(cate-1), name, Integer.parseInt(dailypay), pw);
//        PartyService.createParty("여행","중국여행", 400, "1211");
 //       ps.updatePartyBalance(3,55500);

   //     int balance = ps.getPartyBalance(3);
     //   System.out.println(balance);
    }
}