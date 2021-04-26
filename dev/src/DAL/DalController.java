package DAL;

import Utility.Tuple;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class DalController {


    public DalController(String dbname) {
    }

    // String String === Val Type
    int noSelect(String query, List<Tuple<String,String>> params){
        throw new NotImplementedException();
    }

    ///////////
    ResultSet Select(String query, List<Integer> params){
        throw new NotImplementedException();
    }
}
