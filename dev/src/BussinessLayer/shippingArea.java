package BussinessLayer;

import java.util.Objects;

public class shippingArea {

    private  Area area;

    public shippingArea(){ }
    public shippingArea(Area are){ this.area=are;}

    public Area getArea() { return area; }

    public void setArea(Area area) { this.area = area; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        shippingArea that = (shippingArea) o;
        return area == that.area;
    }

    @Override
    public int hashCode() {
        return Objects.hash(area);
    }
}
