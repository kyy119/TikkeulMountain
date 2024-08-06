package TikkeulMountainApp.fund;

import TikkeulMountainApp.party.MemberShip;
import TikkeulMountainApp.party.PartyChecker;
import TikkeulMountainApp.party.PartyService;
import TikkeulMountainApp.user.LoginChecker;
import TikkeulMountainApp.user.UserDao;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class FundService {

    private static Scanner sc = new Scanner(System.in);

    public static int deposit(String userId, int partyId) { //개인통장->모임통장, transferIndex: "1"

        int userBalance = UserDao.getUserBalance(userId);
        int partyBalance = PartyService.getPartyBalance(partyId);
        int amount;
        while (true) {
            System.out.println("입금 취소하시려면 0을 입력하세요.");
            System.out.print("모임통장에 입금할 금액을 입력하세요: ");
            try {
                amount = Integer.parseInt(sc.nextLine());
                if (userBalance - amount < 0) { //개인계좌잔액이 입금할 금액보다 적으면 거래 x
                    System.out.println("개인계좌의 잔액이 부족합니다.");
                } else if (amount < 0) {
                    System.out.println("양수를 입력해주세요.");
                } else if (amount == 0) {
                    return partyBalance;
                } else {
                    break;
                }
            } catch (NumberFormatException e){
                System.out.println("숫자로 입력바랍니다.");
            }
        }
        int newPartyBalance = partyBalance + amount;
        int newUserBalance = userBalance - amount;
        UserDao.updateUserBalance(userId, newUserBalance);
        PartyService.updatePartyBalance(partyId, newPartyBalance);
        Transaction transaction = new Transaction(amount, newPartyBalance, "1", null, userId,
            partyId);
        TransactionDao.addTransaction(transaction);

        return newPartyBalance;

    }

    public static int withdraw(String userId, int partyId) throws SQLException { //모임통장->개인통장, transferIndex: "2"

        int userBalance = UserDao.getUserBalance(userId);
        int partyBalance = PartyService.getPartyBalance(partyId);
        int amount;
        while (true) {
            System.out.println("출금 취소하시려면 0을 입력하세요.");
            System.out.print("모임통장에서 출금할 금액을 입력하세요: ");
            try {
                amount = Integer.parseInt(sc.nextLine());
                if (amount > 2000000) {
                    System.out.println("출금 최대 금액은 200만원입니다.");
                } else if (partyBalance - amount < 0) {
                    System.out.println("모임통장의 잔액이 부족합니다.");
                } else if (amount < 0) {
                    System.out.println("양수를 입력해주세요.");
                } else if (amount == 0) {
                    return partyBalance;
                } else {
                    break;
                }
            } catch (NumberFormatException e){
                System.out.println("숫자로 입력 바랍니다.");
            }

        }
        System.out.print("출금 메모(50자 제한): ");
        String memo = null;
        while(true) {
            memo = sc.nextLine();
            if(memo.length()<=50) {
                break;
            }else{
                System.out.println("메모는 50자 제한입니다.");
            }
        }

        while(true){
            System.out.print("모임 계좌 비밀번호를 입력하세요:");
            String pwd = sc.nextLine();
            if (pwd.equals(PartyService.getParty(partyId).getPartyAccountPassword())){
                int newPartyBalance = partyBalance - amount;
                int newUserBalance = userBalance + amount;
                UserDao.updateUserBalance(userId, newUserBalance);
                PartyService.updatePartyBalance(partyId, newPartyBalance);
                Transaction transaction = new Transaction(amount, newPartyBalance, "2", memo, userId,
                    partyId);
                TransactionDao.addTransaction(transaction);

                return newPartyBalance;
            }
            System.out.println("잘못된 비밀번호 입니다.");
        }


    }

    public static void deposit(String userId, int partyId, int amount) { //자동이체용 메소드

        int userBalance = UserDao.getUserBalance(userId);
        if (userBalance - amount < 0) { //개인계좌잔액이 입금할 금액보다 적으면 거래 x
            System.out.println(userId+": 개인계좌의 잔액이 부족합니다.");
        } else if (amount < 0) {
            System.out.println("음수 입니다.");
        } else {
            int newPartyBalance = PartyService.getPartyBalance(partyId) + amount;
            int newUserBalance = userBalance - amount;
            UserDao.updateUserBalance(userId, newUserBalance);
            PartyService.updatePartyBalance(partyId, newPartyBalance);
            Transaction transaction = new Transaction(amount, newPartyBalance, "1", null, userId,
                partyId);
            System.out.println(userId+": "+amount+"원 자동이체 되었습니다.");
            TransactionDao.addTransaction(transaction);
        }

    }

    //user_active=1 인 모든 멤버의 party_active=1인 모든 모임에게 자동이체용 메소드
    public static void depositAtOnce() throws SQLException {
        List<MemberShip> memberShipList = PartyService.getMemberList();
        memberShipList.forEach(n -> deposit(n.getUserId(), n.getPartyId(), n.getDailyPay()));

    }

}
