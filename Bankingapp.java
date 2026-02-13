import java.util.*;

interface LoanService {
    void applyForLoan(double amount);
}

class BankAccount {
    private int accountNumber;
    private String holderName;
    private double balance;
    private String email;

    public BankAccount(int accountNumber, String holderName, double balance, String email) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
        this.email = email;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: " + amount);
        }
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn: " + amount);
        } else {
            System.out.println("Insufficient funds");
        }
    }

    public void showDetails() {
        System.out.println("Account Holder: " + holderName);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Email: " + email);
        System.out.println("Balance: " + balance);
    }
}

// ---------------- Banking App ----------------
class BankingApp {
    static ArrayList<BankAccount> accounts = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Show Account Details");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    deposit();
                    break;
                case 3:
                    withdraw();
                    break;
                case 4:
                    showDetails();
                    break;
                case 5:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    static void createAccount() {
        System.out.print("Account No: ");
        int accNo = scanner.nextInt();
        System.out.print("Name: ");
        String name = scanner.next();
        System.out.print("Balance: ");
        double balance = scanner.nextDouble();
        System.out.print("Email: ");
        String email = scanner.next();

        accounts.add(new BankAccount(accNo, name, balance, email));
        System.out.println("Account created successfully!");
    }

    static BankAccount findAccount(int accNo) {
        for (BankAccount acc : accounts) {
            if (acc.getAccountNumber() == accNo) {
                return acc;
            }
        }
        return null;
    }

    static void deposit() {
        System.out.print("Account No: ");
        int accNo = scanner.nextInt();
        BankAccount acc = findAccount(accNo);
        if (acc != null) {
            System.out.print("Amount: ");
            acc.deposit(scanner.nextDouble());
        } else {
            System.out.println("Account not found");
        }
    }

    static void withdraw() {
        System.out.print("Account No: ");
        int accNo = scanner.nextInt();
        BankAccount acc = findAccount(accNo);
        if (acc != null) {
            System.out.print("Amount: ");
            acc.withdraw(scanner.nextDouble());
        } else {
            System.out.println("Account not found");
        }
    }

    static void showDetails() {
        System.out.print("Account No: ");
        int accNo = scanner.nextInt();
        BankAccount acc = findAccount(accNo);
        if (acc != null) {
            acc.showDetails();
        } else {
            System.out.println("Account not found");
        }
    }
}