package be.krivi.did.jari.model.core;

import be.krivi.did.jari.model.Identifiable;
import be.krivi.did.jari.validation.SlackBotToken;
import be.krivi.did.jari.validation.SlackTeamId;
import be.krivi.did.jari.validation.SlackUserId;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table( name = "jari_bot" )
public class Bot extends Identifiable{

    @SlackUserId( message = "{SlackUserId.Bot.handle}" )
    @Column( name = "slack_id" )
    private String handle;

    @SlackTeamId( message = "{SlackTeamId.Bot.team}" )
    @Column( name = "team_id" )
    private String team;

    @SlackBotToken( message = "{SlackBotToken.Bot.token}" )
    @Column( name = "token", nullable = false )
    private String token;
}
