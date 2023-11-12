import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class commandProcessorCreateTest {
    private CommandProcessor commandProcessor;
    private Bank bank;
    private final String accountIdTest1 = "12345678";
    private final String accountIdTest2 = "28092908";
    private final String accountIdTest3 = "98765432";

    private final double apr1 = 1.0;
    private final double apr2 = 2 ;
    private final double apr3 = 3.54;

    private final double amountCd = 500;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        commandProcessor = new CommandProcessor(bank);
        commandProcessor.execute("create checking 12345678 1.0");
        commandProcessor.execute("create savings 28092908 2");
        commandProcessor.execute("create cd 98765432 3.54 500");
    }

    @Test
    void create_checking_command_is_processed_to_the_bank() {
        assertTrue(bank.accountIdAlreadyExists(accountIdTest1));
    }
    @Test
    void create_savings_command_is_processed_to_the_bank() {
        assertTrue(bank.accountIdAlreadyExists(accountIdTest2));
    }
    @Test
    void create_cd_command_is_processed_to_the_bank() {
        assertTrue(bank.accountIdAlreadyExists(accountIdTest3));
    }

    @Test
    void create_checking_command_processed_with_supplied_apr() {
        double actual = bank.getAccounts().get(accountIdTest1).getApr();
        assertEquals(apr1, actual);
    }

    @Test
    void create_savings_command_processed_with_supplied_apr() {
        double actual = bank.getAccounts().get(accountIdTest2).getApr();
        assertEquals(apr2, actual);
    }

    @Test
    void create_cd_command_processed_with_supplied_apr() {
        double actual = bank.getAccounts().get(accountIdTest3).getApr();
        assertEquals(apr3, actual);
    }

    @Test
    void create_checking_command_processed_with_right_account_type() {
        String actual = bank.getAccounts().get(accountIdTest1).getAccountType();
        assertEquals("checking", actual);
    }

    @Test
    void create_savings_command_processed_with_right_account_type() {
        String actual = bank.getAccounts().get(accountIdTest2).getAccountType();
        assertEquals("savings", actual);
    }

    @Test
    void create_cd_command_processed_with_right_account_type() {
        String actual = bank.getAccounts().get(accountIdTest3).getAccountType();
        assertEquals("cd", actual);
    }
    @Test
    void create_cd_account_has_correct_balance() {
        double actual = bank.getAccounts().get(accountIdTest3).getBalance();
        assertEquals( amountCd, actual);
    }

    @Test
    void create_checking_command_processed_with_supplied_account_id() {
        String actual = bank.getAccounts().get(accountIdTest1).getAccountID();
        assertEquals(accountIdTest1, actual);
    }
    @Test
    void create_savings_command_processed_with_supplied_account_id() {
        String actual = bank.getAccounts().get(accountIdTest2).getAccountID();
        assertEquals(accountIdTest2, actual);
    }
    @Test
    void create_cd_command_processed_with_supplied_account_id() {
        String actual = bank.getAccounts().get(accountIdTest3).getAccountID();
        assertEquals(accountIdTest3, actual);
    }

    @Test
    void bank_contains_accounts_after_processing_commands() {
        int actual = bank.getAccounts().size();
        assertEquals(3, actual);
    }
}
