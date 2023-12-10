package banking;

public class CommandValidatorPassTime extends CommandValidator {

	public CommandValidatorPassTime(Bank bank) {
		super(bank);

	}

	public boolean validate(String[] commandParts) {
		try {
			String passMonth = commandParts[1];

			if (commandParts.length != 2 || IsPassTimeValid(passMonth)) {
				return false;
			}
			return true;

		} catch (Exception e) {
			return false;
		}
	}

	private boolean IsPassTimeValid(String passMonth) {
		int numPassMonth = Integer.parseInt(passMonth);
		if (numPassMonth > 0 && numPassMonth <= 60) {
			return true;
		}
		return false;
	}
}
