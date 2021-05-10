package BusinessLayer.StockBusiness.Fcade;

import BusinessLayer.StockBusiness.Fcade.outObjects.*;

import java.util.Date;
import java.util.List;

public interface iStorageService {
    public ResponseData<Report> getWeeklyReport();
    // need List format for CLI
    public ResponseData<Report> getWeeklyReport(List<Integer> c);

    public ResponseData<Report> getNeededReport();
    public ResponseData<Report> getWasteReport();
    public Response addCategory(String name);
    public Response addCategory(String name, int superCategory);
    public ResponseData<Categories> getCategories();
    public ResponseData<Category> getCategoryInfo(int id);
    public Response editCategory(int Id, String name, int superCategory);
    public Response editCategory(int Id, String name);
    //added sale price
    public Response addProductType(String name, int minAmount, double basePrice, double salePrice, String producer, int supID, int category);
    public ResponseData<AllType> getProductTypes();
    public ResponseData<ProductType> getProductTypeInfo(int id);
    //added sale price + ID
    public Response editProductType(int ID,String name, int minAmount, double basePrice, double salePrice, String producer, int supID, int category);

    public Response addSaleProductDiscount(int productTypeID, double percent, Date start,Date end);
    public Response addSaleCategoryDiscount(int catID, double percent, Date start,Date end);
    public Response addSupplierDiscount(int typeID, double percent, Date start,Date end,int supId);
    public ResponseData<List<Integer>> getProductsByType(int typeID);
    public Response addProduct(int typeID, Date expiration );
    public Response removeProduct(int ID);
    public Response reportDamage(int ID);
    public ResponseData<Product> getProductInfo(int ID);
    public ResponseData<Integer> getShelvesAmount(int typeID);
    public ResponseData<Integer> getStorageAmount(int typeID);
    public ResponseData<SupplierDiscounts> getSupplierDiscounts(int typeID);
    public ResponseData<SaleDiscounts> getSaleDiscounts(int typeID);
    public Response relocateProduct(int ID, boolean toStorage, int targetShelf);
    public ResponseData<Integer> addStore();
    public ResponseData<List<Integer>> getStores();
    public Response useStore(int ID);
}
