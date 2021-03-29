package BusinessLayer.Type;

import java.util.Date;

public class SupplierDiscount extends Discount{
    private int _supplierID;

    public int get_supplierID() {
        return _supplierID;
    }

    public SupplierDiscount(int _discountID, float _percent, Date _start, Date _end, int sup) {
        super(_discountID, _percent, _start, _end);
        _discountID=sup;
    }
}
