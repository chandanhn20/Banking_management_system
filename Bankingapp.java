import java.util.*;

interface LoanService {
    void applyForLoan(double amount);

}

abstract class BankAccount {
    private int accountNumber;
    private String accountHolderName;
    protected double balance;
    private String email;

    public BankAccount(int accountNumber, String accountHolderName, double balance, String email) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
        this.email = email;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Amount deposited :" + amount);
        }

    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            System.out.println("Amount withdrawn :" + amount);
        } else {
            System.out.println("Insufficient balance");
        }

    }

    public void showDetails() {
        System.out.println("Account Number :" + accountNumber);
        System.out.println("Account Holder Name :" + accountHolderName);
        System.out.println("Balance :" + balance);
        System.out.println("Email :" + email);
    }

    abstract void calculateInterest();

}

class SavingsAccount extends BankAccount {
    private double interestRate = 5.0;

    public SavingsAccount(int accNo, String name, double balance, String email) {
        super(accNo, name, balance, email);

    }

    @Override
    void calculateInterest() {
        double interest = balance * interestRate;
        System.out.println("Interest :" + interest);
    }

}

class CurrentAccount extends BankAccount {
    private double overDraftLimit = 10000;

    public CurrentAccount(int accNo, String name, double balance, String email) {
        super(accNo, name, balance, email);

    }

    @Override
    public void withdraw(double amount) {
        if (amount <= balance + overDraftLimit) {
            balance -= amount;
            System.out.println("Amount withdrawn :" + amount);
        } else {
            System.out.println("Overdraft limit exceeded");
        }
    }
    @Override
    void calculateInterest() {
        System.out.println("No interest rate for current account");
    }

}

class Bankingapp {

    static ArrayList<BankAccount> accounts = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        while (true) {
            System.out.println("=====  BANK MENU  =====\n1. Create Account\n2. Deposit\n3. Withdraw\n4. Show Details\n5. Calculate Interest\n6. Exit");
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
                    calculateInterest();
                    break;
                case 6:
                    System.out.println("Thank you for using our banking services");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice");
            }

            System.out.println("\n");
        }

    }
    public static void createAccount() {

        System.out.println("1. Savings Account");
        System.out.println("2. Current Account");
        int choice = scanner.nextInt();
        
        System.out.println("Enter account number : ");
        int accNo = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter Name : ");
        String name = scanner.nextLine();

        System.out.println("Enter Email : ");
        String email = scanner.nextLine().toLowerCase();

        if (!email.matches("^[a-z0-9+_.-]+@[a-z0-9.-]+$")) {
            System.out.println("Invalid email");
            return;
        }

        System.out.println("Enter Balance : ");
        double balance = scanner.nextDouble();
        scanner.nextLine();

        if (choice==1) {
            accounts.add(new SavingsAccount(accNo, name, balance, email));
        } else if (choice==2) {
            accounts.add(new CurrentAccount(accNo, name, balance, email));
        }
        else {
            System.out.println("Invalid choice");
            return;
        }
        System.out.println("Account created successfully");    
    }
    static BankAccount findAccount(int accNo) {
        for (BankAccount account : accounts) {
            if (account.getAccountNumber() == accNo) {
                return account;
            }
        }
        return null;
    }


    public static void deposit() {
        System.out.println("Enter account number : ");
        int accNo = scanner.nextInt();
        scanner.nextLine();
        BankAccount account = findAccount(accNo);
        if (account == null) {
            System.out.println("Account not found");
            return;
        }
        System.out.println("Enter amount to deposit : ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        account.deposit(amount);
        System.out.println("Amount deposited successfully");
    }
    public static void withdraw() {
        System.out.println("Enter account number : ");
        int accNo = scanner.nextInt();
        scanner.nextLine();
        BankAccount account = findAccount(accNo);
        if (account == null) {
            System.out.println("Account not found");
            return;
        }
        System.out.println("Enter amount to withdraw : ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        account.withdraw(amount);
        System.out.println("Amount withdrawn successfully");

    }
    public static void showDetails() {
        System.out.println("Enter account number : ");
        int accNo = scanner.nextInt();
        scanner.nextLine();
        BankAccount account = findAccount(accNo);
        if (account != null) {
            account.showDetails();
        }
        else {
            System.out.println("Account not found");
        }
       
    }
    public static void calculateInterest() {
        System.out.println("Enter account number : ");
        int accNo = scanner.nextInt();
        scanner.nextLine();
        BankAccount account = findAccount(accNo);
        if (account != null) {
            account.calculateInterest();
        }
        else {
            System.out.println("Account not found");
        }
    }
}
