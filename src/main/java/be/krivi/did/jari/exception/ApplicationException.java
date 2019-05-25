package be.krivi.did.jari.exception;

public class ApplicationException extends RuntimeException{

    public ApplicationException(){
        super();
    }

    public ApplicationException( String message ){
        super( message );
    }

    public ApplicationException( Throwable throwable ){
        super( throwable );
    }

    public ApplicationException( String message, Throwable exception ){
        super( message, exception );
    }
}