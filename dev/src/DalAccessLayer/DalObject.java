package DalAccessLayer;


public abstract class DalObject <T>{
    protected DalController<DalObject<T>> controller;


    protected DalObject(){
        this.controller = new DalController<>();
    }


    public void Insert(String attribute , String condition){
    }

    public void Save(String attribute , String condition){

    }

    public void Get(String attribute , String condition){

    }

    public void Delete(String attribute , String condition){

    }
}
