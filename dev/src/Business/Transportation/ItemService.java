package Business.Transportation;

import java.util.List;

public class ItemService {

    private DataControl dataControl;

    public ItemService(){
        dataControl=new DataControl();

    }
    public List<Item> getItems() throws Exception {

        return dataControl.getItems();


    }
    public Item getItem(long id) throws Exception {
       return dataControl.getItem(id);

    }
    public List<Item> getItemsList() throws Exception {
        return dataControl.getItems();
    }


    public List<Item> getItemsBySupplier(int id) throws Exception {

        return dataControl.getItemsBySupplier(id);

    }
}
