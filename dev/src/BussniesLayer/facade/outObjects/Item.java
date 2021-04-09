package BussniesLayer.facade.outObjects;

public class Item {
    private final int ItemId;
    private final String category;
    private final String name;
    private final double price;

    public Item(BussniesLayer.Item item) {
        ItemId = item.getItemId();
        category = item.getCategory();
        name = item.getName();
        price = item.getPrice();
    }

    public String toString(boolean shift) {
        if(shift)
            return "\tItem: \n" +
                "\t\tItemId: " + ItemId + "\n" +
                "\t\tcategory: " + category + '\n' +
                "\t\tname: " + name +"\n" +
                "\t\tprice: " + price;
        return "Item: \n" +
                "\tItemId: " + ItemId + "\n" +
                "\tname: " + name + "\n" +
                "\tcategory: " + category + '\n' +
                "\tprice: " + price;
    }

    public String toStringId(){
        return "" +  ItemId;
    }
}
