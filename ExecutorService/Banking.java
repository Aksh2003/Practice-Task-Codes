import java.util.*;
import java.util.concurrent.*;

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
        if (amount > MAX_WITHDRAWAL_AMOUNT) {
            throw new MaxWithdrawalException("Withdrawal amount exceeds the maximum limit for Savings Account.");
        }
        if (amount > balance) {
            throw new InsufficientBalanceException("Insufficient balance.");
        }
        balance -= amount;
        transactionHistory.add(new Transaction(new Date(), "Withdrawal", amount));
    }

    public void calculateInterest() {
        balance += 50; // Simplified interest calculation for demonstration
        System.out.println("Interest calculated. New balance: " + balance);
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
    private static final int SESSION_DURATION_SECONDS = 10;
    private static final int PRINT_INTERVAL_SECONDS = 2;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(1);
        
        Map<String, Account> accounts = new HashMap<>();
        accounts.put("12345", new SavingsAccount("Person1", "12345", 7000.00));
        accounts.put("56890", new CurrentAccount("Person2", "56890", 8000.00));
        
        try {
            System.out.print("Enter account number: ");
            String accountNumber = scanner.nextLine().trim();

            Account account = accounts.get(accountNumber);
            if (account == null) {
                System.out.println("Invalid account number.");
                return;
            }

            Runnable countdownTask = () -> {
                for (int i = SESSION_DURATION_SECONDS; i > 0; i -= PRINT_INTERVAL_SECONDS) {
                    System.out.println(i + " seconds left.");
                    try {
                        Thread.sleep(PRINT_INTERVAL_SECONDS * 1000);
                    } catch (InterruptedException e) {
                        
                      e.printStackTrace();
                      break;
                    }
                }
                System.out.println("Session ended.");
            };

            ScheduledFuture<?> countdownFuture = scheduledExecutor.scheduleAtFixedRate(countdownTask, 0, PRINT_INTERVAL_SECONDS, TimeUnit.SECONDS);

           
                System.out.println("Enter '1' to deposit, '2' to withdraw (any other key to skip): ");
                String choice = scanner.nextLine().trim();

                switch (choice) {
                    case "1":
                        System.out.print("Enter deposit amount: ");
                        double depositAmount = scanner.nextDouble();
                        scanner.nextLine(); 
                        account.deposit(depositAmount);
                        break;
                    case "2":
                        System.out.print("Enter withdrawal amount: ");
                        double withdrawAmount = scanner.nextDouble();
                        scanner.nextLine(); 
                        try {
                            account.withdraw(withdrawAmount);
                        } catch (InsufficientBalanceException | MaxWithdrawalException e) {
                            System.err.println(e.getMessage());
                        }
                        break;
                    default:
                        countdownFuture.cancel(false);
                        break;
                }  

            scheduledExecutor.shutdown();  
            scheduledExecutor.awaitTermination(10,TimeUnit.SECONDS);    
            account.printTransactionHistory();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
