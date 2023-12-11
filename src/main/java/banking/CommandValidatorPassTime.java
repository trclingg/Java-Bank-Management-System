package banking;

public class CommandValidatorPassTime extends CommandValidator {

	public CommandValidatorPassTime(Bank bank) {
		super(bank);

	}

	public boolean validate(String[] commandParts) {
		try {
			String passMonth = commandParts[1];

			return (!(commandParts.length != 2 || !IsPassTimeValid(passMonth)));

		} catch (Exception e) {
			return false;
		}
	}

	private boolean IsPassTimeValid(String passMonth) {
		int numPassMonth = Integer.parseInt(passMonth);
		return (numPassMonth > 0 && numPassMonth <= 60);
	}
}
