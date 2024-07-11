import java.util.*;
 abstract class Account {
    protected String name;
    protected String accountNumber;
    protected double balance;
    protected List<Transaction> transactionHistory;

    public Account(String name, String accountNumber, double balance) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.transactionHistory = new ArrayList<>();
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add(new Transaction(new Date(), "Deposit", amount));
    }

    public abstract void withdraw(double amount) throws InsufficientBalanceException, MaxWithdrawalException;

    public double getBalance() {
        return balance;
    }

    public void printTransactionHistory() {
        for (Transaction t : transactionHistory) {
            System.out.println(t);
        }
    }

    public abstract void displayAccountDetails();
}

class SavingsAccount extends Account {
    private static final double interest_rate = 0.05;
    private static final double MAX_WITHDRAWAL_AMOUNT = 2000.00;
    public SavingsAccount(String name, String accountNumber, double balance) {
        super(name, accountNumber, balance);
    }
    public void withdraw(double amount) throws InsufficientBalanceException, MaxWithdrawalException {
        try{
            if (amount > MAX_WITHDRAWAL_AMOUNT) {
                throw new MaxWithdrawalException("Withdrawal amount exceeds the maximum limit for Savings Account.");
            }
            if (amount > balance) {
                throw new InsufficientBalanceException("Insufficient balance.");
            }
            balance -= amount;
            transactionHistory.add(new Transaction(new Date(), "Withdrawal", amount));
        }
        catch(InsufficientBalanceException |MaxWithdrawalException e )
        {
              System.out.println(e.getMessage());
        }
       
    }

    public void calculateInterest() {
        balance += 50; 
        System.out.println(balance);
    }

    @Override
    public void displayAccountDetails() {
        System.out.println("Savings Account Details:");
        System.out.println("Name: " + name);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Balance: " + balance);
        System.out.println("Interest Rate: " + interest_rate);
    }
}

class CurrentAccount extends Account {
    private static final double min_balance = 1000.00;
    private static final double MAX_WITHDRAWAL_AMOUNT = 5000.00;
    public CurrentAccount(String name, String accountNumber, double balance) {
        super(name, accountNumber, balance);
    }


    public void withdraw(double amount) throws InsufficientBalanceException, MaxWithdrawalException {
        if (amount > MAX_WITHDRAWAL_AMOUNT) {
            throw new MaxWithdrawalException("Withdrawal amount exceeds the maximum limit for Current Account.");
        }
        if (balance - amount < min_balance) {
            throw new InsufficientBalanceException("Minimum balance requirement not met.");
        }
        balance -= amount;
        transactionHistory.add(new Transaction(new Date(), "Withdrawal", amount));
    }

   
    public void displayAccountDetails() {
        System.out.println("Current Account Details:");
        System.out.println("Name: " + name);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Balance: " + balance);
        System.out.println("Minimum Balance Requirement: " + min_balance );
    }
}

class Transaction {
    private Date date;
    private String type;
    private double amount;

    public Transaction(Date date, String type, double amount) {
        this.date = date;
        this.type = type;
        this.amount = amount;
    }

   
    public String toString() {
        return "Date: " + date + ", Type: " + type + ", Amount: " + amount;
    }
}
class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}

class MaxWithdrawalException extends Exception {
    public MaxWithdrawalException(String message) {
        super(message);
    }
}
public class Banking {
    public static void main(String[] args) {
        SavingsAccount savingsAccount = new SavingsAccount("Person1", "12345", 7000.00);
        CurrentAccount currentAccount = new CurrentAccount("Person2", "56890", 8000.00);
        try {
            savingsAccount.deposit(5000);
            savingsAccount.withdraw(100);
            savingsAccount.calculateInterest();
            savingsAccount.displayAccountDetails();
            savingsAccount.printTransactionHistory();

            System.out.println();

            currentAccount.deposit(1000);
            currentAccount.withdraw(1500);
            currentAccount.withdraw(3000); 
            currentAccount.displayAccountDetails();
            currentAccount.printTransactionHistory();
        } catch (InsufficientBalanceException | MaxWithdrawalException e) {
            System.err.println(e.getMessage());
        }
    }
}