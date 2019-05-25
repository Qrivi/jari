package be.krivi.did.jari.model.core;

import be.krivi.did.jari.validation.SlackBotToken;
import be.krivi.did.jari.validation.SlackTeamId;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
@Entity
@Table( name = "bot" )
public class Bot{

    @Id
    @SlackTeamId( message = "{SlackTeamId.Bot.id}" )
    @Column( name = "id" )
    private String id; // one bot per team, so team ID is fit to serve as PK

    @SlackBotToken( message = "{SlackBotId.Bot.token}", optional = false )
    @Column( name = "token" )
    private String token;

    @NotNull( message = "{NotNull.Bot.lastEdit}" )
    @Column( name = "last_edit" )
    @Temporal( TemporalType.TIMESTAMP )
    private Date lastEdit;
}
