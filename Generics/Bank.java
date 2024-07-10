import java.util.*;

public class Bank<T extends Account> {
    private Map<Integer, T> accounts;

    public Bank() {
        this.accounts = new HashMap<>();
    }

    public void add(T account) {
        accounts.put(account.getAccountNumber(), account);
    }

    public void remove(int accountNumber) {
        if (accounts.containsKey(accountNumber)) {
            accounts.remove(accountNumber);
        } else {
            System.out.println("Account not found: " + accountNumber);
        }
    }

    public T display(int accountNumber) {
        if (accounts.containsKey(accountNumber)) {
            T account = accounts.get(accountNumber);
            System.out.println(account);
            return account;
        } 
        return null;
    }

    public void printAccountsAboveBalance(double balanceThreshold) {
        System.out.println("Accounts with balance above " + balanceThreshold + ":");
        for (T account : accounts.values()) {
            if (account.getBalance() > balanceThreshold) {
                System.out.println(account);
            }
        }
    }
    
    public static <T> T[] printAndReturnArray(T[] array) {
        for (T element : array) {
            System.out.println(element);
        }
        return array;
    }
        public static void main(String[] args) {
        Savings s1 = new Savings(12, "person1", 2000.00, 0.05);
        Savings s2 = new Savings(13, "person2", 3000.00, 0.03);
        Savings s3 = new Savings(14, "person3", 4000.00, 0.02);

        Bank<Savings> b1 = new Bank<>();
        b1.add(s1);
        b1.add(s2);
        b1.add(s3);

        b1.display(12); 
        b1.remove(12);  
        b1.display(12); 

        b1.printAccountsAboveBalance(2500.00);

       
        Savings[] savingsArray = {s1, s2, s3};
        printAndReturnArray(savingsArray);

    }
}

class Account {
    protected int accountNumber;
    protected String owner;
    protected double balance;

    public Account(int accountNumber, String owner, double balance) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getOwner() {
        return owner;
    }

    public double getBalance() {
        return balance;
    }

    public String toString() {
        return "Account : " + accountNumber + " " + owner + " " + balance;
    }
}

class Savings extends Account {
    private final double interest;

    public Savings(int accountNumber, String owner, double balance, double interest) {
        super(accountNumber, owner, balance);
        this.interest = interest;
    }

    public double getInterest() {
        return interest;
    }

    public String toString() {
        return super.toString() + " interest rate: " + interest;
    }
}
