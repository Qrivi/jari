package be.krivi.did.jari.service;

import be.krivi.did.jari.exception.DuplicateBotException;
import be.krivi.did.jari.model.IdentifiableAction;
import be.krivi.did.jari.model.core.Bot;
import be.krivi.did.jari.repository.BotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class BotService{

    private BotRepository repository;

    public BotService( BotRepository repository ){
        this.repository = repository;
    }

    public Optional<Bot> getBot( int id ){
        return repository.findById( id );
    }

    public Optional<Bot> getBot( String handle ){
        return repository.findByHandle( handle );
    }

    public Optional<Bot> getBotOfTeam( String team ){
        return repository.findByTeam( team );
    }

    public Bot addBot( String handle, String team, String token ){
        if( repository.findByHandleAndTeam( handle, team ).isPresent() )
            throw new DuplicateBotException( "Bot already exists" );

        Bot bot = new Bot();
        bot.setHandle( handle );
        bot.setTeam( team );
        bot.setToken( token );

        bot.setLastEdit( new Date() );
        bot.setAction( IdentifiableAction.ADDED );

        return repository.save( bot );
    }

    public Bot updateBot( int id, String handle, String team, String token ){
        Bot bot = this.getBot( id ).orElseThrow( () -> new NullPointerException( "Bot does not exist" ) );
        Optional<Bot> duplicate = repository.findByHandleAndTeam( handle, team );
        if( duplicate.isPresent() && !duplicate.get().getId().equals( id ) )
            throw new DuplicateBotException( "Bot already exists" );

        if( handle != null )
            bot.setHandle( handle );
        if( team != null )
            bot.setTeam( team );
        if( token != null )
            bot.setToken( token );

        bot.setLastEdit( new Date() );
        bot.setAction( IdentifiableAction.UPDATED );

        return repository.save( bot );
    }
}
