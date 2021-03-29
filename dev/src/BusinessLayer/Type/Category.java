package BusinessLayer.Type;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private List<Category> _categories;
    private int _categoryID;
    private String _name;
    private List<Integer> _productTypes;
    private List<Integer> _productDiscounts;

    public Category(int _categoryID, String _name) {
        this._categoryID = _categoryID;
        this._name = _name;
        _categories=new ArrayList<>();
        _productTypes=new ArrayList<>();
        _productDiscounts=new ArrayList<>();
    }

    public List<Category> get_categories() {
        return _categories;
    }

    public void set_categories(List<Category> _categories) {
        this._categories = _categories;
    }

    public int get_categoryID() {
        return _categoryID;
    }


    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public List<Integer> get_productTypes() {
        return _productTypes;
    }

    public void set_productTypes(List<Integer> _productTypes) {
        this._productTypes = _productTypes;
    }

    public List<Integer> get_productDiscounts() {
        return _productDiscounts;
    }

    public void set_productDiscounts(List<Integer> _productDiscounts) {
        this._productDiscounts = _productDiscounts;
    }


}
