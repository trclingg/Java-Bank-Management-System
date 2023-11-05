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

    @Test
    void missing_account_id() {
        boolean actual = commandValidator.validateCommand("Create savings 0.6");
        assertFalse(actual);
    }

    @Test
    void account_id_has_more_than_8_digits() {
        boolean actual = commandValidator.validateCommand("Create savings 1234567890 0.6");
        assertFalse(actual);
    }

    @Test
    void account_id_has_less_than_8_digits() {
        boolean actual = commandValidator.validateCommand("Create savings 1234 0.6");
        assertFalse(actual);
    }

    @Test
    void invalid_form_of_account_id() {
        boolean actual = commandValidator.validateCommand("Create checking one123 0.01");
        assertFalse(actual);
    }

    @Test
    void negative_account_id() {
        boolean actual = commandValidator.validateCommand("Create savings -1234567 0.6");
        assertFalse(actual);
    }

    @Test
    void valid_apr() {

        boolean actual = commandValidator.validateCommand("Create savings 98765432 0.65");
        assertTrue(actual);
    }

    @Test
    void apr_at_min_bound() {
        boolean actual = commandValidator.validateCommand("Create savings 98765432 0");
        assertTrue(actual);
    }

    @Test
    void apr_at_max_bound() {
        boolean actual = commandValidator.validateCommand("Create savings 98765432 10");
        assertTrue(actual);
    }

    @Test
    void negative_apr() {
        boolean actual = commandValidator.validateCommand("Create savings 98765432 -0.65");
        assertFalse(actual);
    }

    @Test
    void apr_not_in_range() {
        boolean actual = commandValidator.validateCommand("Create savings 98765432 15.3");
        assertFalse(actual);
    }

    @Test
    void missing_apr() {
        boolean actual = commandValidator.validateCommand("Create savings 98765432");
        assertFalse(actual);
    }

    @Test
    void apr_not_in_double_form() {
        boolean actual = commandValidator.validateCommand("Create savings 98765432 two");
        assertFalse(actual);
    }















}
