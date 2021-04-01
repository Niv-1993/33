package BussinessLayer;

import java.util.Objects;

//yuval
public class License {
    final private int kg;
    public License(int kg){
        this.kg = kg;
    }

    public int getKg() { return kg; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        License license = (License) o;
        return kg == license.kg;
    }

    @Override
    public int hashCode() {
        return Objects.hash(kg);
    }

    @Override
    public String toString() {
        return "License{" +
                "kg=" + kg +
                '}';
    }
}
