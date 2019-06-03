package be.krivi.did.jari.controller;

import be.krivi.did.jari.exception.SlackException;
import be.krivi.did.jari.response.Response;
import be.krivi.did.jari.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionControllerAdvice{

    private MessageService messageService;

    public ExceptionControllerAdvice( MessageService messageService ){
        this.messageService = messageService;
    }

    @ExceptionHandler
    public ResponseEntity<Response> handleConstraintViolationException( ConstraintViolationException e ){
        String messages = e.getConstraintViolations()
                .stream()
                .map( ConstraintViolation::getMessageTemplate )
                .map( m -> m.replaceAll( "[{}]", "" ) )
                .map( messageService::get )
                .collect( Collectors.joining( ". ", "", "." ) );

        return new ResponseEntity<>( Response.bad( messages ), HttpStatus.BAD_REQUEST );
    }

    @ExceptionHandler
    public ResponseEntity<Response> handleTransactionSystemException( TransactionSystemException e ){
        if( e.getRootCause() instanceof ConstraintViolationException )
            return handleConstraintViolationException( (ConstraintViolationException) e.getRootCause() );
        return new ResponseEntity<>( Response.bad( e.getMessage() ), HttpStatus.INTERNAL_SERVER_ERROR );
    }

    @ExceptionHandler
    public ResponseEntity<Response> handleSlackException( SlackException e ){
        return new ResponseEntity<>( Response.bad( messageService.get( e.getMessageKey() ) ), HttpStatus.BAD_REQUEST );
    }
}
