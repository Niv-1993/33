package BusinessLayer.SupplierBusiness;

import DAL.DALObject;
import DAL.DalSuppliers.DalItem;
import DAL.DalSuppliers.DalSupplierCard;
import DAL.Mapper;
import Utility.Tuple;
import Utility.Util;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Item{
    private QuantityDocument quantityDocument;
    private DalItem dalItem;
    final static Logger log=Logger.getLogger(Item.class);

    public Item(int supplierBN, int itemId , String name , double price, int typeId, LocalDate expirationDate){
        List<Tuple<Object,Class>> list=new ArrayList<>();
        list.add(new Tuple<>(itemId,Integer.class));
        list.add(new Tuple<>(supplierBN,Integer.class));
        list.add(new Tuple<>(name,String.class));
        list.add(new Tuple<>(price,Double.class));
        list.add(new Tuple<>(typeId,Integer.class));
        list.add(new Tuple<>(expirationDate.toString(),String.class));
        Mapper map=Mapper.getMap();
        map.setItem(DalItem.class,list);
        List<Integer> keyList=new ArrayList<>();
        keyList.add(itemId);
        DALObject check =map.getItem(DalItem.class ,keyList);
        if (DalItem.class==null || check==null ||(check.getClass()!=DalItem.class)){
            String s="the instance that return from Mapper is null";
            log.warn(s);
            throw new IllegalArgumentException(s);
        }
        else{
            log.info("create new Object");
            dalItem = (DalItem) check;
        }
        //dalItem.load(itemId);
        quantityDocument = null;
    }

    public void addQuantityDocument(int minimalAmount, int discount) throws Exception {
        if(minimalAmount < 0) throw new Exception("minimal amount must be a positive number");
        if(discount < 0 || discount > 100) throw new Exception("discount must be a positive number between 0 to 100");
        quantityDocument = new QuantityDocument(dalItem.getItemId(), minimalAmount, discount, 0);
    }

    public void removeQuantityDocument() throws Exception {
        if(quantityDocument == null) throw new Exception("quantity document all ready removed");
        quantityDocument = null;
    }

    public QuantityDocument showQuantityDocument() throws Exception {
        if(quantityDocument == null) throw new Exception("quantity document all ready removed");
        return quantityDocument;
    }

    public void updateMinimalAmountOfQD(int minimalAmount) throws Exception {
        try {
            quantityDocument.updateMinimalAmountOfQD(minimalAmount);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public void updateDiscountOfQD(int discount) throws Exception {
        try {
            quantityDocument.updateDiscountOfQD(discount);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public int getItemId() {
        return dalItem.getItemId();
    }

    public QuantityDocument getQuantityDocument(){
        return quantityDocument;
    }

    public double getPrice() {
        return dalItem.getPrice();
    }

    public void updatePrice(double price) throws Exception {
        if(price < 0) throw new Exception("price must be a positive number");
        dalItem.updatePrice(price);
    }

    public String getName() { return dalItem.getName(); }

    public int getTypeID() { return dalItem.getTypeID(); }

    public LocalDate getExpirationDate() { return LocalDate.parse(dalItem.getExpirationDate()); }

}
