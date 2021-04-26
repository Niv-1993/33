package DAL;

import Utility.Tuple;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;

import java.util.List;

public class DalController {
    String dbname;

    public DalController(String dbname) {
        this.dbname = dbname;
    }

    private Connection connect() throws Exception {
        String url = "jdbc:sqlite:C://sqlite/db/" + dbname;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
           throw new Exception("cant connect");
        }
        return conn;
    }

    // String String === Val Type
    int noSelect(String query, List<Tuple<String,String>> params) throws Exception {
        boolean isDefault = false;
        try (Connection conn = this.connect();
             PreparedStatement preparedStatement  = conn.prepareStatement(query)){
            for(int i = 1 ; i <= params.size() && !isDefault ; i++){
                Tuple<String , String> tuple = params.get(i);
                switch (tuple.item2){
                    case "int" : preparedStatement.setInt(i , Integer.parseInt(tuple.item1));
                    case "String" : preparedStatement.setString(i , tuple.item1);
                    case "Double" : preparedStatement.setDouble(i , Double.parseDouble(tuple.item1));
                    case "boolean" :   preparedStatement.setBoolean(i , Boolean.parseBoolean(tuple.item1));
                    default : isDefault = true;
                }
            }
            if(isDefault) throw new Exception("illegal type");
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.getMetaData().getColumnCount() == 0) return 0; // can throw nothing changed.
            else return 1;
        } catch (SQLException e) {
            throw new Exception("select field");
        }
    }

    ResultSet Select(String query, List<Integer> params) throws Exception {
        try (Connection conn = this.connect();
             PreparedStatement preparedStatement  = conn.prepareStatement(query)){
            for(int i = 1 ; i <= params.size() ; i++){
                preparedStatement.setInt(i , params.get(i));
            }
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new Exception("select field");
        }
    }

}
