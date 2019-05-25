package be.krivi.did.jari.service;

public interface MessageService{

    String get( String id );

    String get( String id, Object... args );
}
