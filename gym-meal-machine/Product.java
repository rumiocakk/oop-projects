import java.util.ArrayList;
import java.util.List;

public class Product {
    private final String name;
    private final int price;
    private final float protein;
    private final float carbohydrate;
    private final float fat;
    private final float calorie;

    public Product(String name, int price, float protein, float carbohydrate, float fat) {
        this.name = name;
        this.price = price;
        this.protein = protein;
        this.carbohydrate = carbohydrate;
        this.fat = fat;
        this.calorie = setCalorie();
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public float getProtein() {
        return protein;
    }

    public float getCarbohydrate() {
        return carbohydrate;
    }

    public float getFat() {
        return fat;
    }

    public float getCalorie() {
        return calorie;
    }

    public float setCalorie() {
        return 4 * protein + 4 * carbohydrate + 9 * fat;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", protein=" + protein +
                ", carbohydrate=" + carbohydrate +
                ", fat=" + fat +
                ", calorie=" + calorie +
                '}';
    }

    /**
     * This method splits the input string into substrings and converts each substring to a float.
     *
     * @param stringNutritionalValues A string containing nutritional values separated by spaces.
     * @param index                   The index of the nutritional value to retrieve.
     * @return                        The nutritional value at the specified index.
     */
    public static float parseNutritionalValue(String stringNutritionalValues, int index) {
        List<Float> nutritionalValues = new ArrayList<>();
        String[] values = stringNutritionalValues.split(" ");
        for (String value : values) {
            nutritionalValues.add(Float.parseFloat(value));
        }

        return nutritionalValues.get(index);
    }
}