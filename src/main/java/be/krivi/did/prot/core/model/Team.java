package be.krivi.did.prot.core.model;

import be.krivi.did.prot.common.Identifiable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table( name = "team")
public class Team extends Identifiable {

    @NotBlank( message = "{NotBlank.Team.name}" )
    @Size( max = 50, message = "{Size.Team.name}" )
    @Column( name = "name" )
    private String name;

    @NotBlank( message = "{NotBlank.Team.slackId}" )
    @Size( max = 8, message = "{Size.Team.slackId}" )
    @Column( name = "slack_id" )
    private String slackId;

}
