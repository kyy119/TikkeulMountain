package TikkeulMountainApp.fund;

import java.sql.Time;
import java.sql.Timestamp;
import lombok.Data;

@Data
public class Transaction {

    Timestamp transferDate; //거래날짜
    int transferAmount; //거래금액
    int transferBalance; //거래후 잔액
    String transferIndex; //입금,출금 구분자'1': 개인통장->모임통장'2': 모임통장->개인통장
    String transferMemo; //거래 메모
    String userId; //사용자 id
    int partyId; //모임 id

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
                    + "| 보내는 사람: " + userId + " | 잔액:" + transferBalance);

        } else if (transferIndex.equals("2")) {
            System.out.println(
                "거래시각: " + transferDate + " | 거래금액:" + transferAmount + " | 거래방식: 출금"
                    + " | 받는 사람:" + userId + " | 잔액:" + transferBalance + " | 출금메모: " + transferMemo);
        } else {
            System.out.println("거래방식을 명확히 하세요.");
        }
    }
}