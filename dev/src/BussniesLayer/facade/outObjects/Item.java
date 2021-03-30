package BussniesLayer.facade.outObjects;

public class Item {
    private String category;
    private int ItemId;

    public Item(BussniesLayer.Item item) {
        category = item.getCategory();
        ItemId = item.getItemId();
    }
}
