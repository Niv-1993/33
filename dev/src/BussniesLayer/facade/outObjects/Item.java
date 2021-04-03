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
        return "Item{" +
                "ItemId=" + ItemId +
                ", category='" + category + '\'' +
                '}';
    }

    public String toStringId(){
        return ItemId + "";
    }
}
