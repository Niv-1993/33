package BussinessLayer;

import java.util.Objects;

//bar

public class Driver {

    private int id;
    private String name;
    private License LicenseWeight;

    public Driver(int id, String name, License license){
        this.id=id;
        this.name=name;
        this.LicenseWeight=license;
    }
    public int getId() { return id; }

    public License getLicenseWeight() { return LicenseWeight; }

    public String getName() { return name; }

    //TODO: check if license is competible by equaling.
    public boolean compatibleLicense(License license){ return false; }

    public void setLicenseWeight(License licenseWeight) { LicenseWeight = licenseWeight; }

    public void setName(String name) { this.name = name; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Driver driver = (Driver) o;
        return id == driver.id && LicenseWeight == driver.LicenseWeight && name.equals(driver.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, LicenseWeight);
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", LicenseWeight=" + LicenseWeight +
                '}';
    }
}
