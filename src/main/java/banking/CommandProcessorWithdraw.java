package banking;

public class CommandProcessorWithdraw extends CommandProcessor {
	public CommandProcessorWithdraw(Bank bank) {
		super(bank);
	}

	public void execute(String[] splitString) {
		String accountID = splitString[1];
		double withdrawAmount = Double.parseDouble(splitString[2]);
		bank.withdrawMoneyById(accountID, withdrawAmount);
	}
}
