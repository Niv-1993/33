import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class main {
    final static Logger log = Logger.getLogger(main.class);
    public static void main(String[] args) {
        HashMap<Integer, ArrayList<String>> h = new HashMap<>();
        ArrayList<String> s = new ArrayList<>();
        s.add("dor");
        h.put(5,null);
        h.put(6,s);
        System.out.println(h.containsValue(5));
    }
}
