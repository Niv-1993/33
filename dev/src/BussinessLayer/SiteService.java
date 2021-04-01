package BussinessLayer;

import BussinessLayer.ShippingArea;

import java.util.List;

//yuval
public class SiteService {
    private List<Site> sites;
    private List<Supplier> suppliers;
    private List<Branch> branches;

    public List<Supplier> getSuppliers() {
        return suppliers;
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public void setSites(List<Site> sites) {
        this.sites = sites;
    }
    public List<Site> getSites() {
        return sites;
    }
}
