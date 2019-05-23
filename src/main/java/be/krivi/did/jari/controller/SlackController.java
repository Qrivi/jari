package be.krivi.did.jari.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping(
        path = "/slack",
       // consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE )
public class SlackController{

    @RequestMapping(method = RequestMethod.GET)
    public String greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return "Hello " + name;
    }

    @RequestMapping(path = "/auth", method = RequestMethod.GET)
    public RedirectView auth() {
        RedirectView redirect = new RedirectView();
        String clientId = System.getenv("SLACK_CLIENT_ID");
        redirect.setUrl("https://slack.com/oauth/authorize");
        return redirect;
    }
}
