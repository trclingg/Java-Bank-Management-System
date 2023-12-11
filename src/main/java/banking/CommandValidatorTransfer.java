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

			return (!(commandParts.length != 4 || !accountsAreValid(accountIDFrom, accountIDTo)
					|| !amountTransferIsValid(accountIDFrom, accountIDTo, transferAmount)));

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
		return (!(bank.getAccountById(accountIDFrom).getAccountType().equals("cd")
				|| bank.getAccountById(accountIDTo).getAccountType().equals("cd")));
	}

	private boolean amountTransferIsValid(String accountIDFrom, String accountIDTo, String transferAmount) {
		double transferAmountNum = Double.parseDouble(transferAmount);
		return (transferAmountNum >= 0 && bank.getAccountById(accountIDFrom).isWithdrawalAmountValid(transferAmount)
				&& bank.getAccountById(accountIDTo).isValidDeposit(transferAmount));
	}
}
