package be.krivi.did.jari.slack;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class SlackService{

    private static final String AUTH_URL = "https://slack.com/oauth/authorize";
    private static final String ACCESS_URL = "https://slack.com/api/oauth.access";
    private static final String[] READ_SCOPE = {
            "channels:history",
            "groups:history",
            "im:history",
            "mpim:history"
    };
    private static final String[] WRITE_SCOPE = {
            "chat:write:user",
            "chat:write:bot"
    };

    private String clientId;
    private String clientSecret;
    private String scope;
    private String state;

    public SlackService(){
        this.clientId = System.getenv( "SLACK_CLIENT_ID" );
        this.clientSecret = System.getenv( "SLACK_CLIENT_SECRET" );
        this.scope = this.generateScope();
        this.state = RandomStringUtils.randomAlphanumeric( 20 );
    }

    public String getAuthorizeUrl( String redirectUri ){
        return AUTH_URL + "?client_id=" + this.clientId
                + "&scope=" + this.scope
                + "&state=" + this.state
                + "&redirect_uri=" + redirectUri;
    }

    public String getVerificationUrl( String redirectUri, String code ){
        return ACCESS_URL + "?client_id=" + this.clientId
                + "?client_secret=" + this.clientSecret
                + "&code=" + code
                + "&redirect_uri=" + redirectUri;
    }

    public boolean verifyState( String state ){
        return this.state.equals( state );
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
