package Business.ApplicationFacade.Responses;
public class ResponseT<T> extends Response {
    private  T Value;

    public ResponseT(String msg) {
        super(msg);
    }

    public ResponseT(T value) {
        super();

        this.Value = value;
    }

    public T getValue() {
        return Value;
    }

    public ResponseT(T value, String msg) {
        super(msg);
        this.Value = value;
    }

}