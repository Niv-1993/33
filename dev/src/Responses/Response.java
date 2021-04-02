package Responses;

public class Response <T>{
    private  T value;
    private boolean errorIsOccurred;
    private String errorMessage;
    public Response(){
        errorIsOccurred = false;
        errorMessage = null;
    }
    public String getErrorMessage(){ return errorMessage; }
    public Response (T obj){this.value=obj;}

    public T getValue() { return value; }

    public void setValue(T value) { this.value = value; }

    public boolean isErrorOccurred() { return errorIsOccurred; }

    public void setErrorOccurred(String message) {
        errorIsOccurred = true;
        errorMessage = message;
    }
}
