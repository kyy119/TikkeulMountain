package TikkeulMountainApp.fund;

import TikkeulMountainApp.party.PartyService;
import TikkeulMountainApp.user.UserDao;
import java.util.Scanner;

public class FundService {

    Scanner sc = new Scanner(System.in);

    public void deposit(String userId, int partyId) { //개인통장->모임통장, transferIndex: "1"
        System.out.print("모임통장에 입금할 금액을 입력하세요: ");
        int amount = Integer.parseInt(sc.nextLine());
        int newPartyBalance = PartyService.getPartyBalance(partyId) + amount;
        int newUserBalance = UserDao.getUserBalance(userId) - amount;
        UserDao.updateUserBalance(userId, newUserBalance);
        PartyService.updatePartyBalance(partyId, newPartyBalance);
        Transaction transaction = new Transaction(amount, newPartyBalance, "1", null, userId,
            partyId);
        transactionDao.addTransaction(transaction);
    }

    public void withdraw(String userId, int partyId) { //모임통장->개인통장, transferIndex: "2"
        System.out.print("출금할 금액을 입력하세요: ");
        int amount = Integer.parseInt(sc.nextLine());
        int newPartyBalance = partyDao.getPartyBalance(partyId) - amount;
        int newUserBalance = userDao.getuserBalance(userId) + amount;
        userDao.updateUserBalance(userId, newUserBalance);
        partyDao.updatePartyBalance(partyId, newPartyBalance);
        Transaction transaction = new Transaction(amount, newPartyBalance, "1", null, userId, partyId);
        transactionDao.addTransaction(transaction);
    }
}
