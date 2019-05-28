package be.krivi.did.jari.service;

import be.krivi.did.jari.exception.DuplicateUserException;
import be.krivi.did.jari.model.IdentifiableAction;
import be.krivi.did.jari.model.core.User;
import be.krivi.did.jari.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService{

    private UserRepository repository;

    public UserService( UserRepository repository ){
        this.repository = repository;
    }

    public Optional<User> getUser( int id ){
        return repository.findById( id );
    }

    public Optional<User> getUser( String handle ){
        return repository.findByHandle( handle );
    }

    public List<User> getUsersOfTeam( String team ){
        return repository.findAllByTeam( team );
    }

    public User addUser( String handle, String team, String token, String scope ){
        if( repository.findByHandleAndTeam( handle, team ).isPresent() )
            throw new DuplicateUserException( "User already exists" );

        User user = new User();
        user.setHandle( handle );
        user.setTeam( team );
        user.setToken( token );
        user.setScope( scope );

        user.setLastEdit( new Date() );
        user.setAction( IdentifiableAction.ADDED );

        return repository.save( user );
    }

    public User updateUser( int id, String handle, String team, String token, String scope ){
        User user = this.getUser( id ).orElseThrow( () -> new NullPointerException( "User does not exist" ) );
        Optional<User> duplicate = repository.findByHandleAndTeam( handle, team );
        if( duplicate.isPresent() && !duplicate.get().getId().equals( id ) )
            throw new DuplicateUserException( "User already exists" );

        if( handle != null )
            user.setHandle( handle );
        if( team != null )
            user.setTeam( team );
        if( token != null )
            user.setToken( token );
        if( scope != null )
            user.setScope( scope );

        user.setLastEdit( new Date() );
        user.setAction( IdentifiableAction.UPDATED );

        return repository.save( user );
    }
}
