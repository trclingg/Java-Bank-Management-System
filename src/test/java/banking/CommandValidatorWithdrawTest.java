package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandValidatorWithdrawTest {
	public static final String ACCOUNT_ID = "12345678";
	public static final String ACCOUNT_ID_1 = "89456185";

	public static final String ACCOUNT_ID_2 = "01010101";
	CommandValidator commandValidator;
	Bank bank;

	@BeforeEach
	void SetUp() {
		bank = new Bank();
		bank.bankCreateSavingsAccount(ACCOUNT_ID, AccountTest.APR);
		bank.bankCreateCheckingAccount(ACCOUNT_ID_1, AccountTest.APR);
		bank.bankCreateCDAccount(ACCOUNT_ID_2, AccountTest.APR, 1000);
		commandValidator = new CommandValidator(bank);

	}

	@Test
	void withdraw_is_valid() {
		boolean actual = commandValidator.validateCommand("Withdraw 12345678 300");
		assertTrue(actual);
	}

	@Test
	void withdraw_command_with_length_one_is_invalid() {
		boolean actual = commandValidator.validateCommand("Withdraw");
		assertFalse(actual);
	}

	@Test
	void withdraw_command_with_exceeded_words_is_invalid() {
		boolean actual = commandValidator.validateCommand("Withdraw 12345678 500 hi");
		assertFalse(actual);
	}

	@Test
	void missing_command() {
		boolean actual = commandValidator.validateCommand("12345678 500");
		assertFalse(actual);
	}

	@Test
	void invalid_withdraw_command() {
		boolean actual = commandValidator.validateCommand("Withdrawing 12345678 500");
		assertFalse(actual);
	}

	@Test
	void withdraw_command_with_extra_space_beginning() {
		boolean actual = commandValidator.validateCommand("    Withdraw 12345678 300");
		assertFalse(actual);
	}

	@Test
	void withdraw_command_with_extra_space_middle() {
		boolean actual = commandValidator.validateCommand("Withdraw     12345678 300");
		assertFalse(actual);
	}

	@Test
	void withdraw_command_with_extra_space_end() {
		boolean actual = commandValidator.validateCommand("Withdraw 12345678 300     ");
		assertTrue(actual);
	}

	@Test
	void case_insensitive_withdraw_command() {
		boolean actual = commandValidator.validateCommand("WITHdraw 12345678 300");
		assertTrue(actual);
	}

	@Test
	void withdraw_amount_for_savings_cant_be_more_than_1000() {
		boolean actual = commandValidator.validateCommand("withdraw 12345678 1001");
		assertFalse(actual);
	}

	@Test
	void withdraw_amount_for_savings_amount_accepts_max_value_1000() {
		boolean actual = commandValidator.validateCommand("withdraw 12345678 1000");
		assertTrue(actual);
	}

	@Test
	void withdraw_amount_for_savings_can_be_more_than_balance_but_under_max_value() {
		boolean actual = commandValidator.validateCommand("withdraw 12345678 999");
		assertTrue(actual);
	}

	@Test
	void withdraw_amount_for_savings_cant_be_negative() {
		boolean actual = commandValidator.validateCommand("withdraw 12345678 -1");
		assertFalse(actual);
	}

	@Test
	void withdraw_amount_for_savings_can_be_zero() {
		boolean actual = commandValidator.validateCommand("withdraw 12345678 0");
		assertTrue(actual);
	}

	@Test
	void withdraw_amount_for_savings_accepts_above_min_value() {
		boolean actual = commandValidator.validateCommand("withdraw 12345678 1");
		assertTrue(actual);
	}

	@Test
	void withdraw_amount_for_checking_cant_be_more_than_4000() {
		boolean actual = commandValidator.validateCommand("withdraw 89456185 401");
		assertFalse(actual);
	}

	@Test
	void withdraw_amount_for_checking_amount_accepts_max_value_400() {
		boolean actual = commandValidator.validateCommand("withdraw 89456185 400");
		assertTrue(actual);
	}

	@Test
	void withdraw_amount_for_checking_can_be_more_than_balance_but_under_max_value() {
		boolean actual = commandValidator.validateCommand("withdraw 89456185 399");
		assertTrue(actual);
	}

	@Test
	void withdraw_amount_for_checking_accepts_above_min_value() {
		boolean actual = commandValidator.validateCommand("withdraw 89456185 1");
		assertTrue(actual);
	}

	@Test
	void withdraw_amount_must_be_digits() {
		boolean actual = commandValidator.validateCommand("withdraw 894561858 2ne1");
		assertFalse(actual);
	}

	@Test
	void withdraw_amount_for_checking_can_be_zero() {
		boolean actual = commandValidator.validateCommand("withdraw 89456185 0");
		assertTrue(actual);
	}

	@Test
	void cant_withdraw_without_adding_account_in_bank() {
		boolean actual = commandValidator.validateCommand("Withdraw 22222222 100");
		assertFalse(actual);
	}

	@Test
	void savings_account_can_withdraw_at_max_one_time_per_month() {
		bank.withdrawMoneyById(ACCOUNT_ID, 200);
		boolean actual = commandValidator.validateCommand("withdraw 12345678 200");
		assertFalse(actual);
	}

	@Test
	void checking_can_withdraw_twice() {
		bank.withdrawMoneyById(ACCOUNT_ID_1, 100);
		boolean actual = commandValidator.validateCommand("withdraw " + ACCOUNT_ID_1 + " 100");
		assertTrue(actual);
	}

	@Test
	void withdraw_amount_can_double_one_decimal_place() {
		boolean actual = commandValidator.validateCommand("withdraw 12345678 200.2");
		assertTrue(actual);
	}

	@Test
	void withdraw_amount_can_be_double_two_decimal_place() {
		boolean actual = commandValidator.validateCommand("withdraw 12345678 200.35");
		assertTrue(actual);
	}

	@Test
	void withdraw_amount_cannot_be_double_more_than_two_decimal_place() {
		boolean actual = commandValidator.validateCommand("withdraw 12345678 200.005");
		assertFalse(actual);
	}

	@Test
	void savings_account_cannot_withdraw_if_another_withdraw_happened_in_the_same_month() {
		bank.depositMoneyById(ACCOUNT_ID, 600);
		bank.withdrawMoneyById(ACCOUNT_ID, 200);
		boolean actual = commandValidator.validateCommand("withdraw 12345678 200");
		assertFalse(actual);
	}

	@Test
	void savings_account_can_withdraw_if_one_month_passed_after_previous_withdrawal() {
		bank.depositMoneyById(ACCOUNT_ID, 600);
		bank.withdrawMoneyById(ACCOUNT_ID, 200);
		bank.passTime(1);
		boolean actual = commandValidator.validateCommand("withdraw 12345678 200");
		assertTrue(actual);
	}

	@Test
	void CD_withdrawal_invalid_if_age_below_12_months() {
		bank.passTime(3);
		boolean actual = commandValidator.validateCommand("withdraw 01010101 1000");
		assertFalse(actual);
	}

	@Test
	void CD_withdrawal_valid_if_age_is_12_months() {
		bank.passTime(12);
		boolean actual = commandValidator.validateCommand("withdraw 01010101 2000");
		assertTrue(actual);
	}

	@Test
	void CD_withdrawal_valid_if_age_is_above_12_months() {
		bank.passTime(20);
		boolean actual = commandValidator.validateCommand("withdraw 01010101 2000");
		assertTrue(actual);
	}

	@Test
	void CD_can_withdraw_equal_to_balance() {
		bank.passTime(12);
		double balance = bank.getAccountById(ACCOUNT_ID_2).getBalance();

		String strBalance = Double.toString(balance);

		boolean actual = commandValidator.validateCommand("withdraw 01010101 " + strBalance);
		assertTrue(actual);

	}

	@Test
	void CD_can_withdraw_value_greater_than_balance() {
		bank.passTime(12);
		double balance = bank.getAccountById(ACCOUNT_ID_2).getBalance();

		String strBalance = Double.toString(balance + 100);

		boolean actual = commandValidator.validateCommand("withdraw 01010101 " + strBalance);
		assertTrue(actual);

	}

	@Test
	void CD_cannot_withdraw_value_less_than_balance() {
		bank.passTime(12);
		double balance = bank.getAccountById(ACCOUNT_ID_2).getBalance();

		String strBalance = Double.toString(balance - 1);

		boolean actual = commandValidator.validateCommand("withdraw 01010101 " + strBalance);
		assertFalse(actual);

	}

}
