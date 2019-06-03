package be.krivi.did.jari.response;

import lombok.Builder;

import java.util.List;

@Builder
public class UserData{

    private String user;
    private String scope;
}
