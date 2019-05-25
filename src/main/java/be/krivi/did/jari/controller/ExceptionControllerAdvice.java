package be.krivi.did.jari.controller;

import be.krivi.did.jari.exception.SlackException;
import be.krivi.did.jari.response.Response;
import be.krivi.did.jari.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice{

    private MessageService messageService;

    public ExceptionControllerAdvice( MessageService messageService ){
        this.messageService = messageService;
    }

    @ExceptionHandler
    public ResponseEntity<Response> handleSlackException( SlackException e ){
        return new ResponseEntity<>( Response.bad( messageService.get( e.getMessageKey() ) ), HttpStatus.BAD_REQUEST );
    }
}
