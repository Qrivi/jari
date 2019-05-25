package be.krivi.did.jari.service;

import be.krivi.did.jari.repository.BotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BotService{

    private BotRepository repository;

    public BotService( BotRepository repository ){
        this.repository = repository;
    }
}
