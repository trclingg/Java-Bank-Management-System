
public class CommandValidator {
    public Bank bank;

    public CommandValidator(Bank bank){
        this.bank=bank;
    }
    public boolean accountIDValid(String idString) {
        return (idString.matches("\\d+") && idString.length() == 8);
    }
    public boolean validateCommand(String string) {
        String[] commandParts = string.split(" ");
        if (commandParts.length < 1) {
            return false;
        }
        String action = commandParts[0].toLowerCase();
        if ("create".equals(action)) {
            CommandValidatorCreate createValidator = new CommandValidatorCreate(bank);
            return createValidator.validate(commandParts);
        }
        else if ("deposit".equals(action)) {
            CommandValidatorDeposit depositValidator = new CommandValidatorDeposit(bank);
            return depositValidator.validate(commandParts);
        }

        return false;
    }
}
