package be.krivi.did.jari.controller;

import be.krivi.did.jari.response.Response;
import be.krivi.did.jari.service.SlackService;
import be.krivi.did.jari.validation.SlackUserId;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Produces;

@Validated
@RestController
@RequestMapping( path = "/slack" )
@Produces( MediaType.APPLICATION_JSON_VALUE )
public class SlackController{

    private SlackService slackService;

    public SlackController( SlackService slackService ){
        this.slackService = slackService;
    }

    @RequestMapping( method = RequestMethod.GET )
    public ResponseEntity<Response> verify(){
        if( slackService.verifyEnvironment() )
            return new ResponseEntity<>( Response.good( "Slack API client ID and secret are set." ), HttpStatus.OK );
        return new ResponseEntity<>( Response.bad( "Slack API client ID and/or secret missing or malformatted." ), HttpStatus.INTERNAL_SERVER_ERROR );
    }

    @RequestMapping( method = RequestMethod.GET, path = { "/auth", "/authorize" } )
    public RedirectView auth( HttpServletRequest request,
                              @RequestParam( value = "user", defaultValue = "" ) @SlackUserId( message = "SlackController.auth.SlackUserId.userId", allowNull = true ) String userId ){
        RedirectView redirect = new RedirectView();
        String authUrl = request.getRequestURL().toString()
                .replace( "/auth", "/authenticate" )
                .replace( "/authorize", "/authenticate" );

        redirect.setUrl( slackService.getAuthorizeUrl( authUrl, userId ) );
        return redirect;
    }

    @RequestMapping( method = RequestMethod.GET, path = "/authenticate" )
    public ResponseEntity<Response> verify( HttpServletRequest request,
                                            @RequestParam( value = "error", defaultValue = "" ) String error,
                                            @RequestParam( value = "code", defaultValue = "" ) String code,
                                            @RequestParam( value = "state", defaultValue = "" ) @SlackUserId( message = "SlackController.verify.SlackUserId.userId", allowNull = true ) String userId ){
        if( !error.isBlank() )
            return new ResponseEntity<>( Response.bad( "You did not grant me access. 😢" ), HttpStatus.UNAUTHORIZED );
        if( code.isBlank() )
            return new ResponseEntity<>( Response.bad( "Request does not contain a code to verify." ), HttpStatus.BAD_REQUEST );

        return new ResponseEntity<>( Response.good( slackService.authenticate( request.getRequestURL().toString(), userId, code ) ), HttpStatus.OK );
    }
}