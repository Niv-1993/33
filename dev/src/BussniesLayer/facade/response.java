package BussniesLayer.facade;

public class response {
    private String error;
    private boolean isError;

    public response(){
        error = null;
        isError = false;
    }

    public response(String error){
        this.error = error;
        isError = true;
    }
}
