import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class commandProcessorCreateTest {
    private CommandProcessor commandProcessor;
    private Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        commandProcessor = new CommandProcessor(bank);
        commandProcessor.execute("create checking 12345678 1.0");
        commandProcessor.execute("create savings 28092908 2.5");
        commandProcessor.execute("create cd 98765432 3.54 500");
    }
}
