package BusinessLayer.Fcade.outObjects;

import java.util.List;

public class Categories {
    List<Integer> categoryIDs;
    public Categories(List<Integer> IDs){
        categoryIDs=IDs;
    }

    @Override
    public String toString() {
        return "The IDs of all current categories: "+categoryIDs.toString()+"\n";
    }
}
