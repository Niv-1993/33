package BusinessLayer.StockBusiness.Fcade;

import BusinessLayer.StockBusiness.Fcade.outObjects.*;
import BusinessLayer.StockBusiness.StoreController;
import BusinessLayer.StockBusiness.instance.Location;
import BusinessLayer.SupplierBusiness.facade.SupplierService;
import BusinessLayer.SupplierBusiness.facade.Tresponse;
import BusinessLayer.SupplierBusiness.facade.response;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class StorageService implements iStorageService {
    int counter=1;
    int shelves=20;
    int storeShelves=10;
    int MAX_PER_SHELF=100;
    List<StoreController> stores;
    StoreController curr;
    SupplierService supplierService;
    final static Logger log= Logger.getLogger(StorageService.class);

    public StorageService() {
        stores=new ArrayList<>();
        curr=null;
    }

    public Tresponse<Integer> getProductTypeId(String name){
        try {

            return new Tresponse<>(curr.getProductTypeByName(name));
        }
        catch (Exception e) {
            return new Tresponse<>(e.getMessage());
        }
    }

    public response removeSupplier(int supplierId , int itemId){
        try {
            curr.removeSupplier(itemId, supplierId);
            return new response();
        }
        catch (Exception e){
            return new response(e.getMessage());}
    }

    public int getCurrID(){
        return curr.getID();
    }

    @Override
    public Tresponse<Report> getWeeklyReport() {
        try {
            reports.Report rep=curr.getWeeklyReport();
            Report ret=new Report(rep.getStore(),rep.getDate(),rep.toString(),rep.getType());
            return new Tresponse<>(ret);
        }
        catch (Exception e) {
            return new Tresponse<>(e.toString());
        }
    }

    @Override
    public Tresponse<Report> getWeeklyReport(List<Integer> c) {
        try {
            reports.Report rep=curr.getWeeklyReport(c);
            Report ret=new Report(rep.getStore(),rep.getDate(),rep.toString(),rep.getType());
            return new Tresponse<>(ret);
        }
        catch (Exception e) {
            return new Tresponse<>(e.toString());
        }
    }

    @Override
    public Tresponse<Report> getNeededReport() {
        try {
            reports.Report rep=curr.getNeededReport();
            Report ret=new Report(rep.getStore(),rep.getDate(),rep.toString(),rep.getType());
            return new Tresponse<>(ret);
        }
        catch (Exception e) {
            return new Tresponse<>(e.toString());
        }
    }
    @Override
    public Tresponse<NeededReport> getNeededReportToOrder() {
        try {
            reports.NeededReport rep=(reports.NeededReport) curr.getNeededReport();
            NeededReport ret=new NeededReport(rep.getStore(),rep.getDate(),rep.get_list());
            return new Tresponse<>(ret);
        }
        catch (Exception e) {
            return new Tresponse<>(e.toString());
        }
    }

    @Override
    public Tresponse<Report> getWasteReport() {
        try {
            reports.Report rep=curr.getWasteReport();
            Report ret=new Report(rep.getStore(),rep.getDate(),rep.toString(),rep.getType());
            return new Tresponse<>(ret);
        }
        catch (Exception e) {
            return new Tresponse<>(e.toString());
        }
    }

    @Override
    public response addCategory(String name) {
        try {
            curr.addCategory(name);
            return new response();
        }
        catch (Exception e) {
            return new response(e.toString());
        }
    }

    @Override
    public response addCategory(String name, int superCategory) {
        try {
            curr.addCategory(name,superCategory);
            return new response();
        }
        catch (Exception e) {
            return new response(e.toString());
        }
    }

    @Override
    public Tresponse<Categories> getCategories() {
        try {
            return new Tresponse<>(new Categories(curr.getCategories()));
        }
        catch (Exception e) {
            return new Tresponse<>(e.toString());
        }
    }

    @Override
    public Tresponse<Category> getCategoryInfo(int id) {
        try {
            BusinessLayer.StockBusiness.Type.Category ret=curr.getCategory(id);
            List<Integer> cids=new ArrayList<>();
            for(BusinessLayer.StockBusiness.Type.Category c:ret.get_categories()){
                cids.add(c.get_categoryID());
            }
            return new Tresponse<>(new Category(ret.get_categoryID(),ret.get_superCategory()==null?0:ret.get_superCategory().get_categoryID(),ret.get_name(),cids,ret.get_productTypes()));
        }
        catch (Exception e) {
            return new Tresponse<>(e.toString());
        }
    }

    @Override
    public response editCategory(int Id, String name, int superCategory) {
        try {
            curr.editCategory(Id,name,superCategory);
            return new response();
        }
        catch (Exception e) {
            return new response(e.toString());
        }
    }

    @Override
    public response editCategory(int Id, String name) {
        try {
            curr.editCategory(Id,name);
            return new response();
        }
        catch (Exception e) {
            return new response(e.toString());
        }
    }

    @Override
    public response addProductType(String name, int minAmount, double basePrice, double salePrice,String producer, int supID, int category) {
        try {
            curr.addProductType(name,minAmount,basePrice,salePrice,producer,supID,category);
            return new response();
        }
        catch (Exception e) {
            return new response(e.toString());
        }
    }

    @Override
    public Tresponse<AllType> getProductTypes() {
        try {
            return new Tresponse<>(new AllType(curr.getProductTypes()));
        }
        catch (Exception e) {
            return new Tresponse<>(e.toString());
        }
    }

    @Override
    public Tresponse<ProductType> getProductTypeInfo(int id) {
        try {
            BusinessLayer.StockBusiness.Type.ProductType ret=curr.getProductTypeInfo(id);
            return new Tresponse<>(new ProductType(ret.get_typeID(),ret.get_minAmount(),ret.get_categoryID(),ret.get_producer(),
                    ret.get_suppliers(),ret.get_shelfCurr(),ret.get_storageCurr(),ret.get_basePrice(),ret.get_salePrice()));
        }
        catch (Exception e) {
            return new Tresponse<>(e.toString());
        }
    }

    @Override
    public response editProductType(int ID,String name, int minAmount, double basePrice,double salePrice, String producer, int supID, int category) {
        try {
            curr.editProductType(ID,name,minAmount,basePrice,salePrice,producer,supID,category);
            return new response();
        }
        catch (Exception e) {
            log.warn(e);
            return new response(e.toString());
        }
    }

    @Override
    public response addSaleProductDiscount(int productTypeID, double percent, Date start, Date end) {
        try {
            curr.addSaleProductDiscount(productTypeID,percent,start,end);
            return new response();
        }
        catch (Exception e) {
            return new response(e.toString());
        }
    }

    @Override
    public response addSaleCategoryDiscount(int catID, double percent, Date start, Date end) {
        try {
            curr.addSaleCategoryDiscount(catID,percent,start,end);
            return new response();
        }
        catch (Exception e) {
            return new response(e.toString());
        }
    }

    @Override
    public response addSupplierDiscount(int typeID, double percent, Date start, Date end, int supId) {
        try {
            curr.addSupplierDiscount(typeID,percent,start,end,supId);
            return new response();
        }
        catch (Exception e) {
            return new response(e.toString());
        }
    }

    @Override
    public Tresponse<List<Integer>> getProductsByType(int typeID) {
        try {
            return new Tresponse<>(curr.getProductByType(typeID));
        }
        catch (Exception e) {
            return new Tresponse<>(e.toString());
        }
    }

    @Override
    public response addProduct(int typeID, Date expiration) {
        try {
            curr.addProduct(typeID,expiration);
            return new response();
        }
        catch (Exception e) {
            return new response(e.toString());
        }
    }

    @Override
    public response removeProduct(int ID) {
        try {
            curr.removeProduct(ID);
            return new response();
        }
        catch (Exception e) {
            return new response(e.toString());
        }
    }

    @Override
    public response reportDamage(int ID) {
        try {
            curr.reportDamage(ID);
            return new response();
        }
        catch (Exception e) {
            return new response(e.toString());
        }
    }

    @Override
    public Tresponse<Product> getProductInfo(int ID) {
        try {
            BusinessLayer.StockBusiness.Type.ProductType Tret=curr.getProductTypeInfo(ID/curr.MAX_PRODUCTS_ON_PROTUCTTYPE);
            BusinessLayer.StockBusiness.instance.Product Pret=curr.getProductInfo(ID);
            return new Tresponse<>(new Product(Pret.get_id(),Tret.get_typeID(),Pret.get_expiration(),
                    Pret.get_location().item2==Location.Storage,Pret.get_location().item1));
        }
        catch (Exception e) {
            return new Tresponse<>(e.toString());
        }
    }

    @Override
    public Tresponse<Integer> getShelvesAmount(int typeID) {
        try {
            return new Tresponse<>(curr.getShelvesAmount(typeID));
        }
        catch (Exception e) {
            return new Tresponse<>(e.toString());
        }
    }

    @Override
    public Tresponse<Integer> getStorageAmount(int typeID) {
        try {
            return new Tresponse<>(curr.getStorageAmount(typeID));
        }
        catch (Exception e) {
            return new Tresponse<>(e.toString());
        }
    }

    @Override
    public Tresponse<SupplierDiscounts> getSupplierDiscounts(int typeID) {
        try {
            List<BusinessLayer.StockBusiness.Type.SupplierDiscount> get=curr.getSupplierDiscounts(typeID);
            List<SupplierDiscount> ret=new ArrayList<>();
            for (BusinessLayer.StockBusiness.Type.SupplierDiscount d:get){
                ret.add(new SupplierDiscount(d.get_discountID(),d.get_start(),d.get_end(),d.get_percent(),d.get_supplierID()));
            }
            return new Tresponse<>(new SupplierDiscounts(typeID,ret));
        }
        catch (Exception e) {
            return new Tresponse<>(e.toString());
        }
    }

    @Override
    public Tresponse<SaleDiscounts> getSaleDiscounts(int typeID) {
        try {
            List<BusinessLayer.StockBusiness.Type.SaleDiscount> get=curr.getSaleDiscounts(typeID);
            log.error(get);
            int cat=curr.getProductTypeInfo(typeID).get_categoryID();
            get.addAll(curr.getSaleCategoryDiscounts(cat));
            log.error(get);
            List<SaleDiscount> ret=new ArrayList<>();
            for (BusinessLayer.StockBusiness.Type.SaleDiscount d:get){
                ret.add(new SaleDiscount(d.get_discountID(),d.get_start(),d.get_end(),d.get_percent()));
            }
            return new Tresponse<>(new SaleDiscounts(typeID,ret));
        }
        catch (Exception e) {
            return new Tresponse<>(e.toString());
        }
    }

    @Override
    public response relocateProduct(int ID, boolean toStorage, int targetShelf) {
        try {
            curr.relocateProduct(ID,toStorage,targetShelf);
            return new response();
        }
        catch (Exception e) {
            return new response(e.toString());
        }
    }

    @Override
    public Tresponse<Integer> addStore() {
        try {
            stores.add(new StoreController(counter,shelves,storeShelves,MAX_PER_SHELF));
            counter++;
            return new Tresponse<>(counter-1);
        }
        catch (Exception e) {
            log.warn(e);
            return new Tresponse<>(e.toString());
        }

    }

    @Override
    public Tresponse<List<Integer>> getStores() {
        try {
            List<Integer> ret=new LinkedList<>();
            for (StoreController s:stores) {
                ret.add(s.getID());
            }

            if(ret.size()==0) throw new Exception("no stores registered");
            return new Tresponse<>(ret);
        }
        catch (Exception e) {
            return new Tresponse<>(e.toString());
        }
    }

    @Override
    public response useStore(int ID) {
        boolean found = false;
        try {
            StoreController old=curr;
            for(StoreController s:stores){
                System.out.println(s.getID());
                if(s.getID()==ID) {
                    curr = s;
                    found = true;
                    break;
                }
            }
            if(!found) throw new Exception("Store not found.");
            return new response();
        }
        catch (Exception e) {
            return new response(e.toString());
        }
    }
    public static void init(StorageService ss) throws ParseException {
        ss.addStore();
        ss.useStore(1);
        for (int i = 1; i < 11; i++) ss.addCategory("root" + i);
        for (int i = 1; i < 21; i++) {
            ss.addCategory("ch" + i, i);

        }
        for (int i = 1; i < 15; i++) {
            for (int j = 1; j < 10; j++) {
                ss.addProductType("p" + i + "" + j, 8, i * j / 2, i * j * j / 4, "P" + i + "" + j, i, i);
            }
        }
        for (int i = 0; i < ss.getProductTypes().getOutObject().size(); i++) {
            for (int j = 0; j < i * 3; j++) {
                ss.addProduct(i, new Date(System.currentTimeMillis()+1000000000)); // expire in approx 20 days
            }
        }
        ss.addSaleCategoryDiscount(1, 12, new SimpleDateFormat("dd-MM-yyyy").parse("01-01-2000"), new SimpleDateFormat("dd-MM-yyyy").parse("01-01-2050"));
    }

    public void loadAllStores() {
        stores=new ArrayList<>();
        int i=1;
        while(true){
            try{
                stores.add(new StoreController(i));
                i++;
            }catch (Exception e){
                log.warn(e);
                break;
            }
        }
        counter=stores.size()+1;
    }

    public void setStockService(SupplierService service) {
        supplierService=service;
    }
}
