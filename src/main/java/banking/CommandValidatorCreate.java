package banking;

public class CommandValidatorCreate extends CommandValidator {

	public CommandValidatorCreate(Bank bank) {
		super(bank);
	}

	public boolean validate(String[] commandParts) {
		try {
			String accountType = commandParts[1].toLowerCase();
			String accountId = commandParts[2];
			String aprValue = commandParts[3];
			String initialAmount = commandParts.length == 5 ? commandParts[4] : null;

			if (!isValidAccountType(accountType) || !accountIDValid(accountId) || bank.accountIdAlreadyExists(accountId)
					|| !isValidAPR(aprValue)) {
				return false;
			}

			if (accountType.equals("cd")) {
				return (commandParts.length == 5 && isValidInputAmountCheckCd(initialAmount));
			}
			return (commandParts.length == 4);
		} catch (Exception e) {
			return false;

		}

	}

	private boolean isValidAPR(String aprValue) {
		try {
			double apr = Double.parseDouble(aprValue);

			if (apr >= 0 && apr <= 10) {

				return true;

			}
		} catch (NumberFormatException e) {
			return false;
		}
		return false; // other reasons

	}

	private boolean isValidInputAmountCheckCd(String initialAmountValue) {
		double initialAmount = Double.parseDouble(initialAmountValue);

		if (initialAmount >= 1000 && initialAmount <= 10000) {
			String[] parts = initialAmountValue.split("\\.");
			return (parts.length <= 2 && (parts.length == 1 || parts[1].length() <= 2));
		}
		return false;
	}
}
