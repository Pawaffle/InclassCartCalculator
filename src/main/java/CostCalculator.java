import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class CostCalculator {
    // Method to calculate trip cost
    public static double calculateCost(double kilometers, double fuelPrice, double fuelConsumptionPer100Km) {
        double fuelNeeded = (kilometers / 100) * fuelConsumptionPer100Km;
        return fuelNeeded * fuelPrice;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Select a language");
        System.out.println("1.Swedish");
        System.out.println("2.Finish");
        System.out.println("3.Japanese");
        System.out.println("Else = English");

        int choice = scanner.nextInt();

        Locale locale;
        switch (choice) {
            case 1:
                locale = new Locale("sv", "SE");
                break;
            case 2:
                locale = new Locale("fi", "FI");
                break;
            case 3:
                locale = new Locale("ja", "JP");
                break;
            default:
                locale = new Locale("en", "US");
                break;
        }
        ResourceBundle rb;
        try{
            rb = ResourceBundle.getBundle("messages", locale);
        } catch (Exception e){
            System.out.println("Language not found");
            rb = ResourceBundle.getBundle("messages", new Locale("en", "US"));
        }




        // Ask user for the number of items
        System.out.print(rb.getString("numberOfItems"));
        double numberOfItems = scanner.nextInt();

        double totalCost = 0;

        for (int i = 0; i < numberOfItems; i++) {

            // Ask user for the price per item
            System.out.printf(rb.getString("priceForItem"), i+1);
            double priceForItem = scanner.nextDouble();

            // Ask user for the qty of item's type
            System.out.printf(rb.getString("qtyForItem"), i+1);
            double qtyForItem = scanner.nextInt();

            // Calculate total cost of the item type
            totalCost += priceForItem * qtyForItem;
        }

        // Display the total cost
        System.out.printf(rb.getString("totalCost"), totalCost);

        scanner.close();
    }
}