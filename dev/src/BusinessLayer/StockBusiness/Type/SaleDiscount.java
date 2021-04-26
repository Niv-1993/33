package BusinessLayer.StockBusiness.Type;

import java.util.Date;

public class SaleDiscount extends Discount{

    public SaleDiscount(int _discountID, float _percent, Date _start, Date _end) {
        super(_discountID, _percent, _start, _end);

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
