public class CommandProcessorCreate extends CommandProcessor {
    private String accountType;
    private String accountID;
    private String aprString;
    private double apr;
    private double balance;
    public CommandProcessorCreate(Bank bank) {
        super(bank);
    }

    public void execute(String[] commandParts) {
        accountType = commandParts[1];
        accountID = commandParts[2];
        aprString = commandParts[3];
        apr = parseStringToDouble(aprString);
        if (accountType.equalsIgnoreCase("checking")) {
            bank.bankCreateCheckingAccount(accountID,apr);
        }
        else if (accountType.equalsIgnoreCase("savings")) {
            bank.bankCreateSavingsAccount(accountID,apr);
        } else if (accountType.equalsIgnoreCase("cd")) {
            balance = parseStringToDouble(commandParts[4]);
            bank.bankCreateCDAccount(accountID, apr, balance);
        }
    }


    private double parseStringToDouble(String string) {
        try {
            return Double.parseDouble(string);
        } catch (NumberFormatException e) {
            return -1; // Invalid deposit amount
        }
    }
}
