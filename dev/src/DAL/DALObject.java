package DAL;

public abstract class DALObject {
    public abstract String getCreate();
    public abstract String getSelect();
    public abstract String getDelete();
    public abstract String getUpdate();
    public abstract String getInsert();
}
