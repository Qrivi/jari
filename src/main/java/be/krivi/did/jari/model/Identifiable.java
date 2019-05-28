package be.krivi.did.jari.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@MappedSuperclass
public class Identifiable{

    @Id
    @GeneratedValue( strategy = GenerationType.TABLE, generator = "pk_generator" )
    @TableGenerator( name = "pk_generator", allocationSize = 1 )
    @Column( name = "id" )
    private Integer id;

    @NotNull( message = "{NotNull.Identifiable.lastEdit}" )
    @Column( name = "last_edit", nullable = false )
    @Temporal( TemporalType.TIMESTAMP )
    private Date lastEdit;

    private IdentifiableAction action;

    @Override
    public boolean equals( Object obj ){
        return !( obj == null || id == null ) && obj instanceof Identifiable && ( (Identifiable) obj ).getId() != null && ( (Identifiable) obj ).getId().equals( this.id );
    }

    @Override
    public int hashCode(){
        int hash = 7;
        hash = 17 * hash + Objects.hashCode( this.id );
        return hash;
    }
}
