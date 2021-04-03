package BussniesLayer.facade;

public class Tresponse <T> extends response{
    private T outObject;

    public Tresponse(T outObject) { this.outObject = outObject;}

    public T getOutObject() { return outObject; }
}
