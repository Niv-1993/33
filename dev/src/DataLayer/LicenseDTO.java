package DataLayer;

import java.util.Objects;

public class LicenseDTO {

    final private int kg;


    public LicenseDTO(int kg){
        this.kg = kg;
    }

    public int getKg() { return kg;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LicenseDTO that = (LicenseDTO) o;
        return kg == that.kg;
    }

    @Override
    public int hashCode() {
        return Objects.hash(kg);
    }
}
