package ServiceLayer.Objects;

import BussinessLayer.License;

public class TruckServiceDTO {

    private long id;
    private int license;
    private String model;
    private int netWeight;
    private int maxWeight;

    public TruckServiceDTO(long id, int license, int maxWeight, int netWeight, String model){
        this.license=license;
        this.maxWeight=maxWeight;
        this.model=model;
        this.netWeight=netWeight;
        this.id=id;
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public int getLicense() { return license; }

    public int getMaxWeight() { return maxWeight; }

    public int getNetWeight() { return netWeight; }

    public String getModel() { return model; }

    public void setLicenseNumber(int licenseNumber) { this.license = licenseNumber; }

    public void setMaxWeight(int maxWeight) { this.maxWeight = maxWeight; }

    public void setModel(String model) { this.model = model; }

    public void setNetWeight(int netWeight) { this.netWeight = netWeight; }
}
