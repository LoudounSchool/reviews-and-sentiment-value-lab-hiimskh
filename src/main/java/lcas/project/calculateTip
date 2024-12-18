import java.util.Scanner;

public class TipCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        double totalBill = getValidBill(scanner);
        double tipPercentage = getValidTip(scanner);
        double tip = calculateTip(totalBill, tipPercentage);
       
        System.out.println("The tip to give is: $" + tip);
        
        scanner.close();
    }

    public static double calculateTip(double totalBill, double tipPercentage) {
        return (totalBill * tipPercentage) / 100;
    }

    public static double getValidBill(Scanner scanner) {
        double bill = -1;
        while (bill <= 0) {
            System.out.print("Enter the total bill amount: ");
            bill = scanner.nextDouble();
            if (bill <= 0) {
                System.out.println("Please enter a positive, non-zero value for the bill.");
            }
        }
        return bill;
    }

    public static double getValidTip(Scanner scanner) {
        double tipPercentage = -1;
        while (tipPercentage <= 0) {
            System.out.print("Enter the tip percentage (e.g., 15 for 15%): ");
            tipPercentage = scanner.nextDouble();
            if (tipPercentage <= 0) {
                System.out.println("Please enter a positive, non-zero value for the tip percentage.");
            }
        }
        return tipPercentage;
    }
    
    public static String processString(String input) {
        input = input.trim(); 
        if (input.contains(" ")) {  
            System.out.println("The input contains extra spaces.");
        }
        return input;
    }
}
