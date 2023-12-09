package banking;

public class Savings extends Account {
	private boolean withdrawalMadeThisMonth = false;

	public Savings(String accountId, double apr) {
		super(accountId, apr);
	}

	@Override
	public String getAccountType() {
		return "savings";
	}

	@Override
	public boolean isValidDeposit(String depositAmount) {
		double amount = Double.parseDouble(depositAmount);
		if (amount >= 0 && amount <= 2500) {
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
		if (withdrawalMadeThisMonth == true) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isWithdrawalAmountValid(String withdrawAmount) {
		double amount = Double.parseDouble(withdrawAmount);
		if (amount >= 0 && amount <= 1000.0) {
			return true;
		} else {
			return false;
		}
	}
}
