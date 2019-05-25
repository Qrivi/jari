package be.krivi.did.jari.service;

import be.krivi.did.jari.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService{

    private UserRepository repository;

    public UserService(UserRepository repository){
        this.repository = repository;
    }
}
