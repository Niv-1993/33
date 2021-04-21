package DataAccessLayer.DalObjects;

import DataAccessLayer.DalControllers.DalController;

public abstract class DalObject <T>{
    protected DalController<T> controller;

    protected DalObject(DalController<T> controller){
        this.controller = controller;
    }

    public abstract void delete();
}
