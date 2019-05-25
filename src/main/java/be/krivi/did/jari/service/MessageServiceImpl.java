package be.krivi.did.jari.service;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageServiceImpl implements MessageService{

    private final MessageSource messageSource;

    public MessageServiceImpl( MessageSource messageSource ){
        this.messageSource = messageSource;
    }

    @Override
    public String get( String id ){
        try{
            return messageSource.getMessage( id, null, LocaleContextHolder.getLocale() );
        }catch( NoSuchMessageException e ){
            return id;
        }
    }

    @Override
    public String get( String id, Object... args ){
        try{
            return messageSource.getMessage( id, args, LocaleContextHolder.getLocale() );
        }catch( NoSuchMessageException e ){
            return id;
        }
    }
}
