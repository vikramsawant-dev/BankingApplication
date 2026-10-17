
import java.util.*;

class BankAccount 
{
    private long accountNumber;
    private String holderName;
    private int pin;
    private double balance;
    private final double MINIMUM_BALANCE = 500.0;
    private ArrayList<String> transactions = new ArrayList<>();

    public BankAccount(long accountNumber, String holderName,int pin, double balance) 
    {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.pin = pin;
        this.balance = balance;
        transactions.add("Account opened with balance: Rs. " + balance);
    }

    public long getAccountNumber() 
    {
        return accountNumber;
    }

    public String getHolderName() 
    {
        return holderName;
    }

    public boolean verifyPin(int enteredPin) 
    {
        return pin == enteredPin;
    }

    public double getBalance() 
    {
        return balance;
    }

    public void deposit(double amount) 
    {
        if (amount <= 0) 
        {
            throw new IllegalArgumentException(
                "Deposit amount must be positive.");
        }

        balance += amount;
        transactions.add("Deposited: Rs. " + amount);
        System.out.printf("Deposit successful! Balance: Rs. %.2f%n",
                          balance);
    }

    public void withdraw(double amount) 
    {
        if (amount <= 0) 
        {
            throw new IllegalArgumentException(
                "Withdrawal amount must be positive.");
        }

        if (balance - amount < MINIMUM_BALANCE) 
        {
            throw new IllegalArgumentException(
                "Insufficient funds or minimum balance rule violated.");
        }

        balance -= amount;
        transactions.add("Withdrawn: Rs. " + amount);
        System.out.printf("Withdrawal successful! Balance: Rs. %.2f%n",
                          balance);
    }

    public void showBalance() 
    {
        System.out.printf("Current Balance: Rs. %.2f%n", balance);
    }

    public void showTransactions() 
    {
        System.out.println("\n--- Transaction History ---");

        for (String transaction : transactions) 
        {
            System.out.println(transaction);
        }
    }
}

public class BankingApplication 
{
    private static Scanner sc = new Scanner(System.in);
    
    private static HashMap<Long, BankAccount> accounts = new HashMap<>();

    public static void main(String[] args) 
    {
        // Sample accounts for testing
        accounts.put(1001L, new BankAccount(1001L, "Vikram", 1234, 12500));
        accounts.put(1002L, new BankAccount(1002L, "Rahul", 5678, 8000));

        while (true) {
            System.out.println("\n===== BANKING APPLICATION =====");
            System.out.println("1. Login to Account");
            System.out.println("2. Create Account");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            try 
            {
                int choice = sc.nextInt();

                switch (choice) 
                {
                    case 1:
                        login();
                        break;
                    case 2:
                        createAccount();
                        break;
                    case 3:
                        System.out.println("Thank you for using our bank!");
                        sc.close();
                        return;
                    default:
                        System.out.println("Invalid menu choice.");
                }
            } 
            catch (InputMismatchException e) 
            {
                System.out.println("Invalid input! Enter a number.");
                sc.nextLine();
            }
        }
    }

    private static void createAccount() 
    {
        try 
        {
            System.out.print("Enter account number: ");
            long number = sc.nextLong();

            if (accounts.containsKey(number)) 
            {
                System.out.println("Account number already exists.");
                return;
            }

            sc.nextLine();
            System.out.print("Enter account holder name: ");
            String name = sc.nextLine().trim();

            if (name.isEmpty()) 
            {
                System.out.println("Name cannot be empty.");
                return;
            }

            System.out.print("Set a 4-digit PIN: ");
            int pin = sc.nextInt();

            if (pin < 1000 || pin > 9999) 
            {
                System.out.println("PIN must contain 4 digits.");
                return;
            }

            System.out.print("Enter initial deposit (minimum Rs. 500): ");
            double amount = sc.nextDouble();

            if (!Double.isFinite(amount) || amount < 500) 
            {
                System.out.println("Initial deposit must be at least Rs. 500.");
                return;
            }

            accounts.put(number,
                new BankAccount(number, name, pin, amount));

            System.out.println("Account created successfully!");
        } 
        catch (InputMismatchException e) 
        {
            System.out.println("Invalid input. Please try again.");
            sc.nextLine();
        }
    }

    private static void login() 
    {
        try 
        {
            System.out.print("Enter account number: ");
            long number = sc.nextLong();

            BankAccount account = accounts.get(number);

            if (account == null) 
            {
                System.out.println("Account not found.");
                return;
            }

            System.out.print("Enter PIN: ");
            int enteredPin = sc.nextInt();

            if (!account.verifyPin(enteredPin)) 
            {
                System.out.println("Incorrect PIN.");
                return;
            }

            System.out.println("Welcome, " + account.getHolderName() + "!");
            accountMenu(account);

        } 
        catch (InputMismatchException e) 
        {
            System.out.println("Invalid input. Please enter numbers.");
            sc.nextLine();
        }
    }

    private static void accountMenu(BankAccount account) 
    {
        while (true) 
            {
            System.out.println("\n--- ACCOUNT MENU ---");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Balance Inquiry");
            System.out.println("4. Transaction History");
            System.out.println("5. Logout");
            System.out.print("Enter choice: ");

            try 
            {
                int choice = sc.nextInt();

                switch (choice) 
                {
                    case 1:
                        System.out.print("Enter deposit amount: ");
                        double deposit = sc.nextDouble();
                        account.deposit(deposit);
                        break;

                    case 2:
                        System.out.print("Enter withdrawal amount: ");
                        double withdraw = sc.nextDouble();
                        account.withdraw(withdraw);
                        break;

                    case 3:
                        account.showBalance();
                        break;

                    case 4:
                        account.showTransactions();
                        break;

                    case 5:
                        System.out.println("Logged out successfully.");
                        return;

                    default:
                        System.out.println("Invalid menu choice.");
                }

            } 
            catch (InputMismatchException e) 
            {
                System.out.println("Invalid input! Enter a valid number.");
                sc.nextLine();
            } 
            catch (IllegalArgumentException e) 
            {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}