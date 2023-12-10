package banking;

public class CommandProcessorDeposit extends CommandProcessor {
	public CommandProcessorDeposit(Bank bank) {
		super(bank);
	}

	public void execute(String[] commandParts) {
		String accountID = commandParts[1];
		double amount = Double.parseDouble(commandParts[2]);
		bank.depositMoneyById(accountID, amount);

	}

}
