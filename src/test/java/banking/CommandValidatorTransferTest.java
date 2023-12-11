package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandValidatorTransferTest {
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
	void valid_transfer_command_() {
		boolean actual = commandValidator.validateCommand("transfer 12345678 89456185 200");
		assertTrue(actual);
	}

	@Test
	void transfer_command_is_invalid_with_no_transfer_amount() {
		boolean actual = commandValidator.validateCommand("transfer 12345678 89456185");
		assertFalse(actual);
	}

	@Test
	void transfer_command_is_invalid_with_1_account_id() {
		boolean actual = commandValidator.validateCommand("transfer 12345678 200");
		assertFalse(actual);
	}

	@Test
	void transfer_command_must_include_the_transfer() {
		boolean actual = commandValidator.validateCommand("12345678 89456185 20");
		assertFalse(actual);
	}

	@Test
	void transfer_command_having_extra_arguments_is_invalid() {
		boolean actual = commandValidator.validateCommand("transfer 12345678 89456185 200 hi");
		assertFalse(actual);
	}

	@Test
	void transfer_command_is_case_insensitive() {
		boolean actual = commandValidator.validateCommand("TrAnsFER 12345678 89456185 200");
		assertTrue(actual);
	}

	@Test
	void transfer_command_with_typos_is_invalid() {
		boolean actual = commandValidator.validateCommand("transfering 12345678 89456185 200");
		assertFalse(actual);
	}

	@Test
	void transfer_command_with_extra_whitespace_in_the_beginning_is_invalid() {
		boolean actual = commandValidator.validateCommand("   transfer 12345678 89456185 200");
		assertFalse(actual);
	}

	@Test
	void transfer_command_with_extra_whitespace_in_the_middle_is_invalid() {
		boolean actual = commandValidator.validateCommand("Transfer    12345678    89456185 200");
		assertFalse(actual);
	}

	@Test
	void transfer_command_can_have_extra_whitespace_in_the_end() {
		boolean actual = commandValidator.validateCommand("Transfer 12345678 89456185 200     ");
		assertTrue(actual);
	}

	@Test
	void transfer_command_cannot_have_negative_transfer_amount() {
		boolean actual = commandValidator.validateCommand("Transfer 12345678 89456185 -1");
		assertFalse(actual);
	}

	@Test
	void transfer_amount_can_be_zero() {
		boolean actual = commandValidator.validateCommand("transfer 12345678 89456185 0");
		assertTrue(actual);
	}

	@Test
	void transfer_amount_must_be_in_digit_form() {
		boolean actual = commandValidator.validateCommand("Transfer 12345678 89456185 one");
		assertFalse(actual);
	}

	@Test
	void transfer_amount_can_double_one_decimal_place() {
		boolean actual = commandValidator.validateCommand("Transfer 12345678 89456185 200.2");
		assertTrue(actual);
	}

	@Test
	void transfer_amount_can_be_double_two_decimal_place() {
		boolean actual = commandValidator.validateCommand("Transfer 12345678 89456185 200.25");
		assertTrue(actual);
	}

	@Test
	void transfer_amount_cannot_be_double_more_than_two_decimal_place() {
		boolean actual = commandValidator.validateCommand("Transfer 12345678 89456185 200.253");
		assertFalse(actual);
	}

	@Test
	void cd_account_cannot_be_transferred_from() {
		boolean actual = commandValidator.validateCommand("Transfer 01010101 89456185 200");
		assertFalse(actual);
	}

	@Test
	void cd_account_cannot_be_transferred_to() {
		boolean actual = commandValidator.validateCommand("Transfer 89456185 01010101 200");
		assertFalse(actual);
	}

	@Test
	void transfer_command_is_invalid_if_account_transfer_from_doesnt_exist() {
		boolean actual = commandValidator.validateCommand("Transfer 89456185 25252525 200");
		assertFalse(actual);
	}

	@Test
	void transfer_command_is_invalid_if_account_transfer_to_doesnt_exist() {
		boolean actual = commandValidator.validateCommand("Transfer 25252525 89456185 200");
		assertFalse(actual);
	}

	@Test
	void account_ID_of_receiving_account_should_be_valid() {
		boolean actual = commandValidator.validateCommand("transfer 12345678 1 0");
		assertFalse(actual);
	}

	@Test
	void account_ID_of_sending_account_should_be_valid() {
		boolean actual = commandValidator.validateCommand("transfer 1345678 11111111 0");
		assertFalse(actual);
	}

	@Test
	void can_transfer_between_checking_accounts() {
		bank.bankCreateCheckingAccount("34343434", AccountTest.APR);
		boolean actual = commandValidator.validateCommand("transfer 89456185 34343434 200");
		assertTrue(actual);
	}

	@Test
	void can_transfer_between_saving_accounts() {
		bank.bankCreateCheckingAccount("67676767", AccountTest.APR);
		boolean actual = commandValidator.validateCommand("transfer 67676767 12345678 200");
		assertTrue(actual);
	}

	@Test
	void can_transfer_between_saving_and_checking_accounts() {
		boolean actual = commandValidator.validateCommand("transfer 12345678 89456185 200");
		assertTrue(actual);
	}

	@Test
	void cannot_transfer_between_same_account() {
		boolean actual = commandValidator.validateCommand("transfer 12345678 12345678 200");
		assertFalse(actual);
	}

	@Test
	void transfer_from_savings_account_cannot_have_amount_greater_than_1000() {
		boolean actual = commandValidator.validateCommand("transfer 12345678 89456185 1001");
		assertFalse(actual);
	}

	@Test
	void transfer_from_savings_account_can_have_amount_at_max_1000() {
		boolean actual = commandValidator.validateCommand("transfer 12345678 89456185 1000");
		assertTrue(actual);
	}

	@Test
	void transfer_from_checking_account_can_have_amount_at_max_400() {
		boolean actual = commandValidator.validateCommand("transfer 89456185 12345678 400");
		assertTrue(actual);
	}

	@Test
	void transfer_from_checking_account_cannot_have_amount_greater_than_400() {
		boolean actual = commandValidator.validateCommand("transfer 89456185 12345678 401");
		assertFalse(actual);
	}

}
