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
		return (amount >= 0 && amount <= 2500 && numberIsInCorrectDecimalForm(depositAmount));
	}

	@Override
	public boolean isWithdrawalTimeValid() {
		return (!(withdrawalMadeThisMonth));
	}

	@Override
	public boolean isWithdrawalAmountValid(String withdrawAmount) {
		double amount = Double.parseDouble(withdrawAmount);

		return (amount >= 0 && amount <= 1000.0 && numberIsInCorrectDecimalForm(withdrawAmount));
	}

	public void changeWithdrawalStatus() {
		withdrawalMadeThisMonth = !withdrawalMadeThisMonth;

	}

	@Override
	public void increaseAge(int months) {
		super.increaseAge(months);
		withdrawalMadeThisMonth = false;
	}
}
