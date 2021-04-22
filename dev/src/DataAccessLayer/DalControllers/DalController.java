package DataAccessLayer.DalControllers;

import java.util.List;

public abstract class DalController <T> {

    //protected final String connectionString;
    protected final String DatabaseName = "SuperLiDB";
    protected final String tableName;

    public DalController(String tableName){ this.tableName = tableName; }

    public void deleteData(){}

    public void createTables(){}

    private void createSupplierCardsTable(){}

    private void createItemsTable(){}

    private void createOrdersTable(){}

    private void createSupplierAgreementsTable(){}

    private void createQuantityDocumentTable(){}

    public abstract boolean insert(T dalObject);

    public abstract boolean delete(T dalObject);

    public abstract T reader(T dalObject);

    public abstract List<T> loadData(T dalObject);

    public abstract boolean update(T dalObject);  // have few update, different fields.

    public abstract List<T> select(T dalObject);  // have few select, different fields.


}
