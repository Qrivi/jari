package be.krivi.did.jari.model.core;

import be.krivi.did.jari.validation.SlackUserId;
import be.krivi.did.jari.validation.SlackUserToken;
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
@Table( name = "user" )
public class User{

    @Id
    @SlackUserId( message = "{SlackUserId.User.id}" )
    @Column( name = "id" )
    private String id;

    @SlackUserToken( message = "{SlackUserToken.User.token}", optional = true )
    @Column( name = "token" )
    private String token;

    @NotNull( message = "{NotNull.User.lastEdit}" )
    @Column( name = "last_edit" )
    @Temporal( TemporalType.TIMESTAMP )
    private Date lastEdit;
}
