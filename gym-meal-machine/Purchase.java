public class Purchase {
    private final String input;
    private final int validMoney;
    private final int invalidMoney;
    private final String selection;
    private final int value;

    public Purchase(String input, String stringMoney, String selection, int value) {
        this.input = input;
        this.selection = selection;
        this.value = value;

        int[] moneyValues = setMoneyValues(stringMoney);
        this.validMoney = moneyValues[0];
        this.invalidMoney = moneyValues[1];
    }

    public String getInput() {
        return input;
    }

    public int getValidMoney() {
        return validMoney;
    }

    public int getInvalidMoney() {
        return invalidMoney;
    }

    public String getSelection() {
        return selection;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "input='" + input + '\'' +
                ", sumMoney=" + validMoney +
                ", invalidMoney=" + invalidMoney +
                ", selection='" + selection + '\'' +
                ", value=" + value +
                '}';
    }

    /**
     * Parses the string of money values and calculates valid and invalid money.
     *
     * @param stringMoney The string representing money values.
     * @return            An array containing valid and invalid money.
     */
    public static int[] setMoneyValues(String stringMoney) {
        int validMoney = 0;
        int invalidMoney = 0;

        String[] moneyValues = stringMoney.split(" ");
        int[] validValues = {1, 5, 10, 20, 50, 100, 200};

        for (String value : moneyValues) {
            int intValue = Integer.parseInt(value);
            boolean isValid = false; // Flag to check if the value is valid or not.

            for (int validValue : validValues) {
                if (intValue == validValue) {
                    isValid = true;
                    validMoney += intValue;
                    break; // No need to continue checking once a valid value is found.
                }
            }

            if (!isValid) {
                invalidMoney += intValue;
            }
        }

        return new int[] { validMoney, invalidMoney };
    }

    /**
     * Checks if there is any invalid money in the purchase.
     *
     * @param purchase The Purchase object to check.
     * @return         Returns 0 if there is invalid money, -1 otherwise.
     */
    public static int checkForInvalidMoney(Purchase purchase) {
        if (purchase.getInvalidMoney() == 0) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * Checks if there is any valid money in the purchase.
     *
     * @param purchase The Purchase object to check.
     * @return         Returns 0 if there is valid money, -1 otherwise.
     */
    public int checkForValidMoney(Purchase purchase) {
        if (purchase.getValidMoney() == 0) {
            return -1;
        } else {
            return 0;
        }
    }
}