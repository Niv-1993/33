package Bussiness;
//yuval

import BussinessLayer.Address;
import BussinessLayer.ShippingArea;

public abstract class Site {
    protected String phone;
    protected String contactName;
    protected long id;
    protected Address address;
    protected ShippingArea shppingArea;
    public Site(String phone, String contactName, long id, Address address, ShippingArea shippingArea){
        this.phone = phone;
        this.contactName = contactName;
        this.id = id;
        this.address = address;
        this.shppingArea = shippingArea;
    }
    public void updatePhone(String newPhone){ phone = newPhone; }
    public void updateContactName(String newContancName){ contactName = newContancName; }
    public void updateId(long newId){id = newId; }
    public void updateAddress(Address newAddress){ address = newAddress; }
    public void updateShippingArea(ShippingArea newShippingArea){shppingArea = newShippingArea; }
}
