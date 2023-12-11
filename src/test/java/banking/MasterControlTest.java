package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MasterControlTest {
	private final String VALID_CREATE_CHECKING_COMMAND = "create checking 12345678 1.0";
	private final String VALID_CREATE_SAVINGS_COMMAND = "create savings 12345678 2.0";
	private final String VALID_DEPOSIT_COMMAND = "dePosit 12345678 100";
	private final String VALID_WITHDRAW_COMMAND = "wiThdraw 12345678 100";
	MasterControl masterControl;
	private List<String> input;

	@BeforeEach
	void setUp() {
		input = new ArrayList<>();
		Bank bank = new Bank();
		CommandStorage commandStorage = new CommandStorage(bank);
		masterControl = new MasterControl(new CommandValidator(bank), new CommandProcessor(bank), commandStorage,
				new GetOutput(bank, commandStorage));
	}

	private void assertSingleCommand(String command, List<String> actual) {
		assertEquals(1, actual.size());
		assertEquals(command, actual.get(0));
	}

	@Test
	void typo_in_create_command_is_invalid() {
		input.add("creat checking 12345678 1.0");
		List<String> actual = masterControl.start(input);
		assertSingleCommand("creat checking 12345678 1.0", actual);
	}

	@Test
	void typo_in_deposit_command_is_invalid() {
		input.add("deposiTT 12345678 200");

		List<String> actual = masterControl.start(input);
		assertSingleCommand("deposiTT 12345678 200", actual);
	}

	@Test
	void two_commands_with_typos_invalid() {
		input.add("creat checking 12345678 1.0");
		input.add("deposiTT 12345678 100");

		List<String> actual = masterControl.start(input);
		assertEquals(2, actual.size());
		assertEquals("creat checking 12345678 1.0", actual.get(0));
		assertEquals("deposiTT 12345678 100", actual.get(1));
	}

	@Test
	void cannot_open_cd_account_without_balance() {
		input.add("create cd 12345678 0.01");
		assertSingleCommand("create cd 12345678 0.01", masterControl.start(input));
	}

	@Test
	void creating_accounts_with_same_id_invalid() {
		input.add(VALID_CREATE_CHECKING_COMMAND);
		input.add(VALID_CREATE_CHECKING_COMMAND);

		List<String> actual = masterControl.start(input);
		assertEquals(2, actual.size());
		assertEquals("Checking 12345678 0.00 1.00", actual.get(0));
		assertEquals(VALID_CREATE_CHECKING_COMMAND, actual.get(1));

	}

	@Test
	void valid_withdraw_commands_are_stored() {
		input.add("create checking 12345678 1");
		input.add(VALID_DEPOSIT_COMMAND);
		input.add(VALID_WITHDRAW_COMMAND);

		List<String> actual = masterControl.start(input);
		assertEquals(3, actual.size());
		assertEquals("Checking 12345678 0.00 1.00", actual.get(0));
		assertEquals("Deposit 12345678 100", actual.get(1));
		assertEquals("Withdraw 12345678 100", actual.get(2));
	}

	@Test
	void withdraw_from_account_that_does_not_exist_is_invalid() {
		input.add(VALID_WITHDRAW_COMMAND);

		assertSingleCommand(VALID_WITHDRAW_COMMAND, masterControl.start(input));
	}

	@Test
	void cannot_withdraw_more_than_max_bound_from_checking_accounts() {
		input.add(VALID_CREATE_CHECKING_COMMAND);
		input.add("wiTHdraw 12345678 401");

		List<String> actual = masterControl.start(input);
		assertEquals(2, actual.size());
		assertEquals("Checking 12345678 0.00 1.00", actual.get(0));
		assertEquals("wiTHdraw 12345678 401", actual.get(1));
	}

	@Test
	void cannot_withdraw_more_than_max_bound_from_savings_accounts() {
		input.add("cREate savings 12345678 2");
		input.add("DEPOSIT 12345678 200");
		input.add("wiTHdraw 12345678 1001");

		List<String> actual = masterControl.start(input);
		assertEquals(3, actual.size());
		assertEquals("Savings 12345678 200.00 2.00", actual.get(0));
		assertEquals("Deposit 12345678 200", actual.get(1));
		assertEquals("wiTHdraw 12345678 1001", actual.get(2));
	}

	@Test
	void cannot_deposit_into_account_that_does_not_exist() {
		input.add(VALID_DEPOSIT_COMMAND);

		assertSingleCommand(VALID_DEPOSIT_COMMAND, masterControl.start(input));
	}

	@Test
	void valid_deposit_command_got_stored() {
		input.add("create checking 12345678 1");
		input.add(VALID_DEPOSIT_COMMAND);

		List<String> actual = masterControl.start(input);
		assertEquals(2, actual.size());
		assertEquals("Checking 12345678 100.00 1.00", actual.get(0));
		assertEquals("Deposit 12345678 100", actual.get(1));
	}

	@Test
	void deposit_into_cd_account_is_invalid() {
		input.add("create cd 12345678 1 1000");
		input.add("depoSIt 12345678 500");

		List<String> actual = masterControl.start(input);
		assertEquals(2, actual.size());
		assertEquals("depoSIt 12345678 500", actual.get(1));
	}

	@Test
	void deposit_more_than_max_bound_into_checking_accounts_is_invalid() {
		input.add(VALID_CREATE_CHECKING_COMMAND);
		input.add("depoSit 12345678 1001");

		List<String> actual = masterControl.start(input);
		assertEquals(2, actual.size());
		assertEquals("depoSit 12345678 1001", actual.get(1));
	}

	@Test
	void deposit_more_than_max_bound_into_savings_is_invalid() {
		input.add(VALID_CREATE_SAVINGS_COMMAND);
		input.add("deposit 12345678 3000");

		List<String> actual = masterControl.start(input);
		assertEquals(2, actual.size());
		assertEquals("Savings 12345678 0.00 2.00", actual.get(0));
		assertEquals("deposit 12345678 3000", actual.get(1));
	}

	@Test
	void transfer_between_the_same_account_is_invalid() {
		input.add(VALID_CREATE_CHECKING_COMMAND);
		input.add("Create Checking 87654321 1.2");
		input.add("Transfer 12345678 12345678 100");

		List<String> actual = masterControl.start(input);
		assertEquals(3, actual.size());
		assertEquals("Checking 12345678 0.00 1.00", actual.get(0));
		assertEquals("Checking 87654321 0.00 1.20", actual.get(1));
		assertEquals("Transfer 12345678 12345678 100", actual.get(2));

	}

	@Test
	void transfer_between_different_accounts_is_valid() {
		input.add(VALID_CREATE_SAVINGS_COMMAND);
		input.add("Create Checking 87654321 1.2");
		input.add("Transfer 12345678 87654321 100");

		List<String> actual = masterControl.start(input);
		assertEquals(4, actual.size());
		assertEquals("Savings 12345678 0.00 2.00", actual.get(0));
		assertEquals("Transfer 12345678 87654321 100", actual.get(1));
		assertEquals("Checking 87654321 0.00 1.20", actual.get(2));
		assertEquals("Transfer 12345678 87654321 100", actual.get(3));
	}

	@Test
	void invalid_create_an_account_goes_to_invalid_list() {
		input.add("crEate checking 123456 0.01");
		assertSingleCommand("crEate checking 123456 0.01", masterControl.start(input));
	}

	@Test
	void invalid_commands_returns_a_list() {
		input.add("a");
		input.add("abcd");
		input.add("deposit 123456 1000");
		input.add("transfer 12345678 87654321");
		List<String> actual = masterControl.start(input);

		assertEquals("a", actual.get(0));
		assertEquals("abcd", actual.get(1));
		assertEquals("deposit 123456 1000", actual.get(2));
		assertEquals("transfer 12345678 87654321", actual.get(3));

	}

	@Test
	void valid_commands_and_pass_time_got_calculated() {
		input.add("Create checking 12345678 1.245");
		input.add("depoSit 12345678 500");
		input.add("withdraw 12345678 100");
		input.add("pass 1");

		List<String> actual = masterControl.start(input);
		assertEquals(3, actual.size());
		assertEquals("Checking 12345678 400.41 1.24", actual.get(0));
		assertEquals("Deposit 12345678 500", actual.get(1));
		assertEquals("Withdraw 12345678 100", actual.get(2));
	}

	@Test
	void valid_and_invalid_commands_and_pass_time_got_calculated() {
		input.add("Create checking 12345678 1.245");
		input.add("depoSit 12345678 500");
		input.add("withdraw 12345678 100");
		input.add("pass 1");
		input.add("withdraw 12345678");

		List<String> actual = masterControl.start(input);
		assertEquals(4, actual.size());
		assertEquals("Checking 12345678 400.41 1.24", actual.get(0));
		assertEquals("Deposit 12345678 500", actual.get(1));
		assertEquals("Withdraw 12345678 100", actual.get(2));
		assertEquals("withdraw 12345678", actual.get(3));
	}

	@Test
	void valid_commands_to_create_two_accounts_and_stores_transaction_history_with_invalid_command_at_end() {
		input.add("Create checking 12345678 1.245");
		input.add("Create saviNgs 87654321 2.005");
		input.add("depoSit 12345678 500");
		input.add("withdraw 12345678 100");
		input.add("depoSit 87654321 1900");
		input.add("transfer 12345678 87654321 100");
		input.add("withdraw 12345678");

		List<String> actual = masterControl.start(input);
		assertEquals(8, actual.size());
		assertEquals("Checking 12345678 300.00 1.24", actual.get(0));
		assertEquals("Deposit 12345678 500", actual.get(1));
		assertEquals("Withdraw 12345678 100", actual.get(2));
		assertEquals("Transfer 12345678 87654321 100", actual.get(3));
		assertEquals("Savings 87654321 2000.00 2.00", actual.get(4));
		assertEquals("Deposit 87654321 1900", actual.get(5));
		assertEquals("Transfer 12345678 87654321 100", actual.get(6));
		assertEquals("withdraw 12345678", actual.get(7));
	}

	@Test
	void pass_time_removes_empty_account() {
		input.add("Create checking 12345678 1");
		input.add("Withdraw 12345678 100");
		input.add("pass 1");

		List<String> actual = masterControl.start(input);
		assertEquals(0, actual.size());
	}

	@Test
	void can_create_account_with_id_of_a_already_removed_account() {
		input.add("Create checking 12345678 2");
		input.add("pass 1");
		input.add("Create savings 12345678 2.00");

		List<String> actual = masterControl.start(input);
		assertEquals(1, actual.size());
		assertEquals("Savings 12345678 0.00 2.00", actual.get(0));

	}

	@Test
	void display_only_new_create_accounts_transaction_with_id_of_a_already_removed_account() {
		input.add("Create checking 12345678 2");
		input.add("pass 1");
		input.add("Create savings 12345678 2.00");
		input.add("Deposit 12345678 200");

		List<String> actual = masterControl.start(input);
		assertEquals(2, actual.size());
		assertEquals("Savings 12345678 200.00 2.00", actual.get(0));
		assertEquals("Deposit 12345678 200", actual.get(1));
	}

	@Test
	void keep_transfer_transactions_of_that_account_even_other_account_got_deleted() {
		input.add("Create checking 12345678 1.2");
		;
		input.add("Create checking 11111111 0");
		input.add("Deposit 11111111 100");
		input.add("Transfer 11111111 12345678 100");
		input.add("pass 1");

		List<String> actual = masterControl.start(input);
		assertEquals(2, actual.size());
		assertEquals("Checking 12345678 100.10 1.20", actual.get(0));
		assertEquals("Transfer 11111111 12345678 100", actual.get(1));
	}

	@Test
	void display_nothing_if_both_accounts_after_transfer_got_removed() {
		input.add("Create checking 12345678 0.0");
		input.add("Deposit 12345678 0");
		input.add("Create checking 11111111 1.2");
		input.add("Transfer 11111111 12345678 0");
		input.add("pass 1");

		List<String> actual = masterControl.start(input);
		assertEquals(0, actual.size());

	}

	@Test
	void create_two_accounts_with_same_id_is_invalid() {
		input.add("Create checking 12345678 1.245");
		input.add("Deposit 12345678 100");
		input.add("Create savings 12345678 2.00");
		input.add("Withdraw 12345678 50");

		List<String> actual = masterControl.start(input);
		assertEquals(4, actual.size());
		assertEquals("Checking 12345678 50.00 1.24", actual.get(0));
		assertEquals("Deposit 12345678 100", actual.get(1));
		assertEquals("Withdraw 12345678 50", actual.get(2));
		assertEquals("Create savings 12345678 2.00", actual.get(3));

	}

	@Test
	void transfer_between_same_account_is_invalid() {
		input.add("Create checking 12345678 1.245");
		input.add("Deposit 12345678 100");
		input.add("Transfer 12345678 12345678 0");
		input.add("Create savings 87654321 2.00");

		List<String> actual = masterControl.start(input);
		assertEquals(4, actual.size());
		assertEquals("Checking 12345678 100.00 1.24", actual.get(0));
		assertEquals("Deposit 12345678 100", actual.get(1));
		assertEquals("Savings 87654321 0.00 2.00", actual.get(2));
		assertEquals("Transfer 12345678 12345678 0", actual.get(3));

	}

	@Test
	void cd_account_cannot_withdraw_before_one_year() {
		input.add("Create cd 87654321 2.1 2000");
		input.add("pass 1");
		input.add("Withdraw 12345678 300");
		List<String> actual = masterControl.start(input);

		assertEquals(2, actual.size());

		assertEquals("Cd 87654321 2014.03 2.10", actual.get(0));
		assertEquals("Withdraw 12345678 300", actual.get(1));

	}

	@Test
	void pass_time_with_multiple_accounts_present() {
		input.add("Create checking 12345678 1.245");
		input.add("Deposit 12345678 100");
		input.add("Create checking 11111111 1.5");
		input.add("Create cd 87654321 2.1 2000");
		input.add("pass 1");

		List<String> actual = masterControl.start(input);
		assertEquals(3, actual.size());
		assertEquals("Checking 12345678 100.10 1.24", actual.get(0));
		assertEquals("Deposit 12345678 100", actual.get(1));
		assertEquals("Cd 87654321 2014.03 2.10", actual.get(2));

	}

	@Test
	void cannot_withdraw_from_savings_account_more_than_once_per_month() {
		input.add("Create savings 12345678 0.6");
		input.add("Deposit 12345678 700");
		input.add("Withdraw 12345678 300");
		input.add("Deposit 12345678 300");
		input.add("Withdraw 12345678 200");

		List<String> actual = masterControl.start(input);
		System.out.println(actual);

		assertEquals(5, actual.size());
		assertEquals("Savings 12345678 700.00 0.60", actual.get(0));
		assertEquals("Deposit 12345678 700", actual.get(1));
		assertEquals("Withdraw 12345678 300", actual.get(2));
		assertEquals("Deposit 12345678 300", actual.get(3));
		assertEquals("Withdraw 12345678 200", actual.get(4));

	}

	@Test
	void sample_make_sure_this_passes_unchanged() {
		input.add("Create savings 12345678 0.6");
		input.add("Deposit 12345678 700");
		input.add("Deposit 12345678 5000");
		input.add("creAte cHecKing 98765432 0.01");
		input.add("Deposit 98765432 300");
		input.add("Transfer 98765432 12345678 300");
		input.add("Pass 1");
		input.add("Create cd 23456789 1.2 2000");
		List<String> actual = masterControl.start(input);

		assertEquals(5, actual.size());
		assertEquals("Savings 12345678 1000.50 0.60", actual.get(0));
		assertEquals("Deposit 12345678 700", actual.get(1));
		assertEquals("Transfer 98765432 12345678 300", actual.get(2));
		assertEquals("Cd 23456789 2000.00 1.20", actual.get(3));
		assertEquals("Deposit 12345678 5000", actual.get(4));
	}

}
