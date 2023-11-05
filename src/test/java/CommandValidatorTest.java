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
        boolean actual = commandValidator.validate(VALID_CREATE_COMMAND);
        assertTrue(actual);
    }






}
