package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
public class MasterControlTest {
    private final String VALID_CREATE_CHECKING_COMMAND = "create checking 12345678 1.0";
    MasterControl masterControl;
    private List<String> input;
    @BeforeEach
    void setUp() {
        input = new ArrayList<>();
        Bank bank = new Bank();
        masterControl = new MasterControl(new CommandValidator(bank), new CommandProcessor(bank), new CommandStorage(bank));
    }

    private void assertSingleCommand(String command, List<String> actual) {
        assertEquals(1, actual.size());
        assertEquals(command, actual.get(0));
    }

    @Test
    void typo_in_create_command_is_invalid() {
        input.add("creat checking 12345678 1.0");
        List<String> actual = masterControl.start(input);
        assertSingleCommand("creat checking 12345678 1.0", actual);
    }

    @Test
    void typo_in_deposit_command_is_invalid() {
        input.add("deposiTT 12345678 200");

        List<String> actual = masterControl.start(input);
        assertSingleCommand("deposiTT 12345678 200", actual);
    }

    @Test
    void two_commands_with_typos_invalid() {
        input.add("creat checking 12345678 1.0");
        input.add("deposiTT 12345678 100");

        List<String> actual = masterControl.start(input);
        assertEquals(2, actual.size());
        assertEquals("creat checking 12345678 1.0", actual.get(0));
        assertEquals("deposiTT 12345678 100", actual.get(1));
    }

    @Test
    void creating_accounts_with_same_id_invalid() {
        input.add(VALID_CREATE_CHECKING_COMMAND);
        input.add(VALID_CREATE_CHECKING_COMMAND);

        List<String> actual = masterControl.start(input);
        assertSingleCommand(VALID_CREATE_CHECKING_COMMAND, actual);

    }


}
