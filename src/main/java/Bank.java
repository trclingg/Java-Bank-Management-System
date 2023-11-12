import java.util.HashMap;
import java.util.Map;

public class Bank {

	private Map<String, Account> accounts;

	Bank() {
		accounts = new HashMap<>();
	}

	public Map<String, Account> getAccounts() {
		return accounts;
	}

	public void addAccount(String accountID, Account account) {
		accounts.put(accountID, account);
	}

	public Account getAccountById(String accountId) {
		return accounts.get(accountId);
	}

	public void depositMoneyById(String accountId, double depositAmount) {
		accounts.get(accountId).depositMoney(depositAmount);
	}

	public void withdrawMoneyById(String accountId, double withdrawAmount) {
		accounts.get(accountId).withdrawMoney(withdrawAmount);
	}

	public boolean accountIdAlreadyExists(String accountId) {
		if (accounts.get(accountId) != null) {
			return true;
		} else {
			return false;
		}
	}

	public void bankCreateCheckingAccount(String accountId, double apr) {
		Checking checkingAccount = new Checking(accountId,apr);
		accounts.put(accountId, checkingAccount);
	}

	public void bankCreateSavingsAccount(String accountId, double apr) {
		Savings savingsAccount = new Savings(accountId, apr);
		accounts.put(accountId, savingsAccount);
	}

	public void bankCreateCDAccount(String accountId, double apr, double balance) {
		CertificateDeposit cdAccount = new CertificateDeposit(accountId, apr, balance);
		accounts.put(accountId, cdAccount);
	}

}
