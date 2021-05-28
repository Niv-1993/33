package Business.Transportation;
//yuval

import Business.Type.Area;

import java.util.Objects;

public abstract class Site {

    protected String phone;
    protected String contactName;
    protected int id;
    protected Address address;
    protected Area shippingArea;


    public Site(String phone, String contactName, int id, Address address, Area shippingArea){
        this.phone = phone;
        this.contactName = contactName;
        this.id = id;
        this.address = address;
        this.shippingArea = shippingArea;
    }
    public void setId(int newId){id = newId; }
    public void setArea(Area newShippingArea){shippingArea = newShippingArea; }

    public String getPhone(){return phone;}
    public String getContactName(){return contactName;}
    public int getId(){return id;}
    public Address getAddress(){return address;}
    public Area getArea(){return shippingArea;}

    @Override
    public String toString() {
        return "Site: " +
                "phone='" + phone + '\n' +
                ", contactName='" + contactName + '\n' +
                ", id=" + id + '\n' +
                ", " + address + '\n' +
                ", " + shippingArea + '\n';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Site site = (Site) o;
        return id == site.id &&
                Objects.equals(phone, site.phone) &&
                Objects.equals(contactName, site.contactName) &&
                Objects.equals(address, site.address) &&
                Objects.equals(shippingArea, site.shippingArea);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phone, contactName, id, address, shippingArea);
    }
}
