package BussinessLayer;

import BussinessLayer.ShippingArea;

import java.util.List;

//yuval
public class SiteService {
    private List<Site> sites;

    public void setSites(List<Site> sites) {
        this.sites = sites;
    }
    public List<Site> getSites() {
        return sites;
    }
}
