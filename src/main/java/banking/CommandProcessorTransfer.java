package banking;

public class CommandProcessorTransfer extends CommandProcessor {
	public CommandProcessorTransfer(Bank bank) {
		super(bank);
	}

	public void execute(String[] splitString) {
		String accountIdFrom = splitString[1];
		String accountIdTo = splitString[2];
		double transferAmount = Double.parseDouble(splitString[3]);

		bank.transferMoneyBy2Ids(accountIdFrom, accountIdTo, transferAmount);
	}

}
