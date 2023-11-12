public class Checking extends Account {
	public Checking( String accountId, double apr) {

		super(accountId,apr);
	}

	@Override
	public String getAccountType() {
		return "checking";
	}
}
