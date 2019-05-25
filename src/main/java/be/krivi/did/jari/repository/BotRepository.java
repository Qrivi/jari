package be.krivi.did.jari.repository;

import be.krivi.did.jari.model.core.Bot;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BotRepository extends CrudRepository<Bot,Integer>{

    // Bot findByHandle( String handle );

    // Bot findByTeam( String team );
}
