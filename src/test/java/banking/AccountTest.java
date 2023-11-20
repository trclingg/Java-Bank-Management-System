package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountTest {
	public static final double APR = 5.7;
	public static final double BALANCE = 1000;
	public static final double DEPOSIT = 600;
	public static final double WITHDRAWAL = 500;

	Savings savingsAccount;
//    public static final double  = 5.7;

	@BeforeEach
	public void setUp() {
		savingsAccount = new Savings("12345678",APR);
	}

	@Test
	public void starting_balance_at_zero_when_created() {
		double actual = savingsAccount.getBalance();
		assertEquals(0, actual);
	}

	@Test
	public void account_apr_is_whatever_given_apr_is() {
		double actual = savingsAccount.getApr();
		assertEquals(APR, actual);
	}

	@Test
	public void balance_increases_by_the_amount_deposited() {
		savingsAccount.depositMoney(DEPOSIT);
		double actual = savingsAccount.getBalance();
		assertEquals(DEPOSIT, actual);

	}

	@Test
	public void account_can_deposit_money_twice() {
		savingsAccount.depositMoney(DEPOSIT);
		savingsAccount.depositMoney(DEPOSIT);
		double actual = savingsAccount.getBalance();
		assertEquals(DEPOSIT * 2, actual);

	}

	@Test
	public void balance_decreases_by_the_amount_withdrawn() {
		savingsAccount.depositMoney(DEPOSIT);
		savingsAccount.withdrawMoney(WITHDRAWAL);
		double actual = savingsAccount.getBalance();
		assertEquals(DEPOSIT - WITHDRAWAL, actual);
	}

	@Test
	public void balance_cannot_go_below_0_when_withdrawing() {
		savingsAccount.depositMoney(DEPOSIT);
		savingsAccount.withdrawMoney(WITHDRAWAL + 1000);
		double actual = savingsAccount.getBalance();
		assertEquals(0, actual);
	}
	@Test
	public void withdraw_with_equal_amount_of_balance_will_go_to_0() {
		savingsAccount.depositMoney(DEPOSIT);
		savingsAccount.withdrawMoney(DEPOSIT);
		double actual = savingsAccount.getBalance();
		assertEquals(0, actual);
	}

	@Test
	public void account_can_withdraw_money_twice() {
		savingsAccount.depositMoney(DEPOSIT * 3);
		savingsAccount.withdrawMoney(WITHDRAWAL);
		savingsAccount.withdrawMoney(WITHDRAWAL);
		double actual = savingsAccount.getBalance();
		assertEquals(DEPOSIT * 3 - WITHDRAWAL * 2, actual);
	}

}
