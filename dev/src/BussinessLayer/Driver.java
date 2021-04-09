package BussinessLayer;

import java.util.Objects;


public class Driver {

    private long id;
    private String name;
    private License License;

    public Driver(long id, String name, License license){
        this.id=id;
        this.name=name;
        this.License =license;
    }
    public long getId() { return id; }

    public License getLicense() { return License; }

    public String getName() { return name; }

    public boolean compatibleLicense(License license){ return this.License.getKg()>=license.getKg(); }

    public void setLicense(License license) { License = license; }

    public void setName(String name) { this.name = name; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Driver driver = (Driver) o;
        return id == driver.id && License == driver.License && name.equals(driver.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, License);
    }

    @Override
    public String toString() {
        return "Driver: " +
                "id=" + id + '\n' +
                ", name='" + name + '\n' +
                ", LicenseWeight=" + License + '\n';
    }
}
