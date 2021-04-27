package DAL;

import Utility.Tuple;
import org.apache.log4j.Logger;


import java.io.File;
import java.sql.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DalController {
    final static Logger log=Logger.getLogger(DalController.class);
    String dbname;

    public DalController(String dbname) {
        this.dbname = dbname;
    }

    private Connection connect() throws Exception {
        //DriverManager.registerDriver(new com.sqlite.jdbc.Driver());
        Class.forName("org.sqlite.JDBC");
        String url = "jdbc:sqlite:C:\\\\Users\\user\\Desktop\\assignments\\ADSS\\Group_B\\dev\\src\\" + dbname;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            log.warn("failed to make SQL connection");
           throw new Exception("cant connect");
        }
        return conn;
    }

    // String String === Val Type
    int noSelect(String query, List<Tuple<String,String>> params) throws Exception {
        List<String> doQuary= Arrays.asList(query.split(";"));
        if(doQuary.size()>1 && params!=null) throw new Exception("non create multi-query");
        boolean isDefault = false;
        int ret=0;
        for(int j=0;j<doQuary.size();j++) {
            query=doQuary.get(j);
            try (Connection conn = this.connect();
                 PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                if (params != null) {
                    for (int i = 1; i <= params.size() && !isDefault; i++) {
                        Tuple<String, String> tuple = params.get(i);
                        switch (tuple.item2) {
                            case "int":
                                preparedStatement.setInt(i, Integer.parseInt(tuple.item1));
                            case "String":
                                preparedStatement.setString(i, tuple.item1);
                            case "double":
                                preparedStatement.setDouble(i, Double.parseDouble(tuple.item1));
                            default:
                                isDefault = true;
                        }
                    }
                    if (isDefault) throw new Exception("illegal type");
                }
                ret += preparedStatement.executeUpdate();
            } catch (SQLException e) {
                log.warn("noSelect quary: " + query + "\nfailed due to " + e.getMessage());
                throw e;
            }
        }
        return ret;
    }

    Tuple<List<Class>,List<Object>> Select(String query, List<Integer> params) throws Exception {
        Connection conn = this.connect();
        try {
            PreparedStatement preparedStatement  = conn.prepareStatement(query);
            for(int i = 0 ; i < params.size() ; i++){
                preparedStatement.setInt(i+1 , params.get(i));
            }
            ResultSet rs = preparedStatement.executeQuery();
            return fromRS(rs);
        } catch (SQLException e) {
            throw new Exception("select field");
        }
        finally{
            conn.close();
        }
    }

    private Tuple<List<Class>,List<Object>> fromRS(ResultSet rs){
        List<Class> types=new ArrayList<>();
        List<Object> vals=new ArrayList<>();
        if (rs != null ) {
            try {
                rs.next();
                ResultSetMetaData rsmd = rs.getMetaData();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    int type = rsmd.getColumnType(i);
                    if (type == Types.VARCHAR || type == Types.CHAR) {
                        types.add(String.class);
                        vals.add(rs.getString(i));
                    } else if (type == Types.FLOAT || type==Types.DOUBLE || type == Types.REAL) {
                        types.add(Double.class);
                        vals.add(rs.getDouble(i));
                    } else {
                        types.add(Integer.class);
                        vals.add(rs.getInt(i));
                    }
                }
                if (rs.next()) return null;
            }
            catch (Exception e){
                log.warn(e.getMessage());
                return null;
            }
        } else return null;

        types.add(DalController.class);
        vals.add(this);

        return new Tuple<>(types,vals);
    }

}
