import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        String inputProduct = args[0];
        String inputPurchase = args[1];
        String outputFile = args[2];

        String[] productInput = FileInput.readFile(inputProduct, true, true);
        String[] purchaseInput = FileInput.readFile(inputPurchase, true, true);

        List<Product> productList = createProductList(productInput);
        List<Purchase> purchaseList = createPurchaseList(purchaseInput);

        GMMMachine GMMMachine = new GMMMachine(outputFile);
        GMMMachine.loadProducts(productList);
        GMMMachine.printMachine();
        GMMMachine.processPurchases(purchaseList);
        GMMMachine.printMachine();
    }

    /**
     * This method iterates over each input string representing a product and splits it into properties.
     * Using these properties, it constructs a new Product object and adds it to the list.
     *
     * @param productInput An array of strings containing product information.
     * @return             A list of Product objects created from the input strings.
     */
    private static List<Product> createProductList(String[] productInput) {
        List<Product> productList = new ArrayList<>();

        for (String line : productInput) {
            String[] properties = line.split("\t");
            String name = properties[0];
            int price = Integer.parseInt(properties[1]);
            float protein = Product.parseNutritionalValue(properties[2], 0);
            float carbohydrate = Product.parseNutritionalValue(properties[2], 1);
            float fat = Product.parseNutritionalValue(properties[2], 2);

            Product product = new Product(name, price, protein, carbohydrate, fat);

            productList.add(product);
        }

        return productList;
    }

    /**
     * This method iterates over each input string representing a purchase and splits it into properties.
     * Using these properties, it constructs a new Purchase object and adds it to the list.
     *
     * @param purchaseInput An array of strings containing purchase information.
     * @return             A list of Purchase objects created from the input strings.
     */
    private static List<Purchase> createPurchaseList(String[] purchaseInput) {
        List<Purchase> purchaseList = new ArrayList<>();

        for (String line : purchaseInput) {
            String[] properties = line.split("\t");
            String stringMoney = properties[1];
            String selection = properties[2];
            int value = Integer.parseInt(properties[3]);

            Purchase purchase = new Purchase(line, stringMoney, selection, value);

            purchaseList.add(purchase);
        }

        return purchaseList;
    }
}
