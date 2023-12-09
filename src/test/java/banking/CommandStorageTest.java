package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class CommandStorageTest {
    private final String INVALID_COMMAND = "foobar blah blah";
    private final String INVALID_COMMAND_2 = "create create";

    public CommandStorage commandStorage;
    Bank bank;
    @BeforeEach
    void setup() {
        bank = new Bank();
        commandStorage = new CommandStorage(bank);
    }

    @Test
    void invalid_command_list_is_initially_empty() {
        int actual = commandStorage.getInvalidCommands().size();
        assertEquals(0, actual);
    }

    @Test
    void add_one_invalid_command_to_list() {
        commandStorage.addInvalidCommandToList(INVALID_COMMAND);
        String actual = commandStorage.getInvalidCommands().get(0);
        assertEquals(INVALID_COMMAND, actual);
    }

    @Test
    void add_two_invalid_command_to_list() {
        commandStorage.addInvalidCommandToList(INVALID_COMMAND);
        commandStorage.addInvalidCommandToList(INVALID_COMMAND_2);
        String actual1 = commandStorage.getInvalidCommands().get(0);
        String actual2 = commandStorage.getInvalidCommands().get(1);
        assertEquals(INVALID_COMMAND, actual1);
        assertEquals(INVALID_COMMAND_2, actual2);
    }

    @Test
    void size_of_invalid_command_increases_by_one_after_adding_once(){
        commandStorage.addInvalidCommandToList(INVALID_COMMAND);
        int actual = commandStorage.getInvalidCommands().size();
        assertEquals(1, actual);
    }

    @Test
    void size_of_invalid_command_increases_by_two_after_adding_twice(){
        commandStorage.addInvalidCommandToList(INVALID_COMMAND);
        commandStorage.addInvalidCommandToList(INVALID_COMMAND_2);
        int actual = commandStorage.getInvalidCommands().size();
        assertEquals(2, actual);
    }
}
