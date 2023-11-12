import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommandProcessorDepositTest {
    private CommandProcessor commandProcessor;
    private Bank bank;
    private final String accountIdTest1 = "12345678";
    private final String accountIdTest2 = "28092908";

    private final double apr1 = 1.0;
    private final double apr2 = 2 ;

    private final double amount = 500;


    private final double depositAmount = 200;
    @BeforeEach
    void setUp() {
        bank = new Bank();
        bank.bankCreateCheckingAccount(accountIdTest1,apr1 );
        bank.bankCreateSavingsAccount(accountIdTest2, apr2);
        commandProcessor = new CommandProcessor(bank);
    }

    @Test
    void deposit_into_empty_checking_account() {
        commandProcessor.execute("deposit 12345678 200");
        double actual = bank.getAccountById("12345678").getBalance();
        assertEquals(depositAmount, actual);
    }

    @Test
    void deposit_into_non_empty_checking_account() {
        bank.depositMoneyById("12345678", amount);
        commandProcessor.execute("deposit 12345678 200");
        double actual = bank.getAccountById("12345678").getBalance();
        assertEquals(depositAmount+amount, actual);
    }

    @Test
    void deposit_into_empty_savings_account() {
        commandProcessor.execute("deposit 28092908 200");
        double actual = bank.getAccountById("28092908").getBalance();
        assertEquals(depositAmount, actual);
    }

    @Test
    void deposit_into_non_empty_savings_account() {
        bank.depositMoneyById("28092908", amount);
        commandProcessor.execute("deposit 28092908 200");
        double actual = bank.getAccountById("28092908").getBalance();
        assertEquals(depositAmount+amount, actual);
    }

    @Test
    void deposit_into_savings_account_twice() {
        commandProcessor.execute("deposit 28092908 200");
        commandProcessor.execute("deposit 28092908 200");
        double actual = bank.getAccountById("28092908").getBalance();
        assertEquals(depositAmount*2, actual);
    }


}
