package Business.ApplicationFacade.Objects;

import java.util.Objects;

public class DriverServiceDTO {

    private long id;
    private int license;

    public DriverServiceDTO(long id,  int license){
        this.id=id;
        this.license=license;
    }

    @Override
    public String toString() {
        return "Id=" + id +
                "\t\tLicense=" + license;
    }

    public void setId(int id) { this.id = id; }
    public void setLicense(int license) { this.license = license; }

    public long getId() { return id; }
    public int getLicense() { return license; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DriverServiceDTO that = (DriverServiceDTO) o;
        return id == that.id &&
                license == that.license ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,  license);
    }
}
