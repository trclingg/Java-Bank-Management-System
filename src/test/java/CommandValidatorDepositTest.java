import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommandValidatorDepositTest {
    public static final String ACCOUNT_ID = "12345678";
    public static final String ACCOUNT_ID_1 = "89456185";
    CommandValidator commandValidator;
    Savings savingsAccount;
    CertificateDeposit cdAccount;
    Checking checkingAccount;
    Bank bank;

    @BeforeEach
    void SetUp(){
        bank = new Bank();
        savingsAccount = new Savings(AccountTest.APR);
        bank.addAccount(ACCOUNT_ID, savingsAccount);
        checkingAccount = new Checking(AccountTest.APR);
        bank.addAccount(ACCOUNT_ID_1, checkingAccount);
        commandValidator = new CommandValidator(bank);

    }
    @Test
    void deposit_is_valid(){
        boolean actual = commandValidator.validateCommand("Deposit 12345678 500");
        assertTrue(actual);
    }

    @Test
    void invalid_deposit_command() {
        boolean actual = commandValidator.validateCommand("Depositing 12345678 500");
        assertFalse(actual);
    }
    @Test
    void missing_deposit_command() {
        boolean actual = commandValidator.validateCommand("12345678 500");
        assertFalse(actual);
    }



    @Test
    void extra_space_beginning() {
        boolean actual = commandValidator.validateCommand("    Deposit 12345678 500");
        assertFalse(actual);
    }

    @Test
    void extra_space_middle() {
        boolean actual = commandValidator.validateCommand("Deposit      12345678 500");
        assertFalse(actual);
    }

    @Test
    void extra_space_end() {
        boolean actual = commandValidator.validateCommand("Deposit 12345678 500     ");
        assertTrue(actual);
    }

    @Test
    void case_insensitive_action_command() {
        boolean actual = commandValidator.validateCommand("DepoSIt 12345678 500");
        assertTrue(actual);
    }

    @Test
    void valid_deposit_max_savings() {
        boolean actual = commandValidator.validateCommand("Deposit 12345678 2500");
        assertTrue(actual);
    }

    @Test
    void valid_deposit_max_checking() {
        boolean actual = commandValidator.validateCommand("Deposit 12345678 1000");
        assertTrue(actual);
    }

}
