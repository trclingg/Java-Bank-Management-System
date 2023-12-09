package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandValidatorDepositTest {
	public static final String ACCOUNT_ID = "12345678";
	public static final String ACCOUNT_ID_1 = "89456185";

	public static final String ACCOUNT_ID_2 = "01010101";
	CommandValidator commandValidator;
	Savings savingsAccount;
	CertificateDeposit cdAccount;
	Checking checkingAccount;
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
	void deposit_is_valid() {
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
		boolean actual = commandValidator.validateCommand("Deposit 89456185 1000");
		assertTrue(actual);
	}

	@Test
	void valid_deposit_zero() {
		boolean actual = commandValidator.validateCommand("Deposit 12345678 0");
		assertTrue(actual);
	}

	@Test
	void invalid_deposit_negative() {
		boolean actual = commandValidator.validateCommand("Deposit 12345678 -500");
		assertFalse(actual);
	}

	@Test
	void invalid_deposit_max_savings_exceeded() {
		boolean actual = commandValidator.validateCommand("Deposit 12345678 2501");
		assertFalse(actual);
	}

	@Test
	void invalid_deposit_max_checking_exceeded() {
		boolean actual = commandValidator.validateCommand("Deposit 89456185 1001");
		assertFalse(actual);
	}

	@Test
	void invalid_deposit_missing_amount() {
		boolean actual = commandValidator.validateCommand("Deposit 12345678");
		assertFalse(actual);
	}

	@Test
	void invalid_deposit_missing_account_id() {
		boolean actual = commandValidator.validateCommand("Deposit 500");
		assertFalse(actual);
	}

	@Test
	void invalid_deposit_typos_in_command() {
		boolean actual = commandValidator.validateCommand("Depost 12345678 500");
		assertFalse(actual);
	}

	@Test
	void invalid_deposit_missing_account_id_and_amount() {
		boolean actual = commandValidator.validateCommand("Deposit");
		assertFalse(actual);
	}

	@Test
	void invalid_deposit_cd_account() {
		boolean actual = commandValidator.validateCommand("Deposit 01010101 500");
		assertFalse(actual);
	}

	@Test
	void deposit_amount_with_more_than_two_decimals() {
		boolean actual = commandValidator.validateCommand("Deposit 12345678 500.668");
		assertFalse(actual);
	}

	@Test
	void deposit_valid_with_two_decimals() {
		boolean actual = commandValidator.validateCommand("Deposit 12345678 500.65");
		assertTrue(actual);
	}

	@Test
	void deposit_valid_with_one_decimal() {
		boolean actual = commandValidator.validateCommand("Deposit 12345678 500.6");
		assertTrue(actual);
	}

	@Test
	void deposit_valid_with_integer_amount() {
		boolean actual = commandValidator.validateCommand("Deposit 12345678 500");
		assertTrue(actual);
	}

	@Test
	void deposit_invalid_account_id_length_more_than_8_digits() {
		boolean actual = commandValidator.validateCommand("Deposit 1234567890 500");
		assertFalse(actual);
	}

	@Test
	void deposit_invalid_account_id_length_less_than_8_digits() {
		boolean actual = commandValidator.validateCommand("Deposit 1234567 500");
		assertFalse(actual);
	}

	@Test
	void deposit_account_id_not_in_number_form() {
		boolean actual = commandValidator.validateCommand("Deposit one123 500");
		assertFalse(actual);
	}

	@Test
	void deposit_negative_account_id() {
		boolean actual = commandValidator.validateCommand("Deposit -1234567 500");
		assertFalse(actual);
	}

	@Test
	void deposit_in_cd_account_is_invalid() {
		boolean actual = commandValidator.validateCommand("Deposit 01010101 500");
		assertFalse(actual);
	}

	@Test
	void deposit_command_with_length_one_is_invalid() {
		boolean actual = commandValidator.validateCommand("Deposit");
		assertFalse(actual);
	}

	@Test
	void deposit_command_with_exceeded_words_is_invalid() {
		boolean actual = commandValidator.validateCommand("Deposit 12345678 500 hi");
		assertFalse(actual);
	}

	@Test
	void deposit_command_with_ending_extra_space_is_valid() {
		boolean actual = commandValidator.validateCommand("Deposit 12345678 500   ");
		assertTrue(actual);
	}

}
