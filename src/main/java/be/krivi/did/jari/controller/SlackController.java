package be.krivi.did.jari.controller;

import be.krivi.did.jari.response.Response;
import be.krivi.did.jari.service.SlackService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(
        path = "/slack",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE )
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

    @RequestMapping( path = "/authorize", method = RequestMethod.GET )
    public RedirectView auth( HttpServletRequest request,
                              @RequestParam( value = "user", defaultValue = "" ) String userId ){
        RedirectView redirect = new RedirectView();
        String authUrl = request.getRequestURL().toString().replace( "/authorize", "/authenticate" );
        redirect.setUrl( slackService.getAuthorizeUrl( authUrl, userId ) );
        return redirect;
    }

    @RequestMapping( path = "/authenticate", method = RequestMethod.GET )
    public ResponseEntity<Response> verify( HttpServletRequest request,
                                            @RequestParam( value = "code", defaultValue = "" ) String code,
                                            @RequestParam( value = "state", defaultValue = "" ) String userId,
                                            @RequestParam( value = "error", defaultValue = "" ) String error ){
        if( !error.isBlank() )
            return new ResponseEntity<>( Response.bad( "You did not grant me access. 😢" ), HttpStatus.UNAUTHORIZED );
        if( code.isBlank() )
            return new ResponseEntity<>( Response.bad( "Request does not contain a code to verify." ), HttpStatus.BAD_REQUEST );

        String team = slackService.authenticate( request.getRequestURL().toString(), userId, code );
        return new ResponseEntity<>( Response.good( "Thanks for granting me some permissions in \"" + team + "\"!" ), HttpStatus.OK );
    }
}