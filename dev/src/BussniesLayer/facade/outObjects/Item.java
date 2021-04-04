package BussniesLayer.facade.outObjects;

public class Item {
    private int ItemId;
    private String category;

    public Item(BussniesLayer.Item item) {
        ItemId = item.getItemId();
        category = item.getCategory();
    }

    @Override
    public String toString() {
        return "Item: \n" +
                "\tItemId = " + ItemId + "\n" +
                "\tcategory = " + category + '\n';
    }

    public String toStringId(){
        return ItemId + "\n";
    }
}
