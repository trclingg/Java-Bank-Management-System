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
		if (amount >= 0 && amount <= 2500 && numberIsInCorrectDecimalForm(depositAmount)) {

			return true;

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

		if (amount >= 0 && amount <= 1000.0 && numberIsInCorrectDecimalForm(withdrawAmount)) {
			return true;
		} else {
			return false;
		}
	}

	public void changeWithdrawalStatus() {
		withdrawalMadeThisMonth = !withdrawalMadeThisMonth;
		System.out.println("made:" + withdrawalMadeThisMonth);

	}

	@Override
	public void increaseAge(int months) {
		super.increaseAge(months);
		withdrawalMadeThisMonth = false;
	}
}
