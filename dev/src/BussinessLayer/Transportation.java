package BussinessLayer;

import BussinessLayer.Driver;
import BussinessLayer.Item;
import BussinessLayer.Site;

import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

//yuval
public class Transportation {
    long id;
    private Date date;
    private LocalTime leavingTime;
    private Driver driver;
    //ask if they call this item or product?
    private HashMap<Site, List<Item>> deliveryItems;

    public long getId() { return id; }
    public Date getDate() { return date; }
    public Driver getDriver() { return driver; }
    public LocalTime getLeavingTime() { return leavingTime; }

    public void setDate(Date date) { this.date = date; }
    public void setDeliveryItems(HashMap<Site, List<Item>> deliveryItems) { this.deliveryItems = deliveryItems; }
    public void setDriver(Driver driver) { this.driver = driver; }
    public void setId(long id) { this.id = id; }
    public void setLeavingTime(LocalTime leavingTime) { this.leavingTime = leavingTime; }

    public List<Item> getSiteItems(Site site){
        if(!deliveryItems.containsKey(site)){
            throw new IllegalArgumentException("No items for this Site.");
        }
        return deliveryItems.get(site);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transportation that = (Transportation) o;
        return id == that.id &&
                Objects.equals(date, that.date) &&
                Objects.equals(leavingTime, that.leavingTime) &&
                Objects.equals(driver, that.driver) &&
                Objects.equals(deliveryItems, that.deliveryItems);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, date, leavingTime, driver, deliveryItems);
    }
}
