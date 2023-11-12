public class MasterControl {
    private CommandProcessor commandProcessor;
    private CommandValidator commandValidator;
    private CommandStorage commandStorage;

    public MasterControl(CommandValidator commandValidator, CommandProcessor commandProcessor,CommandStorage commandStorage) {
        this.commandProcessor = commandProcessor;
        this.commandStorage = commandStorage;
        this.commandValidator = commandValidator;
    }
}
