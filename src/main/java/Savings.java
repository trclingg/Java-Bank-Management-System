public class Savings extends Account {
	public Savings(double apr) {
		super(apr);
	}

	@Override
	public String getAccountType() {
		return "savings";
	}
}
