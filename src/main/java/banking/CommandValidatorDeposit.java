package banking;

public class CommandValidatorDeposit extends CommandValidator {
	public CommandValidatorDeposit(Bank bank) {
		super(bank);
	}

	public boolean validate(String[] commandParts) {
		try {
			String accountId = commandParts[1];
			String withdrawAmount = commandParts[2];

			if (!accountIDValid(accountId) || !bank.accountIdAlreadyExists(accountId) || commandParts.length != 3) {
				return false; // Invalid account ID
			}
			Account account = bank.getAccounts().get(accountId);
			return account.isValidDeposit(withdrawAmount);

		} catch (Exception e) {
			return false;
		}
	}

}
