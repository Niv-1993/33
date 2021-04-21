package DataAccessLayer.DalControllers;

public class DalController <T> {

    //protected final String connectionString;
    protected final String DatabaseName = "SuperLiDB";
    protected final String tableName;

    public DalController(String tableName){
        this.tableName = tableName;
    }
}
