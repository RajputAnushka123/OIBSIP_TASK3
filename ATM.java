package bean;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ATM {
    private Map<String, User> users;
    private User currentUser;

    public ATM() {
        users = new HashMap<>();
        users.put("Anu", new User("Anu", "1010", 1000.00));
        users.put("Sid", new User("Sid", "1234", 500.00));
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter User ID:");
            String userId = scanner.nextLine();
            System.out.println("Enter PIN:");
            String pin = scanner.nextLine();

            if (authenticate(userId, pin)) {
                currentUser = users.get(userId);
                handleUserOperations(scanner);
            } else {
                System.out.println("Invalid User ID or PIN. Please try again.");
            }
        }
    }

    private boolean authenticate(String userId, String pin) {
        User user = users.get(userId);
        return user != null && user.authenticate(pin);
    }

    private void handleUserOperations(Scanner scanner) {
        while (true) {
            System.out.println("ATM Menu:");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    showTransactionHistory();
                    break;
                case 2:
                    withdraw(scanner);
                    break;
                case 3:
                    deposit(scanner);
                    break;
                case 4:
                    transfer(scanner);
                    break;
                case 5:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void showTransactionHistory() {
        for (Transaction transaction : currentUser.getAccount().getTransactions()) {
            System.out.println(transaction);
        }
    }

    private void withdraw(Scanner scanner) {
        System.out.println("Enter amount to withdraw:");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        Account account = currentUser.getAccount();
        if (amount <= account.getBalance()) {
            account.setBalance(account.getBalance() - amount);
            account.addTransaction(new Transaction("Withdraw", amount));
            System.out.println("Withdrawal successful. New balance: " + account.getBalance());
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    private void deposit(Scanner scanner) {
        System.out.println("Enter amount to deposit:");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        Account account = currentUser.getAccount();
        account.setBalance(account.getBalance() + amount);
        account.addTransaction(new Transaction("Deposit", amount));
        System.out.println("Deposit successful. New balance: " + account.getBalance());
    }

    private void transfer(Scanner scanner) {
        System.out.println("Enter recipient User ID:");
        String recipientId = scanner.nextLine();
        System.out.println("Enter amount to transfer:");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        User recipient = users.get(recipientId);
        if (recipient != null) {
            Account senderAccount = currentUser.getAccount();
            Account recipientAccount = recipient.getAccount();

            if (amount <= senderAccount.getBalance()) {
                senderAccount.setBalance(senderAccount.getBalance() - amount);
                recipientAccount.setBalance(recipientAccount.getBalance() + amount);
                senderAccount.addTransaction(new Transaction("Transfer to " + recipientId, amount));
                recipientAccount.addTransaction(new Transaction("Transfer from " + currentUser.getUserId(), amount));
                System.out.println("Transfer successful. New balance: " + senderAccount.getBalance());
            } else {
                System.out.println("Insufficient balance.");
            }
        } else {
            System.out.println("Invalid recipient ID.");
        }
    }
}
