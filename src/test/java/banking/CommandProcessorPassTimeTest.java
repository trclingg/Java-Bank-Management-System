package banking;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandProcessorPassTimeTest {
	private final String ACCOUNT_ID_1 = "12345678";
	private final String ACCOUNT_ID_2 = "28092908";
	private final String ACCOUNT_ID_3 = "88889999";
	private final String ACCOUNT_ID_4 = "09876543";
	private final double APR_1 = 1.0;
	private final double APR_2 = 2;
	private final double APR_3 = 3;
	private final double APR_4 = 2.3;

	private final double AMOUNT = 300;
	private CommandProcessor commandProcessor;

	private Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
		bank.bankCreateCheckingAccount(ACCOUNT_ID_1, APR_1);
		bank.bankCreateSavingsAccount(ACCOUNT_ID_2, APR_2);
		bank.bankCreateCheckingAccount(ACCOUNT_ID_3, APR_3);
		bank.bankCreateCDAccount(ACCOUNT_ID_4, APR_4, AMOUNT);
	}

	@Test
	void pass_one_month_returns_correct_new_balance() {
		bank.depositMoneyById(ACCOUNT_ID_1, 500);
		commandProcessor.execute("pass 1");
		double actual = ((0.01 / 12) * 500) + 500;
		assertEquals(actual, bank.getAccountById(ACCOUNT_ID_1).getBalance());

	}

	@Test
	void pass_two_month_returns_correct_new_balance() {
		bank.depositMoneyById(ACCOUNT_ID_2, 500);
		commandProcessor.execute("pass 2");
		double firstMonthBalance = ((0.02 / 12 * 500) + 500);
		double actual = (0.02 / 12 * firstMonthBalance) + firstMonthBalance;
		assertEquals(actual, bank.getAccountById(ACCOUNT_ID_2).getBalance());
	}

	@Test
	void pass_one_month_removes_one_empty_account() {
		bank.depositMoneyById(ACCOUNT_ID_1, 500);
		bank.depositMoneyById(ACCOUNT_ID_2, 500);
		commandProcessor.execute("pass 1");
		assertFalse(bank.accountIdAlreadyExists(ACCOUNT_ID_3));

	}

	@Test
	void pass_one_month_removes_two_empty_account2() {
		bank.depositMoneyById(ACCOUNT_ID_1, 500);
		commandProcessor.execute("pass 1");
		assertFalse(bank.accountIdAlreadyExists(ACCOUNT_ID_2));
		assertFalse(bank.accountIdAlreadyExists(ACCOUNT_ID_3));

	}

	@Test
	void pass_two_months_removes_one_empty_account() {
		bank.depositMoneyById(ACCOUNT_ID_1, 500);
		bank.depositMoneyById(ACCOUNT_ID_2, 500);
		commandProcessor.execute("pass 2");
		assertFalse(bank.accountIdAlreadyExists(ACCOUNT_ID_3));

	}

	@Test
	void pass_two_months_removes_two_empty_account2() {
		bank.depositMoneyById(ACCOUNT_ID_1, 500);
		commandProcessor.execute("pass 2");
		assertFalse(bank.accountIdAlreadyExists(ACCOUNT_ID_2));
		assertFalse(bank.accountIdAlreadyExists(ACCOUNT_ID_3));

	}

	@Test
	void pass_one_month_keeps_non_empty_account() {
		bank.depositMoneyById(ACCOUNT_ID_1, 500);
		commandProcessor.execute("pass 1");
		assertTrue(bank.accountIdAlreadyExists(ACCOUNT_ID_1));

	}

	@Test
	void pass_two_months_keeps_non_empty_account() {
		bank.depositMoneyById(ACCOUNT_ID_1, 500);
		commandProcessor.execute("pass 2");
		assertTrue(bank.accountIdAlreadyExists(ACCOUNT_ID_1));
	}

	@Test
	void pass_one_month_returns_correct_deducted_balance_for_account_under_100() {
		bank.depositMoneyById(ACCOUNT_ID_1, 90);
		commandProcessor.execute("pass 1");
		double actual = ((0.01 / 12) * (90 - 25)) + (90 - 25);
		assertEquals(actual, bank.getAccountById(ACCOUNT_ID_1).getBalance());

	}

	@Test
	void pass_two_month_returns_correct_deducted_balance_for_account_under_100() {
		bank.depositMoneyById(ACCOUNT_ID_1, 90);
		commandProcessor.execute("pass 2");
		double firstMonthBalance = ((0.01 / 12) * (90 - 25)) + (90 - 25);
		double actual = ((0.01 / 12) * (firstMonthBalance - 25)) + (firstMonthBalance - 25);
		assertEquals(actual, bank.getAccountById(ACCOUNT_ID_1).getBalance());

	}

	@Test
	void cd_account_balance_is_calculated_correctly_after_one_month() {
		commandProcessor.execute("pass 1");
		double calculatedBalance = AMOUNT;
		for (int count = 0; count < 4; count++) {
			calculatedBalance = calculatedBalance + (calculatedBalance * 0.023 / 12);
		}
		assertEquals(calculatedBalance, bank.getAccountById(ACCOUNT_ID_4).getBalance());

	}

	@Test
	void cd_account_balance_is_calculated_correctly_after_two_months() {
		commandProcessor.execute("pass 2");
		double calculatedBalance = AMOUNT;
		for (int count = 0; count < 8; count++) {
			calculatedBalance = calculatedBalance + (calculatedBalance * 0.023 / 12);
		}
		assertEquals(calculatedBalance, bank.getAccountById(ACCOUNT_ID_4).getBalance());

	}

	@Test
	void pass_time_twice() {
		bank.depositMoneyById(ACCOUNT_ID_2, 500);
		commandProcessor.execute("pass 2");
		double firstMonthBalance = ((0.02 / 12 * 500) + 500);
		double secondMonthBalance = (0.02 / 12 * firstMonthBalance) + firstMonthBalance;
		commandProcessor.execute("pass 1");
		double actual = (0.02 / 12 * secondMonthBalance) + secondMonthBalance;
		assertEquals(actual, bank.getAccountById(ACCOUNT_ID_2).getBalance());
	}

}
