package be.krivi.did.jari.repository;

import be.krivi.did.jari.model.core.Bot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BotRepository extends JpaRepository<Bot, Integer>{}
