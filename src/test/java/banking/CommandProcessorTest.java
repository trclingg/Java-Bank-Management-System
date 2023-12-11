package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandProcessorTest {
	private CommandProcessor commandProcessor;
	private Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
		commandProcessor.execute("create checking 12345678 1.0");
	}

	@Test
	void createCommandBeingProcessed() {

		assertTrue(bank.accountIdAlreadyExists("12345678"));
	}

	@Test
	void depositCommandBeingProcessed() {
		commandProcessor.execute(("deposit 12345678 600"));
		double actual = bank.getAccountById("12345678").getBalance();
		assertEquals(600, actual);
	}

	@Test
	void withdrawCommandBeingProcessed() {
		bank.depositMoneyById("12345678", 500);
		commandProcessor.execute(("withdraw 12345678 600"));
		double actual = bank.getAccountById("12345678").getBalance();
		assertEquals(0, actual);
	}

}
