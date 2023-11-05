public class Checking extends Account {
	public Checking(double apr) {
		super(apr);
	}

	@Override
	public String getAccountType() {
		return "checking";
	}
}
