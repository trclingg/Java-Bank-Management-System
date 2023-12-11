package banking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bank {

	private Map<String, Account> accounts;
	private ArrayList<String> accountOrder;

	Bank() {
		accounts = new HashMap<>();
		accountOrder = new ArrayList<>();
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
		Account account = accounts.get(accountId);
		account.withdrawMoney(withdrawAmount);

		if (account.getAccountType() == "savings") {
			Savings savingsAccount = (Savings) account;
			savingsAccount.changeWithdrawalStatus();

		}
	}

	public boolean accountIdAlreadyExists(String accountId) {
		if (accounts.get(accountId) != null) {
			return true;
		} else {
			return false;
		}
	}

	public void bankCreateCheckingAccount(String accountId, double apr) {
		Checking checkingAccount = new Checking(accountId, apr);
		accounts.put(accountId, checkingAccount);
		accountOrder.add(accountId);
	}

	public void bankCreateSavingsAccount(String accountId, double apr) {
		Savings savingsAccount = new Savings(accountId, apr);
		accounts.put(accountId, savingsAccount);
		accountOrder.add(accountId);
	}

	public void bankCreateCDAccount(String accountId, double apr, double balance) {
		CertificateDeposit cdAccount = new CertificateDeposit(accountId, apr, balance);
		accounts.put(accountId, cdAccount);
		accountOrder.add(accountId);
	}

	public void passTime(int months) {
		List<String> accountsToRemove = new ArrayList<>();

		for (int counter = 0; counter < months; counter++) {
			updateAccounts(accountsToRemove);
		}
		removeAccounts(accountsToRemove);
	}

	private void updateAccounts(List<String> accountsToRemove) {
		for (String accountId : accounts.keySet()) {
			Account account = accounts.get(accountId);
			if (account.balance == 0) {
				accountsToRemove.add(accountId);
			} else if (account.balance < 100) {
				withdrawMoneyById(accountId, 25);
			}
			account.calculateAPR();
			account.increaseAge(1);
		}
	}

	private void removeAccounts(List<String> accountsToRemove) {
		for (String accountID : accountsToRemove) {
			accountOrder.remove(accountID);
			accounts.remove(accountID);
		}
	}

	public List<String> getAccountOrder() {
		return accountOrder;
	}

	public void transferMoneyBy2Ids(String accountIdFrom, String accountIdTo, double transferAmount) {
		if (transferAmount >= getAccountById(accountIdFrom).getBalance()) {
			double actualAmount = getAccountById(accountIdFrom).getBalance();
			depositMoneyById(accountIdTo, actualAmount);
			getAccountById(accountIdFrom).withdrawMoney(actualAmount);
		} else {
			depositMoneyById(accountIdTo, transferAmount);
			getAccountById(accountIdFrom).withdrawMoney(transferAmount);
		}
	}
}
