package TikkeulMountainApp.fund;

import java.sql.Time;
import java.sql.Timestamp;
import lombok.Data;

@Data
public class Transaction {

    Timestamp transferDate;
    int transferAmount;
    int transferBalance;
    String transferIndex; //입금,출금 구분자'1': 개인통장->모임통장'2': 모임통장->개인통장
    String transferMemo;
    String userId;
    int partyId;

    public Transaction() {
    }

    public Transaction(int transferAmount, int transferBalance, String transferIndex,
        String transferMemo, String userId, int partyId) {
        this.transferDate = new Timestamp(System.currentTimeMillis());
        this.transferAmount = transferAmount;
        this.transferBalance = transferBalance;
        this.transferIndex = transferIndex;
        this.transferMemo = transferMemo;
        this.userId = userId;
        this.partyId = partyId;
    }

    public void printTransaction() {
        if (transferIndex.equals("1")) {
            System.out.println(
                "거래시각: " + transferDate + " | 거래금액:" + transferAmount + " | 거래방식: 입금"
                    + " | 보내는 사람: " + userId + " | 잔액:" + transferBalance);
        } else if (transferIndex.equals("2")) {
            System.out.println(
                "거래시각: " + transferDate + " | 거래금액:" + transferAmount + " | 거래방식: 출금"
                    + " | 받는 사람:" + userId + " | 잔액:" + transferBalance + " | 출금메모: " + transferMemo);
        } else {
            System.out.println("거래방식을 명확히 하세요.");
        }
    }
}