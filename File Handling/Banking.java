import java.io.*;
import java.util.*;

abstract class Account {
    protected String name;
    protected String accountNumber;
    protected double balance;
    protected List<Transaction> transactionHistory;
    protected String transactionFile; 

    public Account(String name, String accountNumber, double balance, String transactionFile) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.transactionFile = transactionFile;
        this.transactionHistory = new ArrayList<>();
        
    }

   

    public void deposit(double amount) {
        balance += amount;
        Transaction transaction = new Transaction(new Date(), "Deposit", amount);
        recordTransaction(transaction);
    }

    public abstract void withdraw(double amount) throws InsufficientBalanceException, MaxWithdrawalException;

    public void recordTransaction(Transaction transaction) {
        transactionHistory.add(transaction);
        try  {
            BufferedWriter writer = new BufferedWriter(new FileWriter(transactionFile, true));
            writer.write(transaction.toString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double getBalance() {
        return balance;
    }

    public void printTransactionHistory() {
        try (BufferedReader reader = new BufferedReader(new FileReader(transactionFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public abstract void displayAccountDetails();
}

class SavingsAccount extends Account {
    private static final double interest_rate = 0.05;

    private static final double MAX_WITHDRAWAL_AMOUNT = 2000.00;

    public SavingsAccount(String name, String accountNumber, double balance, String transactionFile) {
        super(name, accountNumber, balance, transactionFile);
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
            recordTransaction(new Transaction(new Date(), "Withdrawal", amount));
        }
        catch(InsufficientBalanceException |MaxWithdrawalException e )
        {
              System.out.println(e.getMessage());
        }
    }

    public void calculateInterest() {
        balance += 50; 
        Transaction transaction = new Transaction(new Date(), "Interest", 50);
        recordTransaction(transaction);
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

    public CurrentAccount(String name, String accountNumber, double balance, String transactionFile) {
        super(name, accountNumber, balance, transactionFile);
    }

    public void withdraw(double amount) throws InsufficientBalanceException, MaxWithdrawalException {
        if (amount > MAX_WITHDRAWAL_AMOUNT) {
            throw new MaxWithdrawalException("Withdrawal amount exceeds the maximum limit for Current Account.");
        }
        if (balance - amount < min_balance) {
            throw new InsufficientBalanceException("Minimum balance requirement not met.");
        }
        balance -= amount;
        recordTransaction(new Transaction(new Date(), "Withdrawal", amount));
    }

    @Override
    public void displayAccountDetails() {
        System.out.println("Current Account Details:");
        System.out.println("Name: " + name);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Balance: " + balance);
        System.out.println("Minimum Balance Requirement: " + min_balance);
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

    @Override
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
        SavingsAccount savingsAccount1 = new SavingsAccount("Person1", "12345", 7000.00, "savings_account1_transactions.txt");
        SavingsAccount savingsAccount2 = new SavingsAccount("Person2", "56789", 8000.00, "savings_account2_transactions.txt");

        CurrentAccount currentAccount1 = new CurrentAccount("Person3", "23456", 10000.00, "current_account1_transactions.txt");
        CurrentAccount currentAccount2 = new CurrentAccount("Person4", "67890", 12000.00, "current_account2_transactions.txt");

        try {
            savingsAccount1.deposit(5000);
            savingsAccount1.calculateInterest();
           // savingsAccount1.displayAccountDetails();
            savingsAccount1.printTransactionHistory();

            System.out.println();

            savingsAccount2.deposit(3000);
            savingsAccount2.calculateInterest();
         //   savingsAccount2.displayAccountDetails();
            savingsAccount2.printTransactionHistory();

            System.out.println();

            currentAccount1.deposit(2000);
            currentAccount1.withdraw(1500);
          //  currentAccount1.displayAccountDetails();
            currentAccount1.printTransactionHistory();

            System.out.println();

            currentAccount2.deposit(4000);
            currentAccount2.withdraw(3000);
          //  currentAccount2.displayAccountDetails();
            currentAccount2.printTransactionHistory();
        } catch (InsufficientBalanceException | MaxWithdrawalException e) {
            System.err.println(e.getMessage());
        }
    }
}
