package be.krivi.did.jari.model.dto.slack;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

// POJO container for oauth.access
@Getter
@JsonIgnoreProperties( ignoreUnknown = true )
public class Access{

    private boolean ok;
    private String error;

    private String scope;
    @JsonProperty( "access_token" )
    private String accessToken;
    @JsonProperty( "team_id" )
    private String teamId;
    @JsonProperty( "team_name" )
    private String teamName;

    private Bot bot;

    private Access(){
        // nop
    }

    @Getter
    public class Bot{
        @JsonProperty( "bot_user_id" )
        private String userId;
        @JsonProperty( "bot_access_token" )
        private String accessToken;
    }
}
