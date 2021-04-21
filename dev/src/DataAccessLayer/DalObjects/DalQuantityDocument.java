package DataAccessLayer.DalObjects;

import DataAccessLayer.DalControllers.DalController;

public class DalQuantityDocument extends DalObject<DalQuantityDocument>{
    protected DalQuantityDocument(DalController<DalQuantityDocument> controller) {
        super(controller);
    }

    @Override
    public void delete() {

    }
}
