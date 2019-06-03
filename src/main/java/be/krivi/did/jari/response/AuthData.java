package be.krivi.did.jari.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthData implements ResponseData{

    private String message;
    private String scope;
    private String user;
    private String bot;
}
