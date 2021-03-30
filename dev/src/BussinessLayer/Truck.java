package BussinessLayer;

import java.util.Objects;

//bar
public class Truck {

    private  int licenseNumber;
    private String model;
    private int netWeight;
    private int maxWeight;

    public  Truck(int licenseNumber, int maxWeight, int netWeight, String model){
        this.licenseNumber=licenseNumber;
        this.maxWeight=maxWeight;
        this.model=model;
        this.netWeight=netWeight;
    }

    public int getLicenseNumber() { return licenseNumber; }

    public int getMaxWeight() { return maxWeight; }

    public int getNetWeight() { return netWeight; }

    public String getModel() { return model; }

    public void setLicenseNumber(int licenseNumber) { this.licenseNumber = licenseNumber; }

    public void setMaxWeight(int maxWeight) { this.maxWeight = maxWeight; }

    public void setModel(String model) { this.model = model; }

    public void setNetWeight(int netWeight) { this.netWeight = netWeight; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Truck truck = (Truck) o;
        return licenseNumber == truck.licenseNumber && netWeight == truck.netWeight && maxWeight == truck.maxWeight && Objects.equals(model, truck.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(licenseNumber, model, netWeight, maxWeight);
    }

    @Override
    public String toString() {
        return "Truck{" +
                "licenseNumber=" + licenseNumber +
                ", model='" + model + '\'' +
                ", netWeight=" + netWeight +
                ", maxWeight=" + maxWeight +
                '}';
    }
}

