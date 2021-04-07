package BussniesLayer.facade.outObjects;

public class Item {
    private final int ItemId;
    private final String category;
    private final String name;

    public Item(BussniesLayer.Item item) {
        ItemId = item.getItemId();
        category = item.getCategory();
        name = item.getName();
    }

    public String toString(boolean shift) {
        if(shift)
            return "\tItem: \n" +
                "\t\tItemId: " + ItemId + "\n" +
                "\t\tcategory: " + category + '\n' +
                "\t\tname: " + name;
        return "Item: \n" +
                "\tItemId: " + ItemId + "\n" +
                "\tcategory: " + category + '\n' +
                "\tname: " + name;
    }

    public String toStringId(){
        return "" +  ItemId;
    }
}
