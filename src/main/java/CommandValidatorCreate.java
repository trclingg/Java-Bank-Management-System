public class CommandValidatorCreate {
    public boolean validate(String[] commandParts) {
//        if (accountTypeIsValid(commandParts[1])) {
//            return true;
//         }
        // Validation logic specific to "create" command
        if (commandParts.length >= 2) {
            String accountType = commandParts[1].toLowerCase();
            return isValidAccountType(accountType);
        }
        return false; // Invalid format
    }

    private boolean isValidAccountType(String accountType){
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
}
