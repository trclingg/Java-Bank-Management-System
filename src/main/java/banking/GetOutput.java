package banking;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class GetOutput {
	private final Bank bank;
	private final CommandStorage commandStorage;

	public GetOutput(Bank bank, CommandStorage commandStorage) {
		this.bank = bank;
		this.commandStorage = commandStorage;
	}

	protected String getAccountCurrentState(String accountID) {
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		decimalFormat.setRoundingMode(RoundingMode.FLOOR);

		Account account = bank.getAccountById(accountID);
		double balance = account.getBalance();
		double apr = account.getApr();
		String formattedBalance = decimalFormat.format(balance);
		String formattedAPR = decimalFormat.format(apr);
		String commandType = account.getAccountType();
		String formattedCommandType = commandType.substring(0, 1).toUpperCase()
				+ commandType.substring(1).toLowerCase();
		return formattedCommandType + " " + account.getAccountID() + " " + formattedBalance + " " + formattedAPR;
	}

	public List<String> getOutput() {
		List<String> output = new ArrayList<>();
		for (String accountID : bank.getAccountOrder()) {
			output.add(getAccountCurrentState(accountID));
			if (commandStorage.getValidCommands().get(accountID) != null) {
				output.addAll(commandStorage.getValidCommands().get(accountID));
			}
		}
		output.addAll(commandStorage.getInvalidCommands());
		return output;
	}

}
