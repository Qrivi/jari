package be.krivi.did.jari.repository;

import be.krivi.did.jari.model.core.Bot;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BotRepository extends CrudRepository<Bot,Integer>{

    Optional<Bot> findByHandle( String handle );

    Optional<Bot> findByTeam( String team );

    Optional<Bot> findByHandleAndTeam( String handle, String team );
}
