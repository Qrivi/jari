package be.krivi.did.prot.common;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.TableGenerator;
import java.util.Objects;

@Getter
@Setter
@MappedSuperclass
public class Identifiable{

    @Id
    @GeneratedValue( strategy = GenerationType.TABLE, generator = "generatorName" )
    @TableGenerator( name = "generatorName", allocationSize = 1 )
    @Column( name = "id" )
    private Integer id;

    @Override
    public boolean equals( Object obj ){
        return !( obj == null || id == null ) && obj instanceof Identifiable && ( (Identifiable)obj ).getId() != null && ( (Identifiable)obj ).getId().equals( this.id );
    }

    @Override
    public int hashCode(){
        int hash = 7;
        hash = 17 * hash + Objects.hashCode( this.id );
        return hash;
    }
}