package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandProcessorWithdrawTest {
	private final String ACCOUNT_ID_1 = "12345678";
	private final String ACCOUNT_ID_2 = "28092908";
	private final String ACCOUNT_ID_3 = "88889999";
	private final double APR_1 = 1.0;
	private final double APR_2 = 2;
	private final double APR_3 = 3;

	private final double AMOUNT = 500;
	private final double WITHDRAW_AMOUNT = 200;
	private CommandProcessor commandProcessor;
	private Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		bank.bankCreateCheckingAccount(ACCOUNT_ID_1, APR_1);
		bank.bankCreateSavingsAccount(ACCOUNT_ID_2, APR_2);
		bank.bankCreateCDAccount(ACCOUNT_ID_3, APR_3, AMOUNT);
		commandProcessor = new CommandProcessor(bank);
	}

	@Test
	void withdraw_from_empty_checking_account() {
		commandProcessor.execute("withdraw 12345678 100");
		assertEquals(0, bank.getAccountById(ACCOUNT_ID_1).getBalance());
	}

	@Test
	void withdraw_from_empty_savings_account() {
		commandProcessor.execute("withdraw 28092908 200");
		assertEquals(0, bank.getAccountById(ACCOUNT_ID_2).getBalance());
	}

	@Test
	void withdraw_from_non_empty_checking_account() {
		bank.depositMoneyById(ACCOUNT_ID_1, AMOUNT);
		commandProcessor.execute("withdraw 12345678 100");
		assertEquals(AMOUNT - 100, bank.getAccountById(ACCOUNT_ID_1).getBalance());
	}

	@Test
	void withdraw_from_non_empty_savings_account() {
		bank.depositMoneyById(ACCOUNT_ID_2, AMOUNT);
		commandProcessor.execute("withdraw 28092908 200");
		assertEquals(AMOUNT - 200, bank.getAccountById(ACCOUNT_ID_2).getBalance());
	}

	@Test
	void withdraw_from_cd_account() {
		commandProcessor.execute("withdraw 88889999 500");
		assertEquals(0, bank.getAccountById(ACCOUNT_ID_3).getBalance());
	}

	@Test
	void withdraw_money_once_from_checking_account() {
		bank.depositMoneyById(ACCOUNT_ID_1, AMOUNT * 2);
		commandProcessor.execute("withdraw 12345678 200");
		assertEquals(AMOUNT * 2 - 200, bank.getAccountById(ACCOUNT_ID_1).getBalance());
	}

	@Test
	void withdraw_money_twice_from_checking_account() {
		bank.depositMoneyById(ACCOUNT_ID_1, AMOUNT);
		commandProcessor.execute("withdraw 12345678 100");
		commandProcessor.execute("withdraw 12345678 100");
		assertEquals(AMOUNT - 100 * 2, bank.getAccountById(ACCOUNT_ID_1).getBalance());
	}

	@Test
	void withdraw_money_once_from_savings_account() {
		bank.depositMoneyById(ACCOUNT_ID_2, AMOUNT * 2);
		commandProcessor.execute("withdraw 28092908 200");
		assertEquals(AMOUNT * 2 - 200, bank.getAccountById(ACCOUNT_ID_2).getBalance());
	}
}
