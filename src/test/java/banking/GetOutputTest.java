package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GetOutputTest {
	private final String INVALID_COMMAND = "foobar blah blah";
	private final String INVALID_COMMAND_2 = "create create";
	private final String VALID_ACCOUNT_STATUS_1 = "Savings 12345678 800.00 2.00";
	private final String VALID_ACCOUNT_STATUS_2 = "Checking 28282828 600.00 3.00";
	private final String VALID_COMMAND_1 = "cReate savings 12345678 2";
	private final String VALID_COMMAND_2 = "DEposit 12345678 400";
	private final String VALID_COMMAND_3 = "Transfer 12345678 28282828 300";
	private final String VALID_COMMAND_4 = "DePosit 28282828 600";
	private final String VALID_COMMAND_5 = "Withdraw 12345678 500";
	private final String ACCOUNT_ID_1 = "12345678";
	private final String ACCOUNT_ID_2 = "28282828";
	public CommandStorage commandStorage;
	Bank bank;
	private GetOutput getOutput;

	@BeforeEach
	void setup() {
		bank = new Bank();
		commandStorage = new CommandStorage(bank);
		getOutput = new GetOutput(bank, commandStorage);
	}

	@Test
	void output_is_empty_initially() {
		assertTrue(getOutput.getOutput().isEmpty());
	}

	@Test
	void output_returns_correct_status() {
		bank.bankCreateSavingsAccount(ACCOUNT_ID_1, 2);
		bank.depositMoneyById(ACCOUNT_ID_1, 400);
		bank.depositMoneyById(ACCOUNT_ID_1, 400);
		assertEquals(VALID_ACCOUNT_STATUS_1, getOutput.getOutput().get(0));
	}

	@Test
	void output_returns_2_correct_status() {
		bank.bankCreateSavingsAccount(ACCOUNT_ID_1, 2);
		bank.bankCreateCheckingAccount(ACCOUNT_ID_2, 3);
		bank.depositMoneyById(ACCOUNT_ID_1, 400);
		bank.depositMoneyById(ACCOUNT_ID_1, 400);
		bank.depositMoneyById(ACCOUNT_ID_2, 600);
		assertEquals(VALID_ACCOUNT_STATUS_1, getOutput.getOutput().get(0));
		assertEquals(VALID_ACCOUNT_STATUS_2, getOutput.getOutput().get(1));

	}

	@Test
	void create_commands_not_included_in_transaction_history() {
		bank.bankCreateSavingsAccount(ACCOUNT_ID_1, 2);
		bank.depositMoneyById(ACCOUNT_ID_1, 800);
		commandStorage.addValidCommand("DEposit 12345678 800");
		assertEquals(VALID_ACCOUNT_STATUS_1, getOutput.getOutput().get(0));
		assertEquals("Deposit 12345678 800", getOutput.getOutput().get(1));
	}

	@Test
	void output_transaction_history_is_formatted() {
		bank.bankCreateCheckingAccount(ACCOUNT_ID_2, 3);
		bank.depositMoneyById(ACCOUNT_ID_2, 600);
		commandStorage.addValidCommand(VALID_COMMAND_4);
		assertEquals("Deposit 28282828 600", getOutput.getOutput().get(1));

	}

	@Test
	void account_status_returns_correct_with_one_transaction_history() {
		bank.bankCreateSavingsAccount(ACCOUNT_ID_1, 2);
		bank.depositMoneyById(ACCOUNT_ID_1, 400);
		commandStorage.addValidCommand(VALID_COMMAND_2);
		assertEquals("Savings 12345678 400.00 2.00", getOutput.getOutput().get(0));
		assertEquals("Deposit 12345678 400", getOutput.getOutput().get(1));

	}

	@Test
	void account_status_returns_correct_with_two_transaction_histories() {
		bank.bankCreateSavingsAccount(ACCOUNT_ID_1, 2);
		bank.depositMoneyById(ACCOUNT_ID_1, 400);
		bank.depositMoneyById(ACCOUNT_ID_1, 400);
		commandStorage.addValidCommand(VALID_COMMAND_2);
		commandStorage.addValidCommand(VALID_COMMAND_2);
		assertEquals("Savings 12345678 800.00 2.00", getOutput.getOutput().get(0));
		assertEquals("Deposit 12345678 400", getOutput.getOutput().get(1));
		assertEquals("Deposit 12345678 400", getOutput.getOutput().get(2));
	}

	@Test
	void output_two_account_status_with_their_transaction_histories() {
		bank.bankCreateSavingsAccount(ACCOUNT_ID_1, 2);
		commandStorage.addValidCommand(VALID_COMMAND_1);
		bank.depositMoneyById(ACCOUNT_ID_1, 400);
		commandStorage.addValidCommand(VALID_COMMAND_2);
		bank.bankCreateCheckingAccount(ACCOUNT_ID_2, 3);
		commandStorage.addInvalidCommandToList(VALID_COMMAND_1);
		bank.depositMoneyById(ACCOUNT_ID_2, 600);
		commandStorage.addValidCommand(VALID_COMMAND_4);
		assertEquals("Savings 12345678 400.00 2.00", getOutput.getOutput().get(0));
		assertEquals("Deposit 12345678 400", getOutput.getOutput().get(1));
		assertEquals("Checking 28282828 600.00 3.00", getOutput.getOutput().get(2));
		assertEquals("Deposit 28282828 600", getOutput.getOutput().get(3));
		assertEquals("cReate savings 12345678 2", getOutput.getOutput().get(4));
	}

}
