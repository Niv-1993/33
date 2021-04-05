import org.apache.log4j.Logger;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class main {
    final static Logger log = Logger.getLogger(main.class);
    public static void main(String[] args) {
        LocalDate  now = LocalDate.now();
        LocalDate d= now.plusWeeks(1);
        System.out.println(d);
        }

}
