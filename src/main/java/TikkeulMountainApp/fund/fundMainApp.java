package TikkeulMountainApp.fund;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class fundMainApp {

    public static void main(String[] args) throws SQLException {

        //FundService.withdraw("pk999",1);

//        FundService.depositAtOnce();


        Scheduler scheduler = new Scheduler();
        scheduler.execute(()-> {
            try {
                FundService.depositAtOnce();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        },14,22,0);

        Map<String,Integer> map = TransactionDao.getMemberContributions(2);
        Set<String> keySet = map.keySet();
        Iterator<String> keyIterator = keySet.iterator();
        while(keyIterator.hasNext()){
            String k = keyIterator.next();
            Integer v = map.get(k);
            System.out.println(k+":"+v);
        }


    }
}
