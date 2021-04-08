package ServiceLayer.Objects;

public class TruckServiceDTO {

    private long id;
    private final int license;
    private final String model;
    private int netWeight;
    private final int maxWeight;


    @Override
    public String toString() {
        return "Truck {\tid=" + id +
                "\tlicense=" + license +
                "\tmodel='" + model +
                "\tnetWeight=" + netWeight +
                "\tmaxWeight=" + maxWeight +
                "\t}\n";
    }

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

}
