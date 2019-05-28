package be.krivi.did.jari.exception;

public class DuplicateBotException extends RuntimeException{

    public DuplicateBotException( String message ){
        super( message );
    }
}