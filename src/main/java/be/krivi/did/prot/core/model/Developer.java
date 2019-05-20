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
@Table( name = "developer")
public class Developer extends Identifiable {

    @NotBlank( message = "{NotBlank.Developer.name}" )
    @Size( max = 50, message = "{Size.Developer.name}" )
    @Column( name = "name" )
    private String name;

    @NotBlank( message = "{NotBlank.Developer.slackId}" )
    @Size( max = 8, message = "{Size.Developer.slackId}" )
    @Column( name = "slack_id" )
    private String slackId;

}
