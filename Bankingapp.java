import java.util.*;

// Custom Exceptions
class InvalidEmailException extends Exception {
    public InvalidEmailException(String message) {
        super(message);
    }
}

class InvalidAmountException extends Exception {
    public InvalidAmountException(String message) {
        super(message);
    }
}

class InvalidAccountException extends Exception {
    public InvalidAccountException(String message) {
        super(message);
    }
}

// Loan Interface
interface LoanService {
    void applyForLoan(double amount);
}

// Abstract Class
abstract class BankAccount implements LoanService {
    private int accountNumber;
    private String accountHolderName;
    protected double balance;
    private String email;

    public BankAccount(int accountNumber, String accountHolderName,
                       double balance, String email)
            throws InvalidEmailException, InvalidAmountException {

        if (!email.matches("^[a-z0-9+_.-]+@[a-z0-9.-]+$"))
            throw new InvalidEmailException("Invalid Email Format!");

        if (balance < 0)
            throw new InvalidAmountException("Initial balance cannot be negative!");

        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
        this.email = email;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void deposit(double amount) throws InvalidAmountException {
        if (amount <= 0)
            throw new InvalidAmountException("Deposit amount must be positive!");

        balance += amount;
        System.out.println("Amount deposited: " + amount);
    }

    public void withdraw(double amount) throws InvalidAmountException {
        if (amount <= 0)
            throw new InvalidAmountException("Withdraw amount must be positive!");

        if (amount > balance)
            throw new InvalidAmountException("Insufficient balance!");

        balance -= amount;
        System.out.println("Amount withdrawn: " + amount);
    }

    public void showDetails() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder: " + accountHolderName);
        System.out.println("Balance: " + balance);
        System.out.println("Email: " + email);
    }

    public void applyForLoan(double amount) {
        System.out.println("Loan of " + amount + " applied successfully.");
    }

    abstract void calculateInterest();
}

// Savings Account
class SavingsAccount extends BankAccount {
    private double interestRate = 0.05;

    public SavingsAccount(int accNo, String name,
                          double balance, String email)
            throws InvalidEmailException, InvalidAmountException {
        super(accNo, name, balance, email);
    }

    @Override
    void calculateInterest() {
        double interest = balance * interestRate;
        System.out.println("Interest: " + interest);
    }
}

// Current Account
class CurrentAccount extends BankAccount {
    private double overdraftLimit = 10000;

    public CurrentAccount(int accNo, String name,
                          double balance, String email)
            throws InvalidEmailException, InvalidAmountException {
        super(accNo, name, balance, email);
    }

    @Override
    public void withdraw(double amount) throws InvalidAmountException {
        if (amount <= 0)
            throw new InvalidAmountException("Withdraw amount must be positive!");

        if (amount > balance + overdraftLimit)
            throw new InvalidAmountException("Overdraft limit exceeded!");

        balance -= amount;
        System.out.println("Amount withdrawn: " + amount);
    }

    @Override
    void calculateInterest() {
        System.out.println("No interest for Current Account.");
    }
}

// Main Application
class BankingApp {

    static ArrayList<BankAccount> accounts = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            try {
                System.out.println("===== BANK MENU =====");
                System.out.println("1. Create Account");
                System.out.println("2. Deposit");
                System.out.println("3. Withdraw");
                System.out.println("4. Show Details");
                System.out.println("5. Calculate Interest");
                System.out.println("6. Exit");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1: createAccount(); break;
                    case 2: deposit(); break;
                    case 3: withdraw(); break;
                    case 4: showDetails(); break;
                    case 5: calculateInterest(); break;
                    case 6:
                        System.out.println("Thank you!");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice!");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    static void createAccount() throws Exception {

        System.out.println("1. Savings\n2. Current");
        int type = scanner.nextInt();

        System.out.print("Account Number: ");
        int accNo = scanner.nextInt();
        scanner.nextLine();

        if (findAccount(accNo) != null)
            throw new InvalidAccountException("Account already exists!");

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine().toLowerCase();

        System.out.print("Initial Balance: ");
        double balance = scanner.nextDouble();

        if (type == 1)
            accounts.add(new SavingsAccount(accNo, name, balance, email));
        else if (type == 2)
            accounts.add(new CurrentAccount(accNo, name, balance, email));
        else
            throw new InvalidAccountException("Invalid account type!");

        System.out.println("Account created successfully!");
    }

    static BankAccount findAccount(int accNo) {
        for (BankAccount acc : accounts) {
            if (acc.getAccountNumber() == accNo)
                return acc;
        }
        return null;
    }

    static void deposit() throws Exception {
        System.out.print("Account Number: ");
        int accNo = scanner.nextInt();

        BankAccount acc = findAccount(accNo);
        if (acc == null)
            throw new InvalidAccountException("Account not found!");

        System.out.print("Amount: ");
        double amount = scanner.nextDouble();

        acc.deposit(amount);
    }

    static void withdraw() throws Exception {
        System.out.print("Account Number: ");
        int accNo = scanner.nextInt();

        BankAccount acc = findAccount(accNo);
        if (acc == null)
            throw new InvalidAccountException("Account not found!");

        System.out.print("Amount: ");
        double amount = scanner.nextDouble();

        acc.withdraw(amount);
    }

    static void showDetails() throws Exception {
        System.out.print("Account Number: ");
        int accNo = scanner.nextInt();

        BankAccount acc = findAccount(accNo);
        if (acc == null)
            throw new InvalidAccountException("Account not found!");

        acc.showDetails();
    }

    static void calculateInterest() throws Exception {
        System.out.print("Account Number: ");
        int accNo = scanner.nextInt();

        BankAccount acc = findAccount(accNo);
        if (acc == null)
            throw new InvalidAccountException("Account not found!");

        acc.calculateInterest();
    }
}