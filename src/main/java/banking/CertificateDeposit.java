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
		return (super.getAge() >= 12);
	}

	@Override
	public boolean isWithdrawalAmountValid(String withdrawAmount) {
		double amount = Double.parseDouble(withdrawAmount);

		return (amount >= super.getBalance());
	}

	@Override
	public void calculateAPR() {
		for (int counter = 0; counter < 4; counter++) {
			super.calculateAPR();
		}
	}
}
