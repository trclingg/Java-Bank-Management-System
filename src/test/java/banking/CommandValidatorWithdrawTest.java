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
		bank.bankCreateCDAccount(ACCOUNT_ID_2, AccountTest.APR, AccountTest.DEPOSIT);
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
		bank.bankProcessMoneyWithdrawal(ACCOUNT_ID, 200);
		boolean actual = commandValidator.validateCommand("withdraw 12345678 200");
		assertFalse(actual);
	}

}
