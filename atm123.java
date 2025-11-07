 import java.util.InputMismatchException;
import java.util.Scanner;

public class atm123{

    public static void main(String[] args) {
        final int CORRECT_PIN = 2212;
        final int MAX_ATTEMPTS = 3;
        double balance = 0.0;
        Scanner scanner = new Scanner(System.in);

        int attempts = 0;
        boolean authenticated = false;

        while (attempts < MAX_ATTEMPTS && !authenticated) {
            System.out.print("Enter your PIN: ");
            try {
                int pin = scanner.nextInt();
                if (pin < 0) {
                    System.out.println("PIN cannot be negative.");
                    attempts++;
                    System.out.println("Attempts left: " + (MAX_ATTEMPTS - attempts));
                    continue;
                }
                if (pin == CORRECT_PIN) {
                    authenticated = true;
                } else {
                    attempts++;
                    System.out.println("Incorrect PIN. Attempts left: " + (MAX_ATTEMPTS - attempts));
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid PIN input. Please enter numeric PIN.");
                scanner.next(); // clear invalid input
                attempts++;
                System.out.println("Attempts left: " + (MAX_ATTEMPTS - attempts));
            }
        }

        if (!authenticated) {
            System.out.println("Too many failed attempts. Exiting...");
            scanner.close();
            return;
        }

        // User authenticated — proceed with ATM options
        while (true) {
            System.out.println("\n1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check Balance");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            try {
                int choice = scanner.nextInt();
                if (choice < 1 || choice > 4) {
                    System.out.println("Invalid option. Please choose between 1 and 4.");
                    continue;
                }

                switch (choice) {
                    case 1:
                        System.out.print("Enter amount to deposit: ");
                        try {
                            double deposit = scanner.nextDouble();
                            if (deposit <= 0) {
                                System.out.println("Deposit amount must be positive.");
                            } else {
                                balance += deposit;
                                System.out.printf("Deposited: %.2f%n", deposit);
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a valid number.");
                            scanner.next(); // Clear invalid input
                        }
                        break;
                    case 2:
                        System.out.print("Enter amount to withdraw: ");
                        try {
                            double withdraw = scanner.nextDouble();
                            if (withdraw <= 0) {
                                System.out.println("Withdrawal amount must be positive.");
                            } else if (withdraw <= balance) {
                                balance -= withdraw;
                                System.out.printf("Withdrawn: %.2f%n", withdraw);
                            } else {
                                System.out.println("Insufficient balance.");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a valid number.");
                            scanner.next(); // Clear invalid input
                        }
                        break;
                    case 3:
                        System.out.printf("Current balance: %.2f%n", balance);
                        break;
                    case 4:
                        System.out.println("Thank you for using the ATM.");
                        scanner.close();
                        return;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid option number.");
                scanner.next(); // Clear invalid input
            }
        }
    }
}
        
    
 
    
