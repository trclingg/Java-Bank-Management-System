package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandProcessorTransferTest {
	private final String ACCOUNT_ID_1 = "12345678";
	private final String ACCOUNT_ID_2 = "28092908";
	private final String ACCOUNT_ID_3 = "88889999";
	private final String ACCOUNT_ID_4 = "09876543";
	private final double APR_1 = 1.0;
	private final double APR_2 = 2;
	private final double APR_3 = 3;
	private final double APR_4 = 2.3;

	private final double AMOUNT_TRANSFER_1 = 300;
	private final double AMOUNT_TRANSFER_2 = 400;
	private final double AMOUNT_TRANSFER_3 = 500;
	private CommandProcessor commandProcessor;
	private Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
		bank.bankCreateCheckingAccount(ACCOUNT_ID_1, APR_1);
		bank.bankCreateSavingsAccount(ACCOUNT_ID_2, APR_2);
		bank.bankCreateCheckingAccount(ACCOUNT_ID_3, APR_3);
		bank.bankCreateSavingsAccount(ACCOUNT_ID_4, APR_4);

	}

	@Test
	void can_transfer_between_checking_accounts() {
		bank.depositMoneyById(ACCOUNT_ID_1, AMOUNT_TRANSFER_1); // 300
		commandProcessor.execute("transfer 12345678 88889999 200");
		assertEquals(200, bank.getAccountById(ACCOUNT_ID_3).getBalance());
		assertEquals(300 - 200, bank.getAccountById(ACCOUNT_ID_1).getBalance());
	}

	@Test
	void can_transfer_between_savings_accounts() {
		bank.depositMoneyById(ACCOUNT_ID_2, AMOUNT_TRANSFER_2); // 400
		commandProcessor.execute("transfer 28092908 09876543 400");
		assertEquals(400, bank.getAccountById(ACCOUNT_ID_4).getBalance());
		assertEquals(0, bank.getAccountById(ACCOUNT_ID_2).getBalance());
	}

	@Test
	void can_transfer_from_checking_to_savings_accounts() {
		bank.depositMoneyById(ACCOUNT_ID_1, AMOUNT_TRANSFER_1); // 300
		commandProcessor.execute("transfer 12345678 28092908 200");
		assertEquals(200, bank.getAccountById(ACCOUNT_ID_2).getBalance());
		assertEquals(300 - 200, bank.getAccountById(ACCOUNT_ID_1).getBalance());
	}

	@Test
	void can_transfer_from_savings_to_checking_accounts() {
		bank.depositMoneyById(ACCOUNT_ID_2, AMOUNT_TRANSFER_3); // 500
		commandProcessor.execute("transfer 28092908 88889999 500");

		assertEquals(500, bank.getAccountById(ACCOUNT_ID_3).getBalance());
		assertEquals(0, bank.getAccountById(ACCOUNT_ID_2).getBalance());
	}

	@Test
	void can_transfer_amount_greater_than_balance() {
		bank.depositMoneyById(ACCOUNT_ID_2, AMOUNT_TRANSFER_3); // 500
		commandProcessor.execute("transfer 28092908 88889999 1000");

		assertEquals(500, bank.getAccountById(ACCOUNT_ID_3).getBalance());
		assertEquals(0, bank.getAccountById(ACCOUNT_ID_2).getBalance());
	}
}
