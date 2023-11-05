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

	public boolean addAccount(String accountID, Account account) {
		if (!accounts.containsKey(accountID)) {
			accounts.put(accountID, account);
			return true;  // Successfully added the account
		} else {
			return false; // Account ID already exists
		}
//		accounts.put(accountID, account);
	}

	public void depositMoneyById(String accountId, double depositAmount) {
		accounts.get(accountId).depositMoney(depositAmount);
	}

	public void withdrawMoneyById(String accountId, double withdrawAmount) {
		accounts.get(accountId).withdrawMoney(withdrawAmount);
	}
}
