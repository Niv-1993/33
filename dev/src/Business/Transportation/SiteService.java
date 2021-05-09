package Business.Transportation;
import java.util.List;


public class SiteService {

    private DataControl dataControl;

public SiteService(){
    dataControl=new DataControl();
}
    public List<Supplier> getSuppliersList() throws Exception {


        return dataControl.getSuppliers();
    }

    public List<Branch> getBranchesList() throws Exception {
        return dataControl.getBranches();
    }


    public Branch getBranch(int id) throws Exception {
        return dataControl.getBranch(id);
    }

    public Supplier getSupplier(int id) throws Exception {

       return dataControl.getSupplier(id);

    }




}
