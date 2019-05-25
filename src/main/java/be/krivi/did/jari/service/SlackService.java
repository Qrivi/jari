package be.krivi.did.jari.service;

import be.krivi.did.jari.exception.SlackException;
import be.krivi.did.jari.model.dto.slack.Access;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.Base64;
import java.util.stream.Collectors;

@Service
public class SlackService{

    private static final String URL_AUTHORIZE = "https://slack.com/oauth/authorize";
    private static final String URL_OAUTH_ACCESS = "https://slack.com/api/oauth.access";

    private static final String[] READ_SCOPE = {
            "channels:history",
            "groups:history",
            "im:history",
            "mpim:history"
    };
    private static final String[] WRITE_SCOPE = {
            "chat:write:user",
            "chat:write:bot",
            "bot"
    };

    private Client client;
    private String clientId;
    private String clientSecret;
    private String scope;

    private UserService userService;
    private BotService botService;

    public SlackService( UserService userService, BotService botService ){
        this.client = ClientBuilder.newClient();
        this.clientId = System.getenv( "SLACK_CLIENT_ID" );
        this.clientSecret = System.getenv( "SLACK_CLIENT_SECRET" );
        this.scope = this.generateScope();

        this.userService = userService;
        this.botService = botService;
    }

    public boolean verifyEnvironment(){
        return this.clientId != null && this.clientId.matches( "^\\d{11}\\.\\d{12}$" )
                && this.clientSecret != null && this.clientSecret.matches( "^[a-z0-9]{32}$" );
    }

    public String getAuthorizeUrl( String authUrl, String userId ){
        if( userId != null && userId.matches( "^xoxp-[0-9]{11}-[0-9]{11}-[0-9]{12}-\\w{32}$" ) )
            return URL_AUTHORIZE + "?client_id=" + this.clientId
                    + "&scope=" + this.scope
                    + "&redirect_uri=" + authUrl
                    + "&state=" + userId;
        return URL_AUTHORIZE + "?client_id=" + this.clientId
                + "&scope=" + this.scope
                + "&redirect_uri=" + authUrl;
    }

    public String authenticate( String authUrl, String userId, String code ){
        String credentials = Base64.getEncoder().encodeToString( ( this.clientId + ":" + this.clientSecret ).getBytes() );
        Form data = new Form()
                .param( "redirect_uri", authUrl )
                .param( "code", code );

        Access response = client.target( URL_OAUTH_ACCESS )
                .request( MediaType.APPLICATION_JSON_TYPE )
                .header( "Authorization", "Basic " + credentials )
                .post( Entity.entity( data, MediaType.APPLICATION_FORM_URLENCODED_TYPE ), Access.class );

        if( !response.isOk() )
            throw new SlackException( response.getError() );


        // TODO add to db
        // Bot en User refactoren om van zelfde rommel af te stammen voor ID en date
        // Slack ID kan niet PK zijn voor user want als hier geen userId gegeven wordt zijn we gescheten

        return response.getTeamName();
    }

    private String generateScope(){
        String readScope = String.join( ",", READ_SCOPE );
        String writeScope = String.join( ",", WRITE_SCOPE );
        String userScope = System.getenv( "SLACK_READ_SCOPE" );

        if( userScope != null )
            readScope = Arrays.stream( userScope.split( "," ) )
                    .map( s -> s.toLowerCase().trim() )
                    .filter( s -> s.equals( "channels" ) || s.equals( "groups" ) || s.equals( "im" ) || s.equals( "mpim" ) )
                    .map( s -> s + ":history" )
                    .collect( Collectors.joining( "," ) );

        return readScope + "," + writeScope;
    }
}
