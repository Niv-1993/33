package Business.Transportation;

import java.util.Objects;


public class Driver {

    private final int id;
    private int License;

    public Driver(int id, int license){
        this.id=id;
        this.License =license;
    }
    public int getId() { return id; }

    public int getLicense() { return License; }

    public void setLicense(int license) { License = license; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Driver driver = (Driver) o;
        return id == driver.id && License == driver.License;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, License);
    }

    @Override
    public String toString() {
        return "Driver: " +
                "id=" + id + '\n' +
                ", LicenseWeight=" + License + '\n';
    }
}
