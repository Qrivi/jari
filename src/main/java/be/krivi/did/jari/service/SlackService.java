package be.krivi.did.jari.service;

import be.krivi.did.jari.exception.SlackException;
import be.krivi.did.jari.model.core.Bot;
import be.krivi.did.jari.model.core.User;
import be.krivi.did.jari.model.dto.slack.Access;
import be.krivi.did.jari.response.AuthData;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;
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
        if( userId == null )
            return URL_AUTHORIZE + "?client_id=" + this.clientId
                    + "&scope=" + this.scope
                    + "&redirect_uri=" + authUrl;
        return URL_AUTHORIZE + "?client_id=" + this.clientId
                + "&scope=" + this.scope
                + "&redirect_uri=" + authUrl
                + "&state=" + userId;
    }

    public AuthData authenticate( String authUrl, String userId, String code ){
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

        Optional<User> existingUser = userService.getUser( userId );
        Optional<Bot> existingBot = botService.getBot( response.getBot().getUserId() );
        User user;
        Bot bot;

        if( existingUser.isPresent() )
            user = userService.updateUser( existingUser.get().getId(), userId, response.getTeamId(), response.getAccessToken(), response.getScope() );
        else
            user = userService.addUser( userId, response.getTeamId(), response.getAccessToken(), response.getScope() );

        if( existingBot.isPresent() )
            bot = botService.updateBot( existingBot.get().getId(), response.getBot().getUserId(), response.getTeamId(), response.getBot().getAccessToken() );
        else
            bot = botService.addBot( response.getBot().getUserId(), response.getTeamId(), response.getBot().getAccessToken() );

        return AuthData.builder()
                .message( "Successfully authenticated in team " + response.getTeamName() + " (" + response.getTeamId() + ")!" )
                .scope( response.getScope() )
                .user( user.getAction().toString() )
                .bot( bot.getAction().toString() )
                .build();
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
