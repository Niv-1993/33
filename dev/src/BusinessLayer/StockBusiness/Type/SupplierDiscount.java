package BusinessLayer.StockBusiness.Type;

import DAL.DalStock.DALSupplierDiscount;
import DAL.Mapper;
import Utility.Util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SupplierDiscount extends Discount{
    DALSupplierDiscount dal;

    public SupplierDiscount(int storeId, Integer i) {
        super();
        List<Integer> list=new ArrayList<>();
        list.add(storeId);
        list.add(i);
        dal=(DALSupplierDiscount) Mapper.getMap().getItem(DALSupplierDiscount.class,list);
    }

    public int get_supplierID() {
        return dal.getSupplier();
    }

    public SupplierDiscount(int storeID,int _discountID, float _percent, Date _start, Date _end, int sup) {
        super(storeID,_discountID, _percent, _start, _end);
        dal.setSupplier(sup);
    }

    @Override
    protected void init(int storeID, int id, double percent, Date start, Date end) {
        dal= Util.initDal(DALSupplierDiscount.class,storeID,id,percent,start,end);
    }

    @Override
    public int get_discountID() {
        return dal.getID();
    }

    @Override
    public float get_percent() {
        return dal.getPercent();
    }

    @Override
    public Date get_start() {
        return dal.getStartDate();
    }

    @Override
    public Date get_end() {
        return dal.getEndDate();
    }

    @Override
    public void addTo(ProductType productType) {
        log.info("addTo(ProductType productType)");
        productType.addDiscount(this);
    }

    @Override
    public void removeFrom(ProductType productType) {
        productType.removeDiscountFromList(this);
    }

}
