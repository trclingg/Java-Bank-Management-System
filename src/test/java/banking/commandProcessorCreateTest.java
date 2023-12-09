package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class commandProcessorCreateTest {
    private CommandProcessor commandProcessor;
    private Bank bank;
    private final String ACCOUNT_ID_1 = "12345678";
    private final String ACCOUNT_ID_2 = "28092908";
    private final String ACCOUNT_ID_3 = "98765432";

    private final double APR_1 = 1.0;
    private final double APR_2 = 2 ;
    private final double APR_3 = 3.54;

    private final double AMOUNT_CD = 500;

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
        assertTrue(bank.accountIdAlreadyExists(ACCOUNT_ID_1));
    }
    @Test
    void create_savings_command_is_processed_to_the_bank() {
        assertTrue(bank.accountIdAlreadyExists(ACCOUNT_ID_2));
    }
    @Test
    void create_cd_command_is_processed_to_the_bank() {
        assertTrue(bank.accountIdAlreadyExists(ACCOUNT_ID_3));
    }

    @Test
    void create_checking_command_processed_with_supplied_apr() {
        double actual = bank.getAccounts().get(ACCOUNT_ID_1).getApr();
        assertEquals(APR_1, actual);
    }

    @Test
    void create_savings_command_processed_with_supplied_apr() {
        double actual = bank.getAccounts().get(ACCOUNT_ID_2).getApr();
        assertEquals(APR_2, actual);
    }

    @Test
    void create_cd_command_processed_with_supplied_apr() {
        double actual = bank.getAccounts().get(ACCOUNT_ID_3).getApr();
        assertEquals(APR_3, actual);
    }

    @Test
    void create_checking_command_processed_with_right_account_type() {
        String actual = bank.getAccounts().get(ACCOUNT_ID_1).getAccountType();
        assertEquals("checking", actual);
    }

    @Test
    void create_savings_command_processed_with_right_account_type() {
        String actual = bank.getAccounts().get(ACCOUNT_ID_2).getAccountType();
        assertEquals("savings", actual);
    }

    @Test
    void create_cd_command_processed_with_right_account_type() {
        String actual = bank.getAccounts().get(ACCOUNT_ID_3).getAccountType();
        assertEquals("cd", actual);
    }
    @Test
    void create_cd_account_has_correct_balance() {
        double actual = bank.getAccounts().get(ACCOUNT_ID_3).getBalance();
        assertEquals(AMOUNT_CD, actual);
    }

    @Test
    void create_checking_command_processed_with_supplied_account_id() {
        String actual = bank.getAccounts().get(ACCOUNT_ID_1).getAccountID();
        assertEquals(ACCOUNT_ID_1, actual);
    }
    @Test
    void create_savings_command_processed_with_supplied_account_id() {
        String actual = bank.getAccounts().get(ACCOUNT_ID_2).getAccountID();
        assertEquals(ACCOUNT_ID_2, actual);
    }
    @Test
    void create_cd_command_processed_with_supplied_account_id() {
        String actual = bank.getAccounts().get(ACCOUNT_ID_3).getAccountID();
        assertEquals(ACCOUNT_ID_3, actual);
    }

    @Test
    void bank_contains_accounts_after_processing_commands() {
        int actual = bank.getAccounts().size();
        assertEquals(3, actual);
    }
}
