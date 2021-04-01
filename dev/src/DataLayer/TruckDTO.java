package DataLayer;


import java.util.Objects;

public class TruckDTO {

    private long id;
    private LicenseDTO license;
    private String model;
    private int netWeight;
    private int maxWeight;

    public  TruckDTO(long id,LicenseDTO license, int maxWeight, int netWeight, String model){
        this.license=license;
        this.maxWeight=maxWeight;
        this.model=model;
        this.netWeight=netWeight;
        this.id=id;
    }

    public long getId() { return id; }
    public int getMaxWeight() { return maxWeight;}
    public int getNetWeight() { return netWeight;}
    public String getModel() { return model;}
    public LicenseDTO getLicense() { return license;}

    public void setId(long id) { this.id = id; }

    public void setLicense(LicenseDTO license) { this.license = license; }
    public void setMaxWeight(int maxWeight) { this.maxWeight = maxWeight; }
    public void setModel(String model) { this.model = model; }
    public void setNetWeight(int netWeight) { this.netWeight = netWeight; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TruckDTO truckDTO = (TruckDTO) o;
        return netWeight == truckDTO.netWeight && maxWeight == truckDTO.maxWeight && Objects.equals(license, truckDTO.license) && Objects.equals(model, truckDTO.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(license, model, netWeight, maxWeight);
    }
}
