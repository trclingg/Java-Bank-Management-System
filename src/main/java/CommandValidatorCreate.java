import java.util.Set;
public class CommandValidatorCreate extends CommandValidator {

    public CommandValidatorCreate(Bank bank){
        super(bank);
    }
//    Set<String> existingAccountIds;
    public boolean validate(String[] commandParts) {
        // Validation logic specific to "create" command
        if (commandParts.length >= 4 ) {
            String accountType = commandParts[1].toLowerCase();
            String accountId = commandParts[2];
            String aprValue = commandParts[3];
            String initialAmount = commandParts.length >= 5 ? commandParts[4] : null;

            if (!isValidAccountType(accountType)) {
                return false;
            }

            if (!isValidAccountId(accountId)) {
                return false;
            }
            if (!isValidAPR(aprValue)) {
                return false;
            }
            if ("cd".equals(accountType)) {
                // Additional check for "CD" account type
                if (!isValidAmountForCDAccount(initialAmount)) {
                    return false;
                }
            }
        }
        else {
            return false; // Missing account type, account ID, or initial balance
        }
        return true;
    }

    private boolean isValidAccountType(String accountType){
        if (accountType.equalsIgnoreCase("checking")) {
            return true;
        }
        else if (accountType.equalsIgnoreCase("savings")) {
            return true;
        }
        else if (accountType.equalsIgnoreCase("cd")) {
            return true;
        }
        return false;
    }

    private boolean isValidAccountId(String accountId) {
        return super.accountIDValid(accountId);
//        if (accountId.length() != 8) {
//            return false;
//        }
//
//        if (!accountId.matches("\\d+")) {
//            return false;
//        }

//        if (existingAccountIds.contains(accountId)) {
//            return false;
//        }

//        return true;
    }


    private boolean isValidAPR(String aprValue) {
        try {
            double apr = Double.parseDouble(aprValue);

            if (apr >= 0 && apr <= 10) {
                // Check if the number of decimal places is 0, 1, or 2
                String[] parts = aprValue.split("\\.");
                if (parts.length <= 2 && (parts.length == 1 || parts[1].length() <= 2)) {
                    return true; // Valid APR within the range [0, 10] with up to 2 decimal places
                }
            }
        } catch (NumberFormatException e) {
            // Invalid APR: Not a valid number
        }

        return false; //other reasons
//        return true;

    }

    private boolean isValidAmountForCDAccount(String initialAmountValue) {
        if (initialAmountValue == null) {
            return false; // Missing initial amount for "CD" account type
        }
        try {
            double initialAmount = Double.parseDouble(initialAmountValue);

            if (initialAmount >= 1000 && initialAmount <= 10000) {
                String[] parts = initialAmountValue.split("\\.");
                if (parts.length <= 2 && (parts.length == 1 || parts[1].length() <= 2)) {
                    return true; // Valid initial amount within the range [1000, 10000] with 0, 1, or 2 decimal places
                }
            }
        } catch (NumberFormatException e) {
            // Invalid initial amount: Not a valid number
        }

        return false; // Invalid initial amount for other reasons
    }

}

