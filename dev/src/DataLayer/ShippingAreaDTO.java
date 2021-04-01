package DataLayer;

import java.util.Objects;
import enums.Area;

public class ShippingAreaDTO {

    private Area area;

    public ShippingAreaDTO(Area are){ this.area=are;}

    public Area getArea() { return area; }

    public void setArea(Area area) { this.area = area; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShippingAreaDTO that = (ShippingAreaDTO) o;
        return area == that.area;
    }

    @Override
    public int hashCode() {
        return Objects.hash(area);
    }
}
