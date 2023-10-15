import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankTest {
	public static final String ACCOUNT_ID = "89456185";
	public static final String ACCOUNT_ID_1 = "00000001";

	Bank bank;
	Savings savingsAccount;
	CertificateDeposit cdAccount;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		savingsAccount = new Savings(AccountTest.APR);
		cdAccount = new CertificateDeposit(AccountTest.APR, AccountTest.DEPOSIT);
	}

	@Test
	public void bank_initially_has_no_accounts() {
		assertTrue(bank.getAccounts().isEmpty());
	}

	@Test
	public void bank_has_1_account_added() {
		bank.addAccount(ACCOUNT_ID, savingsAccount);
		assertEquals(1, bank.getAccounts().size());
	}

	@Test
	public void bank_has_2_accounts_added() {
		bank.addAccount(ACCOUNT_ID, savingsAccount);
		bank.addAccount(ACCOUNT_ID_1, cdAccount);
		assertEquals(2, bank.getAccounts().size());
	}

	@Test
	public void correct_account_is_retrieved_from_bank() {
		bank.addAccount(ACCOUNT_ID, savingsAccount);
		assertEquals(savingsAccount, bank.getAccounts().get(ACCOUNT_ID));
	}

	@Test
	public void deposit_money_by_ID_through_bank() {
		bank.addAccount(ACCOUNT_ID, savingsAccount);
		bank.depositMoneyById(ACCOUNT_ID, AccountTest.DEPOSIT);
		assertEquals(AccountTest.DEPOSIT, bank.getAccounts().get(ACCOUNT_ID).getBalance());
	}

	@Test
	public void deposit_money_by_ID_through_bank_twice() {
		bank.addAccount(ACCOUNT_ID, savingsAccount);
		bank.depositMoneyById(ACCOUNT_ID, AccountTest.DEPOSIT);
		bank.depositMoneyById(ACCOUNT_ID, AccountTest.DEPOSIT);
		assertEquals(AccountTest.DEPOSIT * 2, bank.getAccounts().get(ACCOUNT_ID).getBalance());
	}

	@Test
	public void withdraw_money_by_ID_through_bank() {
		bank.addAccount(ACCOUNT_ID, savingsAccount);
		bank.depositMoneyById(ACCOUNT_ID, AccountTest.DEPOSIT);
		bank.withdrawMoneyById(ACCOUNT_ID, AccountTest.WITHDRAWAL);
		assertEquals(AccountTest.DEPOSIT - AccountTest.WITHDRAWAL, bank.getAccounts().get(ACCOUNT_ID).getBalance());
	}

	@Test
	public void withdraw_money_by_ID_through_bank_twice() {
		bank.addAccount(ACCOUNT_ID, savingsAccount);
		bank.depositMoneyById(ACCOUNT_ID, AccountTest.DEPOSIT + AccountTest.BALANCE);
		bank.withdrawMoneyById(ACCOUNT_ID, AccountTest.WITHDRAWAL);
		bank.withdrawMoneyById(ACCOUNT_ID, AccountTest.WITHDRAWAL);
		assertEquals(AccountTest.DEPOSIT + AccountTest.BALANCE - AccountTest.WITHDRAWAL * 2,
				bank.getAccounts().get(ACCOUNT_ID).getBalance());
	}

}
