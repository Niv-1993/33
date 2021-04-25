package DataAccessLayer.DalObjects;

import DataAccessLayer.DalControllers.DalController;

public class DalSupplierCard extends DalObject<DalSupplierCard>{
    protected DalSupplierCard(DalController<DalSupplierCard> controller) {
        super(controller);
    }

    @Override
    public void delete() {

    }
}
