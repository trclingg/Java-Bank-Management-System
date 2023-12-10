package banking;

public class Checking extends Account {
	public Checking(String accountId, double apr) {

		super(accountId, apr);
	}

	@Override
	public String getAccountType() {
		return "checking";
	}

	@Override
	public boolean isValidDeposit(String depositAmount) {
		double amount = Double.parseDouble(depositAmount);
		if (amount >= 0 && amount <= 1000 && numberIsInCorrectDecimalForm(depositAmount)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isWithdrawalTimeValid() {
		return true;
	}

	@Override
	public boolean isWithdrawalAmountValid(String withdrawAmount) {
		double amount = Double.parseDouble(withdrawAmount);
		if (amount >= 0 && amount <= 400.0 && numberIsInCorrectDecimalForm(withdrawAmount)) {
			return true;
		} else {
			return false;
		}
	}
}
