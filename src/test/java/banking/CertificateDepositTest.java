package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CertificateDepositTest {
	CertificateDeposit certificateDeposit;

	@BeforeEach
	public void setUp() {
		certificateDeposit = new CertificateDeposit("12345678",AccountTest.APR, AccountTest.BALANCE);
	}

	@Test
	public void starting_balance_cd_with_given_balance() {
		double actual = certificateDeposit.getBalance();
		assertEquals(AccountTest.BALANCE, actual);
	}

}
