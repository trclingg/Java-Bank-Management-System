package banking;

public class CertificateDeposit extends Account {
	public CertificateDeposit(String accountId, double apr, double balance) {
		super(accountId, apr);
		this.balance = balance;
	}

	@Override
	public String getAccountType() {
		return "cd";
	}

	@Override
	public boolean isValidDeposit(String depositAmount) {
		return false;
	}

	@Override
	public boolean isWithdrawalTimeValid() {
		if (super.getAge() >= 12) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isWithdrawalAmountValid(String withdrawAmount) {
		double amount = Double.parseDouble(withdrawAmount);
		if (amount >= super.getBalance() && numberIsInCorrectDecimalForm(withdrawAmount)) {
			return true;
		}
		return false;
	}
}
