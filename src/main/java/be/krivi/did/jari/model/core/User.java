package be.krivi.did.jari.model.core;

import be.krivi.did.jari.model.Identifiable;
import be.krivi.did.jari.validation.SlackTeamId;
import be.krivi.did.jari.validation.SlackUserId;
import be.krivi.did.jari.validation.SlackUserToken;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table( name = "jari_user" )
public class User extends Identifiable{

    @SlackUserId( message = "{SlackUserId.User.handle}", allowNull = true )
    @Column( name = "slack_id" )
    private String handle;

    @SlackTeamId( message = "{SlackTeamId.User.team}" )
    @Column( name = "team_id" )
    private String team;

    @SlackUserToken( message = "{SlackUserToken.User.token}" )
    @Column( name = "token", nullable = false )
    private String token;

    @NotNull( message = "{NotNull.User.scope}" )
    @Column( name = "scope", nullable = false )
    private String scope;
}
