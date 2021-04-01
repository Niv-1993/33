package BussinessLayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemService {
    private HashMap<Integer,Item> items;

    public HashMap<Integer, Item> getItems() {
        return items;
    }
    public List<Item> getItemsList(){
        return new ArrayList<>(items.values());
    }
}
