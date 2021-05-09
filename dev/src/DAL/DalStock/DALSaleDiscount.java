package DAL.DalStock;

import DAL.DalController;
import Utility.Tuple;

import java.util.Date;
import java.util.List;

public class DALSaleDiscount extends DALDiscount{
    int categoryID;

    public DALSaleDiscount(){
        super(null);
    }

    public DALSaleDiscount(Integer storeID, Integer id, Integer typeID, Integer categoryID,
                           float percent, Date startDate, Date endDate, DalController dc){
        super(dc,storeID,id,typeID,percent,startDate,endDate);
        this.categoryID=categoryID;
    }// get supplier id from controller

    @Override
    public String getCreate() {
        return super.getCreate();
    }

    public void removeCategory(int i) {
        String query= """
                UPDATE ? \s
                SET categoryID=0
                WHERE storeID=? AND discountID=? AND categoryID=?;
                """;
        List<Tuple<Object,Class>> params=prepareList(tableName,storeID,_discountID,categoryID);
        try {
            DC.noSelect(query,params);
        }
        catch (Exception e){
            throw new IllegalArgumentException("fail");
        }
        categoryID=0;
    }

    public void addCategory(int i) {
        String query= """
                UPDATE ? \s
                SET categoryID=?
                WHERE storeID=? AND discountID=?;
                """;
        List<Tuple<Object,Class>> params=prepareList(tableName,i,storeID,_discountID);
        try {
            DC.noSelect(query,params);
        }
        catch (Exception e){
            throw new IllegalArgumentException("fail");
        }
        categoryID=i;
    }


}
