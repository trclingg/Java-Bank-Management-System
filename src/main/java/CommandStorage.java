import java.util.ArrayList;
import java.util.List;


public class CommandStorage {
    private final List<String> invalidCommands = new ArrayList<>();
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
}
