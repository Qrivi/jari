package be.krivi.did.jari.controller;

import be.krivi.did.jari.model.core.Bot;
import be.krivi.did.jari.response.Response;
import be.krivi.did.jari.response.TeamData;
import be.krivi.did.jari.response.UserData;
import be.krivi.did.jari.service.BotService;
import be.krivi.did.jari.service.UserService;
import be.krivi.did.jari.validation.SlackTeamId;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(
        path = "/users",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE )
public class UserController{

    private UserService userService;
    private BotService botService;

    public UserController( UserService userService, BotService botService ){
        this.userService = userService;
        this.botService = botService;
    }

    @RequestMapping( method = RequestMethod.GET )
    public ResponseEntity<Response> get( @RequestParam( value = "team", defaultValue = "" ) @SlackTeamId String team ){
        Optional<Bot> botOfTeam = botService.getBotOfTeam( team );

        if( botOfTeam.isPresent() ){
            TeamData data = TeamData.builder()
                    .bot( botOfTeam.get()
                            .getHandle() )
                    .users( userService.getUsersOfTeam( team ).stream()
                            .map( u -> UserData.builder()
                                    .user( u.getHandle() )
                                    .scope( u.getScope() )
                                    .build() )
                            .collect( Collectors.toList() ) )
                    .build();

            return new ResponseEntity<>( Response.good( data ), HttpStatus.OK );
        }
        return new ResponseEntity<>( Response.bad( "Unknown team." ), HttpStatus.NOT_FOUND );
    }
}