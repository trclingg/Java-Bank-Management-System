package banking;

public class CommandValidatorTransfer extends CommandValidator {

	public CommandValidatorTransfer(Bank bank) {
		super(bank);

	}

	public Boolean validate(String[] commandParts) {
		try {
			String accountIDFrom = commandParts[1];
			String accountIDTo = commandParts[2];
			String transferAmount = commandParts[3];

			if (commandParts.length != 4 || !accountsAreValid(accountIDFrom, accountIDTo)
					|| !amountTransferIsValid(accountIDFrom, accountIDTo, transferAmount)) {
				return false;
			}
			return true;

		} catch (Exception e) {
			return false;
		}
	}

	private boolean accountsAreValid(String accountIDFrom, String accountIDTo) {
		// Accounts Exist?
		if (!bank.accountIdAlreadyExists(accountIDFrom) || !bank.accountIdAlreadyExists(accountIDTo)) {
			return false;
		}
		// Id valid?
		if (!accountIDValid(accountIDFrom) || !accountIDValid(accountIDTo)) {
			return false;
		}
		// Id the same?
		if (accountIDFrom.equals(accountIDTo)) {
			return false;
		}

		// check account type
		if (bank.getAccountById(accountIDFrom).getAccountType().equals("cd")
				|| bank.getAccountById(accountIDTo).getAccountType().equals("cd")) {
			return false;
		} else if (bank.getAccountById(accountIDFrom).getAccountType()
				.equals(bank.getAccountById(accountIDTo).getAccountType())) {
			return false;
		}
		return true;
	}

	private boolean amountTransferIsValid(String accountIDFrom, String accountIDTo, String transferAmount) {
		Double transferAmountNum = Double.parseDouble(transferAmount);
		if (transferAmountNum >= 0 && bank.getAccountById(accountIDFrom).isWithdrawalAmountValid(transferAmount)
				&& bank.getAccountById(accountIDTo).isValidDeposit(transferAmount)) {
			return true;
		} else {
			return false;
		}
	}
}
