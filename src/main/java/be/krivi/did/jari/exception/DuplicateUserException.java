package be.krivi.did.jari.exception;

public class DuplicateUserException extends RuntimeException{

    public DuplicateUserException( String message ){
        super( message );
    }
}