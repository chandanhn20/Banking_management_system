import java.util.*;

/* ================= CUSTOM EXCEPTIONS ================= */

class InvalideEmailException extends Exception {
    public InvalideEmailException(String message) {
        super(message);
    }
}

class InvalidAmountException extends Exception {
    public InvalidAmountException(String message) {
        super(message);
    }
}

class InvalideAccountNumberException extends Exception {
    public InvalideAccountNumberException(String message) {
        super(message);
    }
}

class InvalidNameException extends Exception {
    public InvalidNameException(String message) {
        super(message);
    }
}

class InvalidBalanceException extends Exception {
    public InvalidBalanceException(String message) {
        super(message);
    }
}

/* ================= INTERFACE ================= */

interface LoanService {
    void applyForLoan(double amount);
}

/* ================= ABSTRACT CLASS ================= */

abstract class BankAccount {
    private int accountNumber;
    private String accountHolderName;
    protected double balance;
    private String email;

    public BankAccount(int accountNumber, String accountHolderName, double balance, String email)
            throws InvalideAccountNumberException, InvalidNameException,
            InvalidBalanceException, InvalideEmailException {

        if (accountNumber <= 0)
            throw new InvalideAccountNumberException("Account number must be positive");

        if (accountHolderName == null || !accountHolderName.matches("[A-Za-z ]+"))
            throw new InvalidNameException("Name must contain only alphabets");

        if (balance < 0)
            throw new InvalidBalanceException("Balance cannot be negative");

        if (!email.matches("^[a-z0-9+_.-]+@[a-z0-9.-]+$"))
            throw new InvalideEmailException("Invalid email format");

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
            throw new InvalidAmountException("Deposit amount must be greater than zero");

        balance += amount;
        System.out.println("Amount deposited: " + amount);
    }

    public void withdraw(double amount) throws InvalidAmountException {
        if (amount <= 0)
            throw new InvalidAmountException("Withdraw amount must be greater than zero");

        if (amount > balance)
            throw new InvalidAmountException("Insufficient balance");

        balance -= amount;
        System.out.println("Amount withdrawn: " + amount);
    }

    public void showDetails() {
        System.out.println("Account Number : " + accountNumber);
        System.out.println("Account Holder : " + accountHolderName);
        System.out.println("Balance        : " + balance);
        System.out.println("Email          : " + email);
    }

    abstract void calculateInterest();
}

/* ================= SAVINGS ACCOUNT ================= */

class SavingsAccount extends BankAccount {
    private final double interestRate = 0.05;

    public SavingsAccount(int accNo, String name, double balance, String email)
            throws InvalideAccountNumberException, InvalidNameException,
            InvalidBalanceException, InvalideEmailException {
        super(accNo, name, balance, email);
    }

    @Override
    void calculateInterest() {
        System.out.println("Interest: " + (balance * interestRate));
    }
}

/* ================= CURRENT ACCOUNT ================= */

class CurrentAccount extends BankAccount {
    private final double overdraftLimit = 10000;

    public CurrentAccount(int accNo, String name, double balance, String email)
            throws InvalideAccountNumberException, InvalidNameException,
            InvalidBalanceException, InvalideEmailException {
        super(accNo, name, balance, email);
    }

    @Override
    public void withdraw(double amount) throws InvalidAmountException {
        if (amount <= 0)
            throw new InvalidAmountException("Withdraw amount must be greater than zero");

        if (amount > balance + overdraftLimit)
            throw new InvalidAmountException("Overdraft limit exceeded");

        balance -= amount;
        System.out.println("Amount withdrawn: " + amount);
    }

    @Override
    void calculateInterest() {
        System.out.println("No interest for current account");
    }
}

/* ================= MAIN APPLICATION ================= */

public class Bankingapp{

    static ArrayList<BankAccount> accounts = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            System.out.println("""
                    ===== BANK MENU =====
                    1. Create Account
                    2. Deposit
                    3. Withdraw
                    4. Show Details
                    5. Calculate Interest
                    6. Exit
                    """);

            int choice = scanner.nextInt();

            try {
                switch (choice) {
                    case 1 -> createAccount();
                    case 2 -> deposit();
                    case 3 -> withdraw();
                    case 4 -> showDetails();
                    case 5 -> calculateInterest();
                    case 6 -> {
                        System.out.println("Thank you!");
                        System.exit(0);
                    }
                    default -> System.out.println("Invalid choice");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    /* ================= METHODS ================= */

    static void createAccount() {
        try {
            System.out.println("1. Savings Account");
            System.out.println("2. Current Account");
            int type = scanner.nextInt();

            System.out.print("Account Number: ");
            int accNo = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Name: ");
            String name = scanner.nextLine();

            System.out.print("Email: ");
            String email = scanner.nextLine().toLowerCase();

            System.out.print("Balance: ");
            double balance = scanner.nextDouble();

            if (type == 1)
                accounts.add(new SavingsAccount(accNo, name, balance, email));
            else if (type == 2)
                accounts.add(new CurrentAccount(accNo, name, balance, email));
            else {
                System.out.println("Invalid account type");
                return;
            }

            System.out.println("Account created successfully!");

        } catch (Exception e) {
            System.out.println("Account creation failed: " + e.getMessage());
        }
    }

    static BankAccount findAccount(int accNo) throws InvalideAccountNumberException {
        for (BankAccount acc : accounts)
            if (acc.getAccountNumber() == accNo)
                return acc;

        throw new InvalideAccountNumberException("Account not found");
    }

    static void deposit() throws Exception {
        System.out.print("Account Number: ");
        int accNo = scanner.nextInt();

        BankAccount acc = findAccount(accNo);

        System.out.print("Amount: ");
        double amount = scanner.nextDouble();

        acc.deposit(amount);
    }

    static void withdraw() throws Exception {
        System.out.print("Account Number: ");
        int accNo = scanner.nextInt();

        BankAccount acc = findAccount(accNo);

        System.out.print("Amount: ");
        double amount = scanner.nextDouble();

        acc.withdraw(amount);
    }

    static void showDetails() throws Exception {
        System.out.print("Account Number: ");
        int accNo = scanner.nextInt();

        findAccount(accNo).showDetails();
    }

    static void calculateInterest() throws Exception {
        System.out.print("Account Number: ");
        int accNo = scanner.nextInt();

        findAccount(accNo).calculateInterest();
    }
}