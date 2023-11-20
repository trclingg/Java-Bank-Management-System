package banking;

public class CommandValidatorDeposit extends CommandValidator {
    public CommandValidatorDeposit(Bank bank) {
        super(bank);
    }

    public boolean validate(String[] commandParts) {
    if (commandParts.length < 3) {
        return false; // Missing account ID or deposit amount
    }

    String accountId = commandParts[1];
    String depositAmount = commandParts[2];

        if (!accountIDValid(accountId)) {
            return false; // Invalid account ID
        }

        Account account =  bank.getAccounts().get(accountId);
        if (account == null) {
            return false; // banking.Account not found
        }

        String accountType = account.getAccountType();
        if ("checking".equals(accountType)) {

            if (!isValidCheckingDeposit(depositAmount)) {
                return false;
            }
        } else if ("savings".equals(accountType)) {

            if (!isValidSavingsDeposit(depositAmount)) {
                return false;
            }
        } else if ("cd".equals(accountType)) {
            return false; // CDs cannot be deposited into
        } else {
            return false; // Unknown account type
        }

        return true;
    }

    private boolean isValidCheckingDeposit(String depositAmount) {
            double amount = parseDepositAmount(depositAmount);
            if (amount >= 0 && amount <= 1000) {
                String amountStr = Double.toString(amount);
                String[] parts = amountStr.split("\\.");
                if (parts.length <= 2 && (parts.length == 1 || parts[1].length() <= 2)) {
                    return true;
                }
            }

        return false;
    }

    private boolean isValidSavingsDeposit(String depositAmount) {

            double amount = parseDepositAmount(depositAmount);
            if (amount >= 0 && amount <= 2500) {
                String amountStr = Double.toString(amount);
                String[] parts = amountStr.split("\\.");
                if (parts.length <= 2 && (parts.length == 1 || parts[1].length() <= 2)) {
                    return true;
                }
            }

        return false;
    }

    private double parseDepositAmount(String depositAmount) {
            return Double.parseDouble(depositAmount);

    }

}
