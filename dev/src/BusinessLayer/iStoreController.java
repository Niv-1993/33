package BusinessLayer;


import BusinessLayer.Type.Category;
import BusinessLayer.Type.ProductType;
import BusinessLayer.Type.SaleDiscount;
import BusinessLayer.Type.SupplierDiscount;
import BusinessLayer.instance.Product;
import reports.Report;

import java.util.Date;
import java.util.List;

public interface iStoreController {
    public Report getWeeklyReport();
    public Report getWeeklyReport(Category... c);
    public Report getNeededReport();
    public Report getWasteReport();
    public void addCategory(String name);
    public void addCategory(String name, int superCategory);
    public List<Category> getCategories();
    public void editCategory(int Id, String name, int superCategory);
    public void editCategory(int Id, String name);
    public void addProductType(String name, int minAmount, float basePrice, String producer, int supID, int category);
    public List<ProductType> getProductTypes();
    public ProductType getProductTypeInfo(int id);
    public void editProductType(String name, int minAmount, float basePrice, String producer, int supID, int category);
    public void addSaleProductDiscount(int productTypeID, float percent, Date start, Date end);
    public void addSaleCategoryDiscount(int productTypeID, float percent, Date start,Date end);
    public void addSupplierDiscount(int categoryID, float percent, Date start,Date end,int supId);
    public void addProduct(int typeID, Date expiration );
    public void removeProduct(int ID);
    public void reportDamage(int ID);
    public Product getProductInfo(int ID);
    public int getShelvesAmount(int typeID);
    public int getStorageAmount(int typeID);
    public List<SupplierDiscount> getSupplierDiscounts(int typeID);
    public List<SaleDiscount> getSaleDiscounts(int typeID);
    public void relocateProduct(int ID, boolean toStorage, int targetShelf);
}
