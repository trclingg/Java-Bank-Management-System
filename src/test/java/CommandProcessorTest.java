import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class CommandProcessorTest {
    private CommandProcessor commandProcessor;
    private Bank bank;
    @BeforeEach
    void setUp() {
        bank = new Bank();
        commandProcessor = new CommandProcessor(bank);
        String testInputString = "create checking 12345678 1.0";
        commandProcessor.execute(testInputString);
    }

    @Test
    void createCommandBeingProcessed() {
        assertTrue(bank.accountIdAlreadyExists("12345678"));
    }
}
