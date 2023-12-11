package banking;

public class CommandValidator {
	public Bank bank;

	public CommandValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean accountIDValid(String idString) {
		return (idString.matches("\\d+") && idString.length() == 8);
	}

	public boolean isValidAccountType(String accountType) {
		return (accountType.equalsIgnoreCase("checking") || accountType.equalsIgnoreCase("savings")
				|| (accountType.equalsIgnoreCase("cd")));
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
		} else if ("deposit".equals(action)) {
			CommandValidatorDeposit depositValidator = new CommandValidatorDeposit(bank);
			return depositValidator.validate(commandParts);
		}

		else if ("withdraw".equals(action)) {
			CommandValidatorWithdraw withdrawValidator = new CommandValidatorWithdraw(bank);
			return withdrawValidator.validate(commandParts);
		}

		else if ("pass".equals(action)) {
			CommandValidatorPassTime passTimeValidator = new CommandValidatorPassTime(bank);
			return passTimeValidator.validate(commandParts);
		}

		else if ("transfer".equals(action)) {
			CommandValidatorTransfer transferValidator = new CommandValidatorTransfer(bank);
			return transferValidator.validate(commandParts);
		}

		return false;
	}
}
