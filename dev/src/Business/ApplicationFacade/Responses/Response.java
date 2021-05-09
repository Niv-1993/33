package Business.ApplicationFacade.Responses;

public class Response
{
    public final String ErrorMessage;
    public boolean ErrorOccured() { return (ErrorMessage != null); }
    public Response(String errorMessage) {
        ErrorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public Response() {ErrorMessage = null;}
}
