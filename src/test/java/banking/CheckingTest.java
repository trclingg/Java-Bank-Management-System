package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CheckingTest {
	@Test
	public void starting_balance_of_checking_account_is_zero() {
		Checking checkingAccount = new Checking("12345678", AccountTest.APR);
		double actual = checkingAccount.getBalance();
		assertEquals(0, actual);

	}
}
