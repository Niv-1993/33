package BussinessLayer;

import BussinessLayer.ShippingArea;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//yuval
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
    public void setSites(List<Site> sites) {
        this.sites = sites;
    }
    public List<Site> getSites() {
        return sites;
    }


    public void loadData(DataControl dataControl) {

        suppliers=dataControl.getSuppliers();
        branches=dataControl.getBranches();
    }
}
