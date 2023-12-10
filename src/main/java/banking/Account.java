package banking;

public abstract class Account {
	double balance;
	private double apr;

	private String accountId;
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

}
