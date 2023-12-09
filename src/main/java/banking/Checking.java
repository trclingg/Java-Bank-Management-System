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
		if (amount >= 0 && amount <= 1000) {
			String amountStr = Double.toString(amount);
			String[] parts = amountStr.split("\\.");
			if (parts.length <= 2 && (parts.length == 1 || parts[1].length() <= 2)) {
				return true;
			}

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
		if (amount >= 0 && amount <= 400.0) {
			return true;
		} else {
			return false;
		}
	}
}
