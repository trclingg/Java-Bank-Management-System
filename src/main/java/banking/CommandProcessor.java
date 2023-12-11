package banking;

public class CommandProcessor {
	public Bank bank;

	public CommandProcessor(Bank bank) {
		this.bank = bank;
	}

	public void execute(String commandString) {
		String[] commandParts = commandString.split(" ");
		String action = commandParts[0].toLowerCase();

		if ("create".equals(action)) {
			CommandProcessorCreate commandProcessorCreate = new CommandProcessorCreate(bank);
			commandProcessorCreate.execute(commandParts);
		} else if ("deposit".equals(action)) {
			CommandProcessorDeposit commandProcessorDeposit = new CommandProcessorDeposit(bank);
			commandProcessorDeposit.execute(commandParts);
		}

		else if ("withdraw".equals(action)) {
			CommandProcessorWithdraw commandProcessorWithdraw = new CommandProcessorWithdraw(bank);
			commandProcessorWithdraw.execute(commandParts);
		}

		else if ("pass".equals(action)) {
			CommandProcessorPassTime commandProcessorPassTime = new CommandProcessorPassTime(bank);
			commandProcessorPassTime.execute(commandParts);
		}

		else if ("transfer".equals(action)) {
			CommandProcessorTransfer commandProcessorTransfer = new CommandProcessorTransfer(bank);
			commandProcessorTransfer.execute(commandParts);
		}
	}

}
