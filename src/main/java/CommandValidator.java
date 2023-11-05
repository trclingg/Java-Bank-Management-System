//import org.w3c.dom.UserDataHandler;

public class CommandValidator {
    private boolean accountTypeIsValid(String accountType){
        if (accountType.equalsIgnoreCase("checking")) {
            return true;
        }
        else if (accountType.equalsIgnoreCase("savings")) {
            return true;
        }
        else if (accountType.equalsIgnoreCase("cd")) {
            return true;
        }
        return false;
    }
    public boolean validateCommand(String string) {
        String[] command = string.split(" ");
        if (accountTypeIsValid(command[1])) {
            return true;
         }   else {
            return false;
        }
}}

