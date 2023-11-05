import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommandValidatorTest {
    public static final String VALID_CREATE_COMMAND = "Create savings 98765432 0.6";
    private CommandValidator commandValidator;

    @BeforeEach
    void setUp() {
        commandValidator = new CommandValidator();
    }

    @Test
    void valid_create_command_string() {
        boolean actual = commandValidator.validateCommand(VALID_CREATE_COMMAND);
        assertTrue(actual);
    }

    @Test
    void typos_in_account_types() {
        boolean actual = commandValidator.validateCommand("Create favings 98765432 0.6");
        assertFalse(actual);
    }

    @Test
    void missing_account_types() {
        boolean actual = commandValidator.validateCommand("Create 98765432 0.6");
        assertFalse(actual);
    }

    @Test
    void valid_action_command() {
        boolean actual = commandValidator.validateCommand("Create savings 12345678 0.6");
        assertTrue(actual);
    }
    @Test
    void invalid_action_command() {
        boolean actual = commandValidator.validateCommand("Foobar savings 98765432 0.6");
        assertFalse(actual);
    }
    @Test
    void missing_action_command() {
        boolean actual = commandValidator.validateCommand("savings 98765432 0.6");
        assertFalse(actual);
    }









}
