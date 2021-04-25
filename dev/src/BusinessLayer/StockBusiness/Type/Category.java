package BusinessLayer.Type;

import BusinessLayer.StoreController;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Category {
    private List<Category> _categories=new ArrayList<>();
    private int _categoryID;

    public Category get_superCategory() {
        return _superCategory;
    }

    private String _name;
    private Category _superCategory=null;

    private List<Integer> _productTypes= new ArrayList<>();
    private List<Integer> _productDiscounts=new ArrayList<>();
    final static Logger log=Logger.getLogger(StoreController.class);

    public Category(int _categoryID, String _name, Category tmp) {
        checkValues(_categoryID,_name);
        this._categoryID = _categoryID;
        this._name = _name;
        _superCategory=tmp;
        tmp.addAllDiscountCategory(_productTypes);
    }
    private void checkValues(Object... o){
        for(Object o1: o){
            if (o1 instanceof String && o1.equals(""))
            {
                String s="the value is illegal";
                log.warn(s);
                throw new IllegalArgumentException(s);
            }
            else if (o1 instanceof Integer && (Integer)o1<1){
                String s="the value is illegal";
                log.warn(s);
                throw new IllegalArgumentException(s);
            }
        }
    }

    public Category(int catId, String name) {
        checkValues(catId,name);
        if (catId<1 | name==null || name.equals(""))
        {
            String s="the value is illegal";
            log.warn(s);
            throw new IllegalArgumentException(s);
        }
        _categoryID=catId;
        _name=name;
    }

    public List<Category> get_categories() {
        log.debug("get_categories()");
        return _categories;
    }

    public int get_categoryID() {
        log.debug("get_categoryID()");
        return _categoryID;
    }


    public String get_name() {
        log.debug("get_name()");
        return _name;
    }

    public void set_name(String name) {
        if (name==null || name.equals(""))
        {
            String s="the value is illegal";
            log.warn(s);
            throw new IllegalArgumentException(s);
        }
        this._name = name;
    }

    public List<Integer> get_productTypes() {
        log.debug("get_productTypes()");
        return _productTypes;
    }


    public List<Integer> get_productDiscounts() {
        log.debug("get_productDiscounts()");
        return _productDiscounts;
    }
    public void addAllDiscountCategory(List<Integer> list){
        for (Integer i: _productDiscounts)
            if (!list.contains(i))
                list.add(i);
        if (_superCategory!=null)
            _superCategory.addAllDiscountCategory(list);
    }


    public void addCategory(Category output) {
        if (output==null || output==this){
            String s="the Category is illegal";
            log.warn(s);
            throw new IllegalArgumentException(s);
        }
        _categories.add(output);
    }

    public void addProductType(int typeID) {
        log.debug(String.format("addProductType(int typeID)",typeID));
        if (typeID<1)
        {
            String s="the Category is illegal";
            log.warn(s);
            throw new IllegalArgumentException(s);
        }
        _productTypes.add(typeID);
    }

    public List<Integer> getAllProductType() {
        log.debug("getAllProductType()");
        List<Integer> list=_productTypes;
        _categories.stream().map(x->list.addAll(x.getAllProductType()));
        return list;
    }

    public void addDiscount(int count) {
        log.debug(String.format("addDiscount(int count)",count));
        checkValues(count);
        _productDiscounts.add(count);
    }


    public void removeDiscount(int count) {
        log.debug(String.format("removeDiscount(int count)",count));
        checkValues(count);
        _productDiscounts.remove(count);
    }

    public void edit(String name, Category superCategory) {
        log.debug(String.format("edit(String name, Category superCategory) Value:",name));
        checkValues(name);
        if (superCategory==null || superCategory==this)
        {
            String s="the superCategory is illegal";
            log.warn(s);
            throw new IllegalArgumentException(s);
        }
        if (checkRec(superCategory))
        {
            String s=String.format("the Category #%d can not be a child of itself",_categoryID);
            log.warn(s);
            throw new IllegalArgumentException(s);
        }
        _name=name;
        _superCategory=superCategory;
        _superCategory.addAllDiscountCategory(_productDiscounts);
        log.info(String.format("the values of Category #? changed.",_categoryID));
    }

    private boolean checkRec(Category superCategory) {
        if (_categories.contains(superCategory))
            return true;
        for (Category c: _categories){
            if (c.checkRec(superCategory))
                return true;
        }
        return false;
    }

    public void edit(String name) {
        log.debug(String.format("edit(String name) Value:",name));
        checkValues(name);
        _name=name;
        _superCategory=null;
        log.info(String.format("the values of Category #? changed.",_categoryID));
    }

    @Override
    public String toString() {
        return "Category{" +
                "_categories=" + _categories.stream().map(Category::get_categoryID).collect(Collectors.toList()) +
                ", _categoryID=" + _categoryID +
                ", _name='" + _name + '\'' +
                ", _superCategory=" + _superCategory.get_categoryID() +
                ", _productTypes=" + _productTypes +
                ", _productDiscounts=" + _productDiscounts +
                '}';
    }

    public void removeCategory(Category c) {
        _categories.remove(c);
    }

    public void fixDiscount() {
        if (_superCategory!=null)
            _superCategory.fixDiscount(_productDiscounts);
    }
    private void fixDiscount(List<Integer> list){
        for (Integer i: _productDiscounts)
            if (list.contains(i))
                list.remove(i);
        if (_superCategory!=null)
            _superCategory.fixDiscount(list);
    }
}
