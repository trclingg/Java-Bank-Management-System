package banking;

public class CommandProcessorPassTime extends CommandProcessor {
	public CommandProcessorPassTime(Bank bank) {
		super(bank);
	}

	public void execute(String[] inputString) {
		int months = Integer.parseInt(inputString[1]);
		bank.passTime(months);
	}
}
