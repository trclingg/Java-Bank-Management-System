import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class MasterControlTest {
    MasterControl masterControl;
    @BeforeEach
    void setUp() {
        Bank bank = new Bank();
        masterControl = new MasterControl(new CommandValidator(bank), new CommandProcessor(bank), new CommandStorage(bank));
    }
}
