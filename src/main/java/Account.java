public abstract class Account {
	double balance;
	private double apr;

	protected Account(double apr) {
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
}
