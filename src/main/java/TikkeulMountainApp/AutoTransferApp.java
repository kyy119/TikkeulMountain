package TikkeulMountainApp;

import TikkeulMountainApp.fund.FundService;
import TikkeulMountainApp.fund.Scheduler;
import java.sql.SQLException;

public class AutoTransferApp {

    public static void main(String[] args) {
        //자동이체, hour과 minute 수정하면됨
        Scheduler scheduler = new Scheduler();
        scheduler.execute(()-> {
            try {
                FundService.depositAtOnce();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        },18,35,0);
    }
}
