package BusinessLayer.Fcade;

public class ResponseData<T> extends Response{
    public T data;
    public ResponseData(String error) {
        super(error);
    }
    public T getData() {
        return data;
    }
    public ResponseData(T data) {
        this.data = data;
    }
}
