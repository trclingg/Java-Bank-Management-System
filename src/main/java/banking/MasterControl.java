package banking;

import java.util.List;

public class MasterControl {
	private final CommandProcessor commandProcessor;
	private final CommandValidator commandValidator;
	private final CommandStorage commandStorage;
	private final GetOutput getOutput;

	public MasterControl(CommandValidator commandValidator, CommandProcessor commandProcessor,
			CommandStorage commandStorage, GetOutput getOutput) {
		this.commandProcessor = commandProcessor;
		this.commandStorage = commandStorage;
		this.commandValidator = commandValidator;
		this.getOutput = getOutput;
	}

	public List<String> start(List<String> input) {
		for (String command : input) {
			if (commandValidator.validateCommand(command)) {
				commandProcessor.execute(command);
				commandStorage.addValidCommand(command);
			} else {
				commandStorage.addInvalidCommandToList(command);
			}
		}

		return getOutput.getOutput();
	}
}
