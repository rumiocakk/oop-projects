import java.util.List;

public class GMMMachine {
    private static final int ROWS = 6;
    private static final int COLUMNS = 4;
    private static final int SLOT_CAPACITY = 10;
    private final Product[][] slot;
    private final int[][] amount;
    private final String outputFile;

    public GMMMachine(String outputFile) {
        this.outputFile = outputFile;
        slot = new Product[ROWS][COLUMNS];
        amount = new int[ROWS][COLUMNS];
    }

    /**
     * Checks if a product can be placed in the machine.
     *
     * @param product The product to be placed.
     * @return        0 if the product can be placed, -1 otherwise.
     */
    private int checkIfProductPlaceable(Product product) {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                if ((slot[row][col] == null || slot[row][col].getName().equals(product.getName())) &&
                        amount[row][col] < SLOT_CAPACITY) {
                    if (slot[row][col] == null) {
                        slot[row][col] = product;
                        amount[row][col] = 0; // Set quantity to 0 if the slot is empty.
                    }
                    amount[row][col]++; // Increment the amount of product in the current slot if product is placed.
                    return 0;
                }
            }
        }

        return -1;
    }

    /**
     * Loads products into the machine.
     *
     * @param productList The list of products to be loaded.
     * @return            0 if the machine is not full, -1 otherwise.
     */
    public int loadProducts(List<Product> productList) {
        int filledSlots = 0; // Track the number of filled slots.
        for (Product product : productList) {
            if (checkIfProductPlaceable(product) == -1) {
                if (filledSlots == ROWS * COLUMNS * SLOT_CAPACITY) {
                    writeToFileWithNewLine("INFO: There is no available place to put " + product.getName());
                    writeToFileWithNewLine("INFO: The machine is full!");
                    return -1;
                } else {
                    writeToFileWithNewLine("INFO: There is no available place to put " + product.getName());
                }
            } else {
                filledSlots++; // Increment filledSlots if the product is successfully placed.
            }
        }

        return 0;
    }


    /**
     * Processes a list of purchases.
     *
     * @param purchaseList A list of Purchase objects representing the purchases to be processed.
     */
    public void processPurchases(List<Purchase> purchaseList) {
        for (Purchase purchase : purchaseList) {
            int sumMoney = purchase.getValidMoney() + purchase.getInvalidMoney();
            String selection = purchase.getSelection();
            int value = purchase.getValue();
            writeToFileWithNewLine("INPUT: " + purchase.getInput());

            if (selection.equals("NUMBER")) {
                processedByNumber(purchase, sumMoney, value);
            } else {
                processedBySelection(purchase, sumMoney, selection, value);
            }
        }
    }

    /**
     * Processes a purchase based on the selection ("NUMBER").
     *
     * @param purchase The purchase object representing the current transaction.
     * @param sumMoney The total amount of money inserted by the user.
     * @param value    The slot number selected by the user.
     * @return         0 if the product is found (If the payment phase has been reached), -1 otherwise.
     */
    private int processedByNumber(Purchase purchase, int sumMoney, int value) {
        if (Purchase.checkForInvalidMoney(purchase) == 0 && purchase.checkForValidMoney(purchase) == 0) {
            writeToFileWithNewLine("INFO: You sent invalid money. Your transaction will be made using the valid " + purchase.getValidMoney() + " TL.");
        } else if (Purchase.checkForInvalidMoney(purchase) == 0 && purchase.checkForValidMoney(purchase) == -1) {
            writeToFileWithNewLine("INFO: You sent invalid money. Your transaction cannot be completed.");
            writeToFileWithNewLine("RETURN: Returning your change: " + sumMoney + " TL");
            return -1;
        }

        if (value < 0 || value >= ROWS * COLUMNS) {
            writeToFileWithNewLine("INFO: Number cannot be accepted. Please try again with another number.");
            writeToFileWithNewLine("RETURN: Returning your change: " + sumMoney + " TL");
            return -1; // Exit the method. The slot number is invalid, and the process is ending.
        }

        int row = value / COLUMNS;
        int col = value % COLUMNS;
        Product currentProduct = slot[row][col];

        // The slot number is valid.
        // Perform the operation depending on whether the product is found or not.
        if (currentProduct != null) {
            processPayment(currentProduct, purchase);
        } else {
            writeToFileWithNewLine("INFO: This slot is empty, your money will be returned.");
            writeToFileWithNewLine("RETURN: Returning your change: " + sumMoney + " TL");
            return -1;
        }

        return 0;
    }

    /**
     * Processes a purchase based on a given selection.
     *
     * @param purchase  The Purchase object representing the current purchase.
     * @param sumMoney  The total amount of money inserted by the user.
     * @param selection A selection in ("PROTEIN", "CARB", "FAT", "CALORIE").
     * @param value     The value associated with the selection.
     * @return          0 if the product is found (If the payment phase has been reached), -1 otherwise.
     */
    private int processedBySelection(Purchase purchase, int sumMoney, String selection, int value) {
        if (Purchase.checkForInvalidMoney(purchase) == 0 && purchase.checkForValidMoney(purchase) == 0) {
            writeToFileWithNewLine("INFO: You sent invalid money. Your transaction will be made using the valid " + purchase.getValidMoney() + " TL.");
        } else if (Purchase.checkForInvalidMoney(purchase) == 0 && purchase.checkForValidMoney(purchase) == -1) {
            writeToFileWithNewLine("INFO: You sent invalid money. Your transaction cannot be completed.");
            writeToFileWithNewLine("RETURN: Returning your change: " + sumMoney + " TL");
            return -1;
        }

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                Product currentProduct = slot[row][col];
                if (currentProduct != null && isInRange(currentProduct, selection, value)) {
                    processPayment(currentProduct, purchase);
                    return 0; // Exit the method. Suitable product found.
                }
            }
        }

        // No suitable product found.
        writeToFileWithNewLine("INFO: Product not found, your money will be returned.");
        writeToFileWithNewLine("RETURN: Returning your change: " + sumMoney + " TL");

        return -1;
    }

    /**
     * Checks whether the nutritional value of a product is within ±5 units of the desired value.
     *
     * @param product   The product whose nutritional value is being checked.
     * @param selection The selection of the user.
     * @param value     The value requested by the user.
     * @return          True if the product meets the nutritional value selection criteria, false otherwise.
     */
    private boolean isInRange(Product product, String selection, int value) {
        switch (selection) {
            case "PROTEIN":
                return Math.abs(product.getProtein() - value) <= 5;
            case "CARB":
                return Math.abs(product.getCarbohydrate() - value) <= 5;
            case "FAT":
                return Math.abs(product.getFat() - value) <= 5;
            case "CALORIE":
                return Math.abs(product.getCalorie() - value) <= 5;
            default:
                return false;
        }
    }

    /**
     * Processes the payment for a product purchase.
     * If the provided money contains invalid values, the transaction is carried out using only the valid values.
     * After the transaction, any remaining money (including invalid values) is returned as change.
     *
     * @param product  The product being purchased.
     * @param purchase The purchase information containing the payment details.
     * @return         0 if the item is purchased (If the valid money to purchase the product is sufficient), -1 otherwise.
     */
    private int processPayment(Product product, Purchase purchase) {
        int sumMoney = purchase.getValidMoney() + purchase.getInvalidMoney();

        if (sumMoney == 0) {
            writeToFileWithNewLine("INFO: You did not enter money. Your transaction cannot be completed.");
            return -1;
        } else if (purchase.getValidMoney() >= product.getPrice()) {
            writeToFileWithNewLine("PURCHASE: You have bought one " + product.getName());
            decrementProductAmount(product);
            sumMoney -= product.getPrice();
            writeToFileWithNewLine("RETURN: Returning your change: " + sumMoney + " TL");
            return 0;
        } else {
            writeToFileWithNewLine("INFO: Insufficient money, try again with more money.");
            writeToFileWithNewLine("RETURN: Returning your change: " + sumMoney + " TL");
            return -1;
        }
    }

    /**
     * Decrements the amount of the product in the slot after a purchase.
     *
     * @param product The product to decrement.
     */
    private void decrementProductAmount(Product product) {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                if (slot[row][col] == product && amount[row][col] > 0) {
                    amount[row][col]--;
                    if (amount[row][col] == 0) {
                        slot[row][col] = null;  //
                    }

                    return; // Decrement only one quantity.
                }
            }
        }
    }

    /**
     * Writes content to the output file with a new line.
     *
     * @param content The content to be written.
     */
    private void writeToFileWithNewLine(String content) {
        FileOutput.writeToFile(outputFile, content, true, true);
    }

    /**
     * Writes content to the output file without a new line.
     *
     * @param content The content to be written.
     */
    private void writeToFileWithoutNewLine(String content) {
        FileOutput.writeToFile(outputFile, content, true, false);
    }

    /**
     * Prints the current state of the machine.
     */
    public void printMachine() {
        writeToFileWithNewLine("-----Gym Meal Machine-----");
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                if (slot[row][col] != null) {
                    float calorie = slot[row][col].getCalorie();
                    int roundedCalorie = Math.round(calorie);
                    writeToFileWithoutNewLine(slot[row][col].getName() + "(" + roundedCalorie + ", " + amount[row][col] + ")___");
                } else {
                    writeToFileWithoutNewLine("___(0, 0)___");
                }
            }
            writeToFileWithNewLine("");
        }
        writeToFileWithNewLine("----------");
    }
}