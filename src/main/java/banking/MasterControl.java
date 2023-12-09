package banking;

import java.util.List;
public class MasterControl {
    private CommandProcessor commandProcessor;
    private CommandValidator commandValidator;
    private CommandStorage commandStorage;

    public MasterControl(CommandValidator commandValidator, CommandProcessor commandProcessor,CommandStorage commandStorage) {
        this.commandProcessor = commandProcessor;
        this.commandStorage = commandStorage;
        this.commandValidator = commandValidator;
    }

    public List<String> start(List<String> input) {
        for (String command : input) {
            if (commandValidator.validateCommand(command)) {
                commandProcessor.execute(command);
            } else {
                commandStorage.addInvalidCommandToList(command);
            }
        }

        return commandStorage.getInvalidCommands();
    }
}
