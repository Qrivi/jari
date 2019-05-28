package be.krivi.did.jari.response;

import lombok.Builder;

import java.util.List;

@Builder
public class UserData extends ResponseData{

    private String bot;
    private List<User> users;

    @Builder
    public class User{
        private String user;
        private String scope;
    }
}
