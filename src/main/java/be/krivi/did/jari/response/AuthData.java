package be.krivi.did.jari.response;

import lombok.Builder;

@Builder
public class AuthData implements ResponseData{

    private String message;
    private String scope;
    private String user;
    private String bot;
}
