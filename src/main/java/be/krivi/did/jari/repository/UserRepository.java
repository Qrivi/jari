package be.krivi.did.jari.repository;

import be.krivi.did.jari.model.core.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Integer>{

    // User findByHandle( String handle );

    // List<User> findAllByTeam( String team );
}
