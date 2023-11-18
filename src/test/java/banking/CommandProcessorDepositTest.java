package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommandProcessorDepositTest {
    private CommandProcessor commandProcessor;
    private Bank bank;
    private final String ACCOUNT_ID_1 = "12345678";
    private final String ACCOUNT_ID_2 = "28092908";

    private final double APR_1 = 1.0;
    private final double APR_2 = 2 ;

    private final double AMOUNT = 500;


    private final double DEPOSIT_AMOUNT = 200;
    @BeforeEach
    void setUp() {
        bank = new Bank();
        bank.bankCreateCheckingAccount(ACCOUNT_ID_1, APR_1);
        bank.bankCreateSavingsAccount(ACCOUNT_ID_2, APR_2);
        commandProcessor = new CommandProcessor(bank);
    }

    @Test
    void deposit_into_empty_checking_account() {
        commandProcessor.execute("deposit 12345678 200");
        double actual = bank.getAccountById("12345678").getBalance();
        assertEquals(DEPOSIT_AMOUNT, actual);
    }

    @Test
    void deposit_into_non_empty_checking_account() {
        bank.depositMoneyById("12345678", AMOUNT);
        commandProcessor.execute("deposit 12345678 200");
        double actual = bank.getAccountById("12345678").getBalance();
        assertEquals(DEPOSIT_AMOUNT + AMOUNT, actual);
    }

    @Test
    void deposit_into_empty_savings_account() {
        commandProcessor.execute("deposit 28092908 200");
        double actual = bank.getAccountById("28092908").getBalance();
        assertEquals(DEPOSIT_AMOUNT, actual);
    }

    @Test
    void deposit_into_non_empty_savings_account() {
        bank.depositMoneyById("28092908", AMOUNT);
        commandProcessor.execute("deposit 28092908 200");
        double actual = bank.getAccountById("28092908").getBalance();
        assertEquals(DEPOSIT_AMOUNT + AMOUNT, actual);
    }

    @Test
    void deposit_into_savings_account_twice() {
        commandProcessor.execute("deposit 28092908 200");
        commandProcessor.execute("deposit 28092908 200");
        double actual = bank.getAccountById("28092908").getBalance();
        assertEquals(DEPOSIT_AMOUNT *2, actual);
    }


}
