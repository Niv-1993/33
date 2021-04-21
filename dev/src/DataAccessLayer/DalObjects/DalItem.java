package DataAccessLayer.DalObjects;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalControllers.ItemController;

import java.time.LocalDate;

public class DalItem extends DalObject<DalItem>{
    private int itemId;
    private String name;
    private double price;
    private int typeId;
    private LocalDate expirationDate;

    protected DalItem(DalController<DalItem> controller) {
        super(controller);
    }

    protected DalItem(int itemId , String name , double price, int typeId, LocalDate expirationDate) {
        super(new ItemController());
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.typeId = typeId;
        this.expirationDate = expirationDate;
    }

    @Override
    public void delete() {}
}
