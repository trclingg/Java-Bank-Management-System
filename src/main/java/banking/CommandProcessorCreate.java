package banking;

public class CommandProcessorCreate extends CommandProcessor {
	String accountType;
	String accountID;
	String aprString;
	double apr;
	double balance;

	public CommandProcessorCreate(Bank bank) {
		super(bank);
	}

	public void execute(String[] commandParts) {
		accountType = commandParts[1];
		accountID = commandParts[2];
		aprString = commandParts[3];
		apr = Double.parseDouble(aprString);
		if (accountType.equalsIgnoreCase("checking")) {
			bank.bankCreateCheckingAccount(accountID, apr);
		} else if (accountType.equalsIgnoreCase("savings")) {
			bank.bankCreateSavingsAccount(accountID, apr);
		} else if (accountType.equalsIgnoreCase("cd")) {
			balance = Double.parseDouble(commandParts[4]);
			bank.bankCreateCDAccount(accountID, apr, balance);
		}
	}

}
