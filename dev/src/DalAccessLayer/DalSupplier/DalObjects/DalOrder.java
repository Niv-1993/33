package DataAccessLayer.DalObjects;

import DataAccessLayer.DalControllers.DalController;

public class DalOrder extends DalObject<DalOrder>{

    protected DalOrder(DalController<DalOrder> controller) {
        super(controller);
    }

    @Override
    public void delete() {

    }
}
