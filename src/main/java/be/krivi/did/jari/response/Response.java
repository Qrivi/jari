package be.krivi.did.jari.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude( JsonInclude.Include.NON_NULL )
public class Response{

    private boolean ok;
    private Object data;
    private String message;
    private String error;

    private Response(){
        // nop
    }

    public static Response good( Object data ){
        Response response = new Response();
        response.setOk( true );
        response.setData( data );
        return response;
    }

    public static Response good( String message ){
        Response response = new Response();
        response.setOk( true );
        response.setMessage( message );
        return response;
    }

    public static Response bad( String message ){
        Response response = new Response();
        response.setOk( false );
        response.setError( message );
        return response;
    }

    private void setData( Object data ){
        this.data = data;
    }

    private void setOk( boolean ok ){
        this.ok = ok;
    }

    private void setMessage( String message ){
        this.message = message;
    }

    private void setError( String error ){
        this.error = error;
    }
}
