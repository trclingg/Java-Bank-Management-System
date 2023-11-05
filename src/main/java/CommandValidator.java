//import org.w3c.dom.UserDataHandler;

public class CommandValidator {

//    public boolean validateCommand(String string) {
//        String[] command = string.split(" ");
//        if (accountTypeIsValid(command[1])) {
//            return true;
//         }
//
//        return false;
//
//}
public boolean validateCommand(String string) {
    String[] commandParts = string.split(" ");
    if (commandParts.length < 1) {
        return false;
    }
    String action = commandParts[0].toLowerCase();
    if ("create".equals(action)) {
        CommandValidatorCreate createValidator = new CommandValidatorCreate();
        return createValidator.validate(commandParts);
    } else if ("deposit".equals(action)) {
        CommandValidatorDeposit depositValidator = new CommandValidatorDeposit();
//        return depositValidator.validate(commandParts);
    } else if ("withdraw".equals(action)) {
        CommandValidatorWithdraw withdrawValidator = new CommandValidatorWithdraw();
//        return withdrawValidator.validate(commandParts);
    }

    return false;
}
}

