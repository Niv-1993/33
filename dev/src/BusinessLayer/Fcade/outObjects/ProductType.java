package BusinessLayer.Fcade.outObjects;

import java.util.List;

public class ProductType {
    int typeID;
    int minAmount;
    int categoryID;
    String producer;
    List<Integer> supplierIDs;
    int currShelves;
    int currStorage;
    float basePrice;
    float salePrice;

    public ProductType(int typeID, int minAmount, int categoryID, String producer, List<Integer> supplierIDs, int currShelves, int currStorage, float basePrice, float salePrice) {
        this.typeID = typeID;
        this.minAmount = minAmount;
        this.categoryID = categoryID;
        this.producer = producer;
        this.supplierIDs = supplierIDs;
        this.currShelves = currShelves;
        this.currStorage = currStorage;
        this.basePrice = basePrice;
        this.salePrice = salePrice;
    }

    @Override
    public String toString() {
        return "Product type ID: "+typeID+"\nIs in category: "+categoryID+"\nThere are : "+currShelves+"items in the store and "+currStorage+
                "\nitems in storage\n"+currStorage+currShelves+"items in total\nThere should be at least: "+minAmount+
                " items\nBase price: "+basePrice+"\nSale price: "+salePrice+"\n";
    }
}
