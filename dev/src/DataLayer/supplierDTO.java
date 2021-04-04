package DataLayer;

import java.util.Objects;

public class supplierDTO {
    protected String phone;
    protected String contactName;
    protected int id;
    protected AddressDTO address;
    protected ShippingAreaDTO shippingArea;

    public supplierDTO(String phone, String contactName, int id, AddressDTO address, ShippingAreaDTO shippingArea){
        this.phone = phone;
        this.contactName = contactName;
        this.id = id;
        this.address = address;
        this.shippingArea = shippingArea;
    }
    public void setPhone(String newPhone){ phone = newPhone; }
    public void setContactName(String newContactName){ contactName = newContactName; }
    public void setId(int newId){id = newId; }
    public void setAddress(AddressDTO newAddress){ address = newAddress; }
    public void setShippingArea(ShippingAreaDTO newShippingArea){shippingArea = newShippingArea; }

    public String getPhone(){return phone;}
    public String getContactName(){return contactName;}
    public int getId(){return id;}
    public AddressDTO getAddress(){return address;}
    public ShippingAreaDTO getShippingArea(){return shippingArea;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        supplierDTO branchDTO = (supplierDTO) o;
        return id == branchDTO.id && Objects.equals(phone, branchDTO.phone) && Objects.equals(contactName, branchDTO.contactName) && Objects.equals(address, branchDTO.address) && Objects.equals(shippingArea, branchDTO.shippingArea);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phone, contactName, id, address, shippingArea);
    }
}
