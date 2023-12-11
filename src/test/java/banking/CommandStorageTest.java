package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandStorageTest {
	private final String INVALID_COMMAND = "foobar blah blah";
	private final String INVALID_COMMAND_2 = "create create";
	private final String VALID_COMMAND_1 = "cReate savings 12345678 2";
	private final String VALID_COMMAND_2 = "deposit 12345678 400";
	private final String VALID_COMMAND_2_1 = "Deposit 12345678 400";
	private final String VALID_COMMAND_3 = "Transfer 12345678 28282828 300";
	private final String VALID_COMMAND_4 = "Deposit 28282828 600";
	private final String VALID_COMMAND_5 = "Withdraw 12345678 500";
	private final String ACCOUNT_ID_1 = "12345678";
	private final String ACCOUNT_ID_2 = "28282828";
	public CommandStorage commandStorage;
	Bank bank;

	@BeforeEach
	void setup() {
		bank = new Bank();
		commandStorage = new CommandStorage(bank);
	}

	@Test
	void invalid_command_list_is_initially_empty() {
		int actual = commandStorage.getInvalidCommands().size();
		assertEquals(0, actual);
	}

	@Test
	void add_one_invalid_command_to_list() {
		commandStorage.addInvalidCommandToList(INVALID_COMMAND);
		String actual = commandStorage.getInvalidCommands().get(0);
		assertEquals(INVALID_COMMAND, actual);
	}

	@Test
	void add_two_invalid_command_to_list() {
		commandStorage.addInvalidCommandToList(INVALID_COMMAND);
		commandStorage.addInvalidCommandToList(INVALID_COMMAND_2);
		String actual1 = commandStorage.getInvalidCommands().get(0);
		String actual2 = commandStorage.getInvalidCommands().get(1);
		assertEquals(INVALID_COMMAND, actual1);
		assertEquals(INVALID_COMMAND_2, actual2);
	}

	@Test
	void size_of_invalid_command_increases_by_one_after_adding_once() {
		commandStorage.addInvalidCommandToList(INVALID_COMMAND);
		int actual = commandStorage.getInvalidCommands().size();
		assertEquals(1, actual);
	}

	@Test
	void size_of_invalid_command_increases_by_two_after_adding_twice() {
		commandStorage.addInvalidCommandToList(INVALID_COMMAND);
		commandStorage.addInvalidCommandToList(INVALID_COMMAND_2);
		int actual = commandStorage.getInvalidCommands().size();
		assertEquals(2, actual);
	}

	@Test
	void valid_command_list_is_empty_initially() {
		assertTrue(commandStorage.getValidCommands().isEmpty());
	}

	@Test
	void valid_command_can_be_stored_for_one_id() {
		bank.bankCreateSavingsAccount(ACCOUNT_ID_1, 2);
		commandStorage.addValidCommand(VALID_COMMAND_2);
		String actual = commandStorage.getValidCommands().get(ACCOUNT_ID_1).get(0);
		assertEquals(VALID_COMMAND_2_1, actual);
	}

	@Test
	void valid_commands_can_be_stored_for_one_id() {
		bank.bankCreateSavingsAccount(ACCOUNT_ID_1, 2);
		commandStorage.addValidCommand(VALID_COMMAND_2);
		commandStorage.addValidCommand(VALID_COMMAND_5);
		String actual1 = commandStorage.getValidCommands().get(ACCOUNT_ID_1).get(0);
		String actual2 = commandStorage.getValidCommands().get(ACCOUNT_ID_1).get(1);

		assertEquals(VALID_COMMAND_2_1, actual1);
		assertEquals(VALID_COMMAND_5, actual2);
	}

	@Test
	void valid_commands_stored_for_two_ids() {
		bank.bankCreateSavingsAccount(ACCOUNT_ID_1, 2);
		bank.bankCreateCheckingAccount(ACCOUNT_ID_2, 2);
		commandStorage.addValidCommand(VALID_COMMAND_2);
		commandStorage.addValidCommand(VALID_COMMAND_4);
		assertEquals(VALID_COMMAND_2_1, commandStorage.getValidCommands().get(ACCOUNT_ID_1).get(0));
		assertEquals(VALID_COMMAND_4, commandStorage.getValidCommands().get(ACCOUNT_ID_2).get(0));
	}

	@Test
	void two_valid_commands_stored_for_two_ids() {
		bank.bankCreateSavingsAccount(ACCOUNT_ID_1, 2);
		bank.bankCreateCheckingAccount(ACCOUNT_ID_2, 2);
		commandStorage.addValidCommand(VALID_COMMAND_2);
		commandStorage.addValidCommand(VALID_COMMAND_4);
		commandStorage.addValidCommand(VALID_COMMAND_3);
		assertEquals(VALID_COMMAND_2_1, commandStorage.getValidCommands().get(ACCOUNT_ID_1).get(0));
		assertEquals(VALID_COMMAND_3, commandStorage.getValidCommands().get(ACCOUNT_ID_1).get(1));
		assertEquals(VALID_COMMAND_4, commandStorage.getValidCommands().get(ACCOUNT_ID_2).get(0));
		assertEquals(VALID_COMMAND_3, commandStorage.getValidCommands().get(ACCOUNT_ID_2).get(1));
	}

	@Test
	void create_commands_not_added_to_valid_commands() {
		bank.bankCreateSavingsAccount(ACCOUNT_ID_1, 2);
		commandStorage.addValidCommand(VALID_COMMAND_1);

		assertTrue(commandStorage.getValidCommands().isEmpty());
	}

	@Test
	void transfer_commands_added_to_both_accounts_transaction_history() {
		bank.bankCreateSavingsAccount(ACCOUNT_ID_1, 2);
		bank.bankCreateCheckingAccount(ACCOUNT_ID_2, 2);
		commandStorage.addValidCommand(VALID_COMMAND_3);
		assertEquals(VALID_COMMAND_3, commandStorage.getValidCommands().get(ACCOUNT_ID_1).get(0));
		assertEquals(VALID_COMMAND_3, commandStorage.getValidCommands().get(ACCOUNT_ID_2).get(0));
	}

}
