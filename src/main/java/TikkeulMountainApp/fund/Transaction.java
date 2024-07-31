package TikkeulMountainApp.fund;

import java.sql.Time;
import java.sql.Timestamp;

public class Transaction {

    Timestamp transfer_date;
    int transfer_amount;
    int transfer_balance;
    String transfer_index; //입금,출금 구분자 '1': 개인통장->모임통장 '2': 모임통장->개인통장
    String userId;
    String groupId;

    public Transaction() {
    }

    public Transaction(int transfer_amount, int transfer_balance, String transfer_index,
        String userId,
        String groupId) {
        this.transfer_date = new Timestamp(System.currentTimeMillis());
        this.transfer_amount = transfer_amount;
        this.transfer_balance = transfer_balance;
        this.transfer_index = transfer_index;
        this.userId = userId;
        this.groupId = groupId;
    }

    public void printTransaction{
        if(transfer_index.equals("1")) {
            System.out.println("거래시각:" + transfer_date + "거래금액:" + transfer_amount + "거래방식: 1" + "보내는 사람:"+ userId +
                "받는 사람:" + groupId + "잔액:" + transfer_balance);
        } else if(transfer_index=="2") {
            System.out.println("거래시각:" + transfer_date + "거래금액:" + transfer_amount + "거래방식: 2" + "보내는 사람:"+ groupId +
                "받는 사람:" + userId + "잔액:" + transfer_balance);
        } else {
            System.out.println("거래방식 명확히 하세요.");
        }
    }
}
