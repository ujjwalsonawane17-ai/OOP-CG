
    


class Bank_Account {
    String accountHolder;
    double balance;

    // Constructor
    Bank_Account(String accountHolder, double balance) {
        this.accountHolder = accountHolder;
        this.balance = balance;
    }

    // Method to deposit money
    void deposit(double amount) {
        balance += amount;
        System.out.println(amount + " deposited. Current Balance: " + balance);
    }

    // Method to withdraw money
    void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            System.out.println(amount + " withdrawn. Current Balance: " + balance);
        } else {
            System.out.println("Insufficient balance!");
        }
    }

    // Method to display account details
    void displayDetails() {
        System.out.println("Account Holder: " + accountHolder);
        System.out.println("Balance: " + balance);
    }
}

// Subclass (Single Inheritance)
class SavingsAccount extends Bank_Account {
    double interestRate;

    // Constructor
    SavingsAccount(String accountHolder, double balance, double interestRate) {
        super(accountHolder, balance); // calling superclass constructor
        this.interestRate = interestRate;
    }

    // Method specific to SavingsAccount
    void addInterest() {
        double interest = balance * interestRate / 100;
        balance += interest;
        System.out.println("Interest Added: " + interest + ". New Balance: " + balance);
    }
}

// Main class
public class Main {
    public static void main(String[] args) {
        // Creating object of subclass
        SavingsAccount acc = new SavingsAccount("Om", 5000, 5);

        acc.displayDetails();   // Method from superclass
        acc.deposit(2000);      // Superclass method
        acc.withdraw(1500);     // Superclass method
        acc.addInterest();      // Subclass-specific method
    }
} 
    
