package DataLayer;

import BussinessLayer.Address;
import BussinessLayer.ShippingArea;

import java.util.Objects;

public class SiteDTO {

    protected String phone;
    protected String contactName;
    protected long id;
    protected AddressDTO address;
    protected ShippingAreaDTO shippingArea;

    public SiteDTO(String phone, String contactName, long id, AddressDTO address, ShippingAreaDTO shippingArea){
        this.phone = phone;
        this.contactName = contactName;
        this.id = id;
        this.address = address;
        this.shippingArea = shippingArea;
    }
    public void setPhone(String newPhone){ phone = newPhone; }
    public void setContactName(String newContactName){ contactName = newContactName; }
    public void setId(long newId){id = newId; }
    public void setAddress(AddressDTO newAddress){ address = newAddress; }
    public void setShippingArea(ShippingAreaDTO newShippingArea){shippingArea = newShippingArea; }

    public String getPhone(){return phone;}
    public String getContactName(){return contactName;}
    public long getId(){return id;}
    public AddressDTO getAddress(){return address;}
    public ShippingAreaDTO getShippingArea(){return shippingArea;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SiteDTO siteDTO = (SiteDTO) o;
        return id == siteDTO.id && Objects.equals(phone, siteDTO.phone) && Objects.equals(contactName, siteDTO.contactName) && Objects.equals(address, siteDTO.address) && Objects.equals(shippingArea, siteDTO.shippingArea);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phone, contactName, id, address, shippingArea);
    }
}
