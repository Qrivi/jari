package be.krivi.did.jari.controller;

import be.krivi.did.jari.response.Response;
import be.krivi.did.jari.slack.SlackService;
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
        produces = MediaType.APPLICATION_JSON_VALUE )
public class SlackController{

    private SlackService slackService;

    public SlackController( SlackService slackService ){
        this.slackService = slackService;
    }

    @RequestMapping( path = "/auth", method = RequestMethod.GET )
    public RedirectView auth( HttpServletRequest request ){
        RedirectView redirect = new RedirectView();
        String url = request.getRequestURL().toString().replace( "/auth", "/verify" );
        redirect.setUrl( slackService.getAuthorizeUrl( url ) );
        return redirect;
    }

    @RequestMapping( path = "/verify", method = RequestMethod.GET )
    public ResponseEntity<Response> verify( HttpServletRequest request, @RequestParam( "code" ) String code, @RequestParam( "state" ) String state ){
        if( code == null )
            return new ResponseEntity<>( Response.makeErrorRespone( "Request does not contain a code to verify." ), HttpStatus.BAD_REQUEST );
        if( !slackService.verifyState( state ) )
            return new ResponseEntity<>( Response.makeErrorRespone( "Request does not contain a valid state." ), HttpStatus.FORBIDDEN );

    }
