package TikkeulMountainApp.fund;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class fundMainApp {

    public static void main(String[] args) throws SQLException {

        //FundService.withdraw("pk999",1);

//        FundService.depositAtOnce();

        List<String> userList = TransactionDao.getDailyContribution(4);

        userList.forEach(n-> System.out.println(n));

    }
}
