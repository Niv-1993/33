package Database;

public class Database {
    private static Database database = null;
    //---------------------------------fields---------------------------
    private static int counterSID;
    private static int counterCID;

    //--------------------------------constructor-----------------------
    private Database(){
        counterSID=0;
        counterCID=0;
    }

    public static Database getInstance()
    {
        if (database == null)
            database = new Database();

        return database;
    }
    //-------------------------------methods----------------------------
    public int addShift(){
        //add to DB and return SID
        counterSID++;
        return counterSID;
    }

    public int addConstraint(){
        //add to DB and return CID
        counterCID++;
        return counterCID;
    }



}
