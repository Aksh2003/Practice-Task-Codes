//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import java.sql.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose operation:");
        System.out.println("1. New Customer (Insert Data)");
        System.out.println("2. Withdraw");
        System.out.println("3. Deposit");
        System.out.println("4. Delete Account");
        System.out.println("5. Check Balance");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                insertData();
                break;
            case 2:
                withdraw();
                break;
            case 3:
                deposit();
                break;
            case 4:
                deleteAccount();
                break;
            case 5:
                checkBalance();
                break;
            default:
                System.out.println("Invalid choice");
        }
    }

    private static void insertData() {
        try {
            Connection conn = DatabaseConnection.INSTANCE.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO account (name, accountNumber, balance) VALUES (?, ?, ?)");
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter name: ");
            String name = scanner.nextLine();
            System.out.print("Enter account number: ");
            int accountNumber = scanner.nextInt();
            System.out.print("Enter initial balance: ");
            double balance = scanner.nextDouble();

            pstmt.setString(1, name);
            pstmt.setInt(2, accountNumber);
            pstmt.setDouble(3, balance);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("New customer data inserted successfully");
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void withdraw() {
        try {
            Connection conn = DatabaseConnection.INSTANCE.getConnection();
            PreparedStatement pstmtSelect = conn.prepareStatement("SELECT balance FROM account WHERE accountNumber = ?");
            PreparedStatement pstmtUpdate = conn.prepareStatement("UPDATE account SET balance = ? WHERE accountNumber = ?");
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter account number: ");
            int accountNumber = scanner.nextInt();
            System.out.print("Enter withdrawal amount: ");
            double withdrawalAmount = scanner.nextDouble();

            pstmtSelect.setInt(1, accountNumber);
            ResultSet rs = pstmtSelect.executeQuery();
            if (rs.next()) {
                double currentBalance = rs.getDouble("balance");
                if (currentBalance >= withdrawalAmount) {
                    double newBalance = currentBalance - withdrawalAmount;
                    pstmtUpdate.setDouble(1, newBalance);
                    pstmtUpdate.setInt(2, accountNumber);
                    int rowsUpdated = pstmtUpdate.executeUpdate();
                    if (rowsUpdated > 0) {
                        System.out.println("Withdrawal successful");
                    }
                } else {
                    System.out.println("Insufficient balance");
                }
            } else {
                System.out.println("Account not found");
            }
            pstmtSelect.close();
            pstmtUpdate.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deposit() {
        try {
            Connection conn = DatabaseConnection.INSTANCE.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("UPDATE account SET balance = balance + ? WHERE accountNumber = ?");
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter account number: ");
            int accountNumber = scanner.nextInt();
            System.out.print("Enter deposit amount: ");
            double depositAmount = scanner.nextDouble();

            pstmt.setDouble(1, depositAmount);
            pstmt.setInt(2, accountNumber);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Deposit successful");
            } else {
                System.out.println("Account not found");
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteAccount() {
        try {
            Connection conn = DatabaseConnection.INSTANCE.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM account WHERE accountNumber = ?");
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter account number to delete: ");
            int accountNumber = scanner.nextInt();

            pstmt.setInt(1, accountNumber);

            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Account deleted successfully");
            } else {
                System.out.println("Account not found");
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void checkBalance() {
        try {
            Connection conn = DatabaseConnection.INSTANCE.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT balance FROM account WHERE accountNumber = ?");
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter account number: ");
            int accountNumber = scanner.nextInt();

            pstmt.setInt(1, accountNumber);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                double balance = rs.getDouble("balance");
                System.out.println("Current balance: " + balance);
            } else {
                System.out.println("Account not found");
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
