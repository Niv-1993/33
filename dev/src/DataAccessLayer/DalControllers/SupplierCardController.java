package DataAccessLayer.DalControllers;

import DataAccessLayer.DalObjects.DalSupplierCard;

public class SupplierCardController extends DalController<DalSupplierCard>{

    private final String tableName = "SupplierCards";

    public SupplierCardController(){super("SupplierCards");}
}
