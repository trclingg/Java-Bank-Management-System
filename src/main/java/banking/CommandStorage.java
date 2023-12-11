package banking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandStorage {
	private final List<String> invalidCommands = new ArrayList<>();
	private final Map<String, List<String>> validCommands = new HashMap<>();
	protected Bank bank;

	public CommandStorage(Bank bank) {
		this.bank = bank;
	}

	public void addInvalidCommandToList(String invalidCommand) {
		invalidCommands.add(invalidCommand);
	}

	public List<String> getInvalidCommands() {
		return invalidCommands;
	}

	public Map<String, List<String>> getValidCommands() {
		return validCommands;
	}

	public void addValidCommand(String inputString) {
		String[] splitString = inputString.split(" ");
		String commandType = splitString[0];
		String formattedValidCommand = formatValidCommand(splitString);
		if (commandType.equalsIgnoreCase("withdraw") || commandType.equalsIgnoreCase("deposit")) {
			insertToValidCommandHashMap(validCommands, splitString[1], formattedValidCommand);
		} else if (commandType.equalsIgnoreCase("transfer")) {
			insertToValidCommandHashMap(validCommands, splitString[1], formattedValidCommand);
			insertToValidCommandHashMap(validCommands, splitString[2], formattedValidCommand);
		} else if (commandType.equalsIgnoreCase("create") && (validCommands.containsKey(splitString[2]))) {
			validCommands.remove(splitString[2]);
		}
	}

	private void insertToValidCommandHashMap(Map<String, List<String>> validCommands, String accountID,
			String command) {
		if (validCommands.containsKey(accountID)) {
			validCommands.get(accountID).add(command);
		} else {
			validCommands.put(accountID, new ArrayList<>());
			validCommands.get(accountID).add(command);
		}
	}

	protected String formatValidCommand(String[] validCommand) {
		String commandType = validCommand[0];
		String formattedCommandType = commandType.substring(0, 1).toUpperCase()
				+ commandType.substring(1).toLowerCase();

		StringBuilder formattedString = new StringBuilder(formattedCommandType);
		for (int i = 1; i < validCommand.length; i++) {
			formattedString.append(" ").append(validCommand[i]);
		}
		return formattedString.toString();
	}
}
