public class CommandProcessorDeposit extends CommandProcessor {
    public CommandProcessorDeposit(Bank bank) {
        super(bank);
    }

    public void execute(String[] commandParts) {
        String accountID = commandParts[1];
        double amount = parseStringToDouble(commandParts[2]);
        bank.depositMoneyById(accountID, amount);

    }
    private double parseStringToDouble(String string) {
        try {
            return Double.parseDouble(string);
        } catch (NumberFormatException e) {
            return -1; // Invalid deposit amount
        }
    }
}
