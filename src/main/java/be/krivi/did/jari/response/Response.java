package be.krivi.did.jari.response;

public class Response{

    private boolean ok;
    private String status;
    private String error;

    private Response(){
        // nop
    }

    public static Response makeGoodRespone( String message ){
        Response response = new Response();
        response.setOk( false );
        response.setStatus( message );
        return response;
    }

    public static Response makeErrorRespone( String message ){
        Response response = new Response();
        response.setOk( false );
        response.setError( message );
        return response;
    }

    private void setOk( boolean ok ){
        this.ok = ok;
    }

    private void setStatus( String status ){
        this.status = status;
    }

    private void setError( String error ){
        this.error = error;
    }
}
