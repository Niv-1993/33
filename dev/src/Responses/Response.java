package Responses;

public class Response <T>{
    public T value;
    public boolean errorIsOccurred;
    public Response(){}
    public Response (T obj){this.value=obj;}

    public T getValue() { return value; }

    public void setValue(T value) { this.value = value; }

    public boolean isErrorOccurred() { return errorIsOccurred; }

    public void setErrorOccurred(boolean errorOccurred) { errorOccurred = errorOccurred; }
}
