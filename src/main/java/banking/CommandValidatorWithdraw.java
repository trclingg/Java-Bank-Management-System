package banking;

public class CommandValidatorWithdraw extends CommandValidator {
	public CommandValidatorWithdraw(Bank bank) {
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
//			System.out.println(account.isWithdrawalAmountValid(withdrawAmount));
//			System.out.println(account.isWithdrawalTimeValid());

			return (account.isWithdrawalAmountValid(withdrawAmount) && account.isWithdrawalTimeValid());

		} catch (Exception e) {

			return false;
		}
	}
}
