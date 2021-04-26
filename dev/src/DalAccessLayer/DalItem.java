package DalAccessLayer;

public class DalItem extends DalObject<DalItem>{
    private int itemId;
    private String name;
    private double price;
    private int typeId;
    private String expirationDate;
    private int SupplierBN;
    private int OrderId;

    protected DalItem() {
        super();
    }

    public void Insert(int SupplierBn , int itemId , String name , double price , int typeId , String expirationDate){
        this.SupplierBN = SupplierBn;
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.typeId = typeId;
        this.expirationDate = expirationDate;
        //INSERT TO SuppliersItems;
    }

    public void Insert(int supplierBN , int orderId , int itemId){
        this.SupplierBN = supplierBN;
        this.OrderId = orderId;
        this.itemId = itemId;
        //insert to ordersItems.
    }

    public void Save(double price){
        this.price = price;
        //save the new price.
    }

    public int GetId (){ return itemId; }

    public String GetName() { return name; }

    public double GetPrice() { return price; }

    public int GetTypeID() { return typeId; }



}


