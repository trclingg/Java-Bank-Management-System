public class CommandProcessor {
    public Bank bank;
    private String commandChoiceType;

    public CommandProcessor(Bank bank) {
        this.bank=bank;
    }

    public void execute(String commandString) {
        String[] commandParts = commandString.split(" ");
        String action = commandParts[0].toLowerCase();
        if ("create".equals(action)) {
            CommandProcessorCreate commandProcessorCreate = new CommandProcessorCreate(bank);
            commandProcessorCreate.execute(commandParts);
        }
        else if ("deposit".equals(action)) {
            CommandProcessorDeposit commandProcessorDeposit = new CommandProcessorDeposit(bank);
            commandProcessorDeposit.execute(commandParts);
        }
    }


}
