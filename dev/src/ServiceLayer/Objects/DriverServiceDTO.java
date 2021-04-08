package ServiceLayer.Objects;

import java.util.Objects;

public class DriverServiceDTO {

    private long id;
    private String name;
    private int license;

    public DriverServiceDTO(long id, String name, int license){
        this.id=id;
        this.name=name;
        this.license=license;
    }

    @Override
    public String toString() {
        return "Driver {\tid=" + id +
                "\tname='" + name  +
                "\tlicense=" + license +
                "\t}\n";
    }

    public void setName(String name) { this.name = name; }
    public void setId(int id) { this.id = id; }
    public void setLicense(int license) { this.license = license; }

    public String getName() { return name; }
    public long getId() { return id; }
    public int getLicense() { return license; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DriverServiceDTO that = (DriverServiceDTO) o;
        return id == that.id &&
                license == that.license &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, license);
    }
}
