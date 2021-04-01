package DataLayer;

import DataLayer.LicenseDTO;

import java.util.Objects;

public class DriverDTO {
    private int id;
    private String name;
    private LicenseDTO license;

    public DriverDTO(int id, String name, LicenseDTO license){
        this.id=id;
        this.name=name;
        this.license =license;
    }

    public void setName(String name) { this.name = name; }
    public void setId(int id) { this.id = id; }
    public void setLicense(LicenseDTO license) { this.license = license; }

    public int getId() { return id; }
    public String getName() { return name; }
    public LicenseDTO getLicense() { return license; }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DriverDTO driverDTO = (DriverDTO) o;
        return id == driverDTO.id && Objects.equals(name, driverDTO.name) && Objects.equals(license, driverDTO.license);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, license);
    }
}
