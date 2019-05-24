package be.krivi.did.jari.core.model;

import lombok.EqualsAndHashCode;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Setter
@EqualsAndHashCode
@Entity
@Table( name = "user" )
public class User implements Serializable{

    @Id
    @GeneratedValue( generator = "pk_generator", strategy = GenerationType.TABLE )
    @TableGenerator( name = "pk_generator", allocationSize = 1 )
    @Column( name = "id" )
    private Long id;

    @NotBlank( message = "{NotBlank.User.name}" )
    @Size( max = 50, message = "{Size.User.name}" )
    @Column( name = "name" )
    private String name;

    @NotBlank( message = "{NotBlank.User.slackId}" )
    @Size( max = 8, message = "{Size.User.slackId}" )
    @Column( name = "slack_id" )
    private String slackId;

}
