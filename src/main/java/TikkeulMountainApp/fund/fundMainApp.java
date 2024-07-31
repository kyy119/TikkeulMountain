package TikkeulMountainApp.fund;

public class fundMainApp {
    public static void main(String[] args) {
        TransactionDao transactionDao = new TransactionDao();
        Transaction transaction1 = new Transaction(500,2600,"2","pkaaa42","3");

        transactionDao.addTransaction(transaction1);
        transaction1.printTransaction();

    }
}
