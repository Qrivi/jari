package be.krivi.did.jari.exception;

public class SlackException extends RuntimeException{

    private String error;

    public SlackException( String error ){
        super();
        this.error = error;
    }

    public String getMessageKey(){
        return "Slack." + this.error;
    }
}