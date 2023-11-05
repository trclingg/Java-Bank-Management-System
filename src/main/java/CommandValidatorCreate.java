import java.util.Set;
public class CommandValidatorCreate {
//    Set<String> existingAccountIds;
    public boolean validate(String[] commandParts) {
        // Validation logic specific to "create" command
        if (commandParts.length >= 4 ) {
            String accountType = commandParts[1].toLowerCase();
            String accountId = commandParts[2];
            String aprValue = commandParts[3];
            if (!isValidAccountType(accountType)) {
                return false;
            }

            if (!isValidAccountId(accountId)) {
                return false;
            }
            if (!isValidAPR(aprValue)) {
                return false;
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
        if (accountId.length() != 8) {
            return false;
        }

        if (!accountId.matches("\\d+")) {
            return false;
        }

//        if (existingAccountIds.contains(accountId)) {
//            return false;
//        }

        return true;
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
}

