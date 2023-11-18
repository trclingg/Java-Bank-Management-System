package banking;

public class CertificateDeposit extends Account {
	public CertificateDeposit( String accountId,double apr, double balance) {
		super(accountId, apr);
		this.balance = balance;
	}

	@Override
	public String getAccountType() {
		return "cd";
	}
}
