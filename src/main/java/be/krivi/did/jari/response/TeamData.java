package be.krivi.did.jari.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class TeamData implements ResponseData{

    private String bot;
    private List<UserData> users;
}
