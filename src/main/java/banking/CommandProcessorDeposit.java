package banking;

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

            return Double.parseDouble(string);
    }
}
