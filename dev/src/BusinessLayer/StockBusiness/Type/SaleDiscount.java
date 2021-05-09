package BusinessLayer.StockBusiness.Type;

import DAL.DalStock.DALSaleDiscount;
import DAL.Mapper;
import Utility.Util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SaleDiscount extends Discount{
    DALSaleDiscount dal;

    public SaleDiscount(int storeID,int _discountID, float _percent, Date _start, Date _end) {
        super(storeID,_discountID, _percent, _start, _end);
    }

    public SaleDiscount(int id, Integer i) {
        List<Integer> list=new ArrayList<>();
        list.add(id);
        list.add(i);
        dal=(DALSaleDiscount) Mapper.getMap().getItem(DALSaleDiscount.class,list);
    }

    @Override
    protected void init(int storeID, int id, float percent, Date start, Date end) {
        dal=Util.initDal(DALSaleDiscount.class,storeID,id,percent,start,end);
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
        productType.removeDiscount(this);
    }



}
