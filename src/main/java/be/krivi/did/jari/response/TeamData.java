package be.krivi.did.jari.response;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public class TeamData implements ResponseData{

    private String bot;
    private List<UserData> users;
}
