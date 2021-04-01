package Responses;

public class Response <T>{

    public T value;
    public boolean ErrorOccured;
    public Response(){}
    public Response (T obj){this.value=obj;}

    public T getValue() { return value; }

    public void setValue(T value) { this.value = value; }

    public boolean isErrorOccured() { return ErrorOccured; }

    public void setErrorOccured(boolean errorOccured) { ErrorOccured = errorOccured; }
}
