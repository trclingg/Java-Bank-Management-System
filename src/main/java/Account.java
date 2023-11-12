public abstract class Account {
	double balance;
	private double apr;

	private String accountId;
	protected Account( String accountId ,double apr) {
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
		if (balance < withdrawalAmount) {
			balance = 0;
		} else {
			balance -= withdrawalAmount;
		}
	}
	public abstract String getAccountType();

	public String getAccountID() {
		return accountId;
	}
	
}

