package banking;

public abstract class Account {
	private final double apr;
	private final String accountId;
	double balance;
	private int age = 0;

	protected Account(String accountId, double apr) {
		this.accountId = accountId;
		this.apr = apr;
	}

	public double getBalance() {
		return balance;
	}

	public double getApr() {
		return apr;
	}

	public void depositMoney(double depositAmount) {
		balance += depositAmount;
	}

	public void withdrawMoney(double withdrawalAmount) {
		if (balance <= withdrawalAmount) {
			balance = 0;
		} else {
			balance -= withdrawalAmount;
		}
	}

	public abstract String getAccountType();

	public abstract boolean isValidDeposit(String depositAmount);

	public String getAccountID() {
		return accountId;
	}

	public abstract boolean isWithdrawalTimeValid();

	public abstract boolean isWithdrawalAmountValid(String withdrawAmount);

	public void increaseAge(int months) {
		this.age += months;
	}

	public int getAge() {
		return age;
	}

	public boolean numberIsInCorrectDecimalForm(String testNumber) {
		String[] parts = testNumber.split("\\.");
		return (parts.length <= 2 && (parts.length == 1 || parts[1].length() <= 2));

	}

	public void calculateAPR() {
		double calculation = (apr / 100) / 12 * balance;
		balance = calculation + balance;
	}

}
