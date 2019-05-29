package be.krivi.did.jari.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude( JsonInclude.Include.NON_NULL )
public class Response{

    private boolean ok;
    private ResponseData data;
    private String message;
    private String error;

    private Response(){
        // nop
    }

    public static Response good( ResponseData data ){
        Response response = new Response();
        response.ok = true;
        response.data = data;
        return response;
    }

    public static Response good( String message ){
        Response response = new Response();
        response.ok = true;
        response.message = message;
        return response;
    }

    public static Response bad( String message ){
        Response response = new Response();
        response.ok = false;
        response.error = message;
        return response;
    }
}
