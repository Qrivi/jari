package be.krivi.did.jari.repository;

import be.krivi.did.jari.model.core.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,Integer>{

    Optional<User> findByHandle( String handle );

    List<User> findAllByTeam( String team );

    Optional<User> findByHandleAndTeam( String handle, String team );
}
