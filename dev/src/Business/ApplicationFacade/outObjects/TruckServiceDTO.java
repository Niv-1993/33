package Business.ApplicationFacade.outObjects;

import java.util.Objects;

public class TruckServiceDTO {

    private long id;
    private final int license;
    private final String model;
    private int netWeight;
    private final int maxWeight;


    @Override
    public String toString() {
        return "Id=" + id +
                "\t\tLicense=" + license +
                "\t\tModel='" + model +
                "\t\tNetWeight=" + netWeight +
                "\t\tMaxWeight=" + maxWeight;
    }

    public TruckServiceDTO(long id, int license, int maxWeight, int netWeight, String model){
        this.license=license;
        this.maxWeight=maxWeight;
        this.model=model;
        this.netWeight=netWeight;
        this.id=id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TruckServiceDTO that = (TruckServiceDTO) o;
        return id == that.id &&
                license == that.license &&
                netWeight == that.netWeight &&
                maxWeight == that.maxWeight &&
                Objects.equals(model, that.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, license, model, netWeight, maxWeight);
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public int getLicense() { return license; }

}
