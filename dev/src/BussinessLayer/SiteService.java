package BussinessLayer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SiteService {
    private List<Site> sites;
    private HashMap<Integer, Supplier> suppliers;
    private HashMap<Integer, Branch> branches;

    public List<Supplier> getSuppliersList() {
        return new ArrayList<>(suppliers.values());
    }

    public List<Branch> getBranchesList() {
        return new ArrayList<>(branches.values());
    }
    public Branch getBranch(int id){
        if(branches.containsKey(id)){
            return branches.get(id);
        }
        throw new IllegalArgumentException("branch with id: " + id + "does not exists");
    }

    public Supplier getSupplier(int id){
        if(suppliers.containsKey(id)){
            return suppliers.get(id);
        }
        throw new IllegalArgumentException("supplier with id: " + id +"does not exist");
    }
    public HashMap<Integer, Supplier> getSuppliers(){return suppliers;}


    /**
     * load all the sites from the database using the control
     * @param dataControl : the control that ask the database for the data.
     */
    public void loadData(DataControl dataControl) {

        suppliers=dataControl.getSuppliers();
        branches=dataControl.getBranches();
    }
}
