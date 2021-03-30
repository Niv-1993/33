package BusinessLayer.Type;

import java.util.ArrayList;
import java.util.List;

public class ProductType {
    private int _typeID;
    private int _categoryID;
    private List<Integer> _products;
    private String _name;
    private int _minAmount;
    private int _shelfCurr;
    private int _storageCurr;
    private float _basePrice;
    private float _salePrice;
    private String _producer;
    private List<Integer> _suppliers;
    private List<SaleDiscount> _saleDiscounts;
    private List<SupplierDiscount> _supplierDiscounts;


    public ProductType(int _typeID, int _categoryID, String _name, int _minAmount, float _basePrice, float _salePrice, String _producer, List<Integer> _suppliers) {
        this._typeID = _typeID;
        this._categoryID = _categoryID;
        this._name = _name;
        this._minAmount = _minAmount;
        this._basePrice = _basePrice;
        this._salePrice = _salePrice;
        this._producer = _producer;
        this._suppliers=_suppliers;
        _products=new ArrayList<>();
        _saleDiscounts=new ArrayList<>();
        _supplierDiscounts=new ArrayList<>();
    }

    public int get_typeID() {
        return _typeID;
    }


    public int get_categoryID() {
        return _categoryID;
    }

    public void set_categoryID(int _categoryID) {
        this._categoryID = _categoryID;
    }

    public List<Integer> get_products() {
        return _products;
    }

    public void set_products(List<Integer> _products) {
        this._products = _products;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public int get_minAmount() {
        return _minAmount;
    }

    public void set_minAmount(int _minAmount) {
        this._minAmount = _minAmount;
    }

    public int get_shelfCurr() {
        return _shelfCurr;
    }

    public void set_shelfCurr(int _shelfCurr) {
        this._shelfCurr = _shelfCurr;
    }

    public int get_storageCurr() {
        return _storageCurr;
    }

    public void set_storageCurr(int _storageCurr) {
        this._storageCurr = _storageCurr;
    }

    public float get_basePrice() {
        return _basePrice;
    }

    public void set_basePrice(float _basePrice) {
        this._basePrice = _basePrice;
    }

    public float get_salePrice() {
        return _salePrice;
    }

    public void set_salePrice(float _salePrice) {
        this._salePrice = _salePrice;
    }

    public String get_producer() {
        return _producer;
    }

    public void set_producer(String _producer) {
        this._producer = _producer;
    }

    public List<Integer> get_suppliers() { return _suppliers;    }

    public void set_suppliers(List<Integer> _suppliers) { this._suppliers = _suppliers; }


}
