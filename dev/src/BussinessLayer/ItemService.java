package BussinessLayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemService {
    private HashMap<Long,Item> items;


    public Item getItem(long id){
        if(items.containsKey(id)){
            return items.get(id);
        }
        throw new IllegalArgumentException("item si id: " + id + "does not exists");
    }

    public List<Item> getItemsList(){
        return new ArrayList<>(items.values());
    }
    /**
     * load all the items from the database using the control
     * @param dataControl : the control that ask the database for the data.
     */
    public void loadData(DataControl dataControl) {

        items=dataControl.getItems();
    }
}
