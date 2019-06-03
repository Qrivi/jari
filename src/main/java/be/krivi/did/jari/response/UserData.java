package be.krivi.did.jari.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserData{

    private String user;
    private String scope;
}
