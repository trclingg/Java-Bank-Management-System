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

	public void depositMoneyById(String accountId, double depositAmount) {
		accounts.get(accountId).depositMoney(depositAmount);
	}

	public void withdrawMoneyById(String accountId, double withdrawAmount) {
		accounts.get(accountId).withdrawMoney(withdrawAmount);
	}
}
