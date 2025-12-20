package banking_system;

import accounts.*;
import services.*;
import transactions.*;
import notifications.*;
import interest.*;

import java.util.ArrayList;
import java.util.List;

public class BankFacade {

    private String currentUserRole = "USER"; // Default role

    private AccountService accountService;
    private AccountTransactionService transactionService;

    private ApprovalHandler approvalChain;
    private InterestStrategy interestStrategy;
    private List<NotificationObserver> observers;

    public BankFacade() {
        accountService = new AccountService();
        transactionService = new AccountTransactionService(accountService);

        ApprovalHandler auto = new AutoApprovalHandler();
        ApprovalHandler manager = new ManagerApprovalHandler();
        auto.setNext(manager);
        approvalChain = auto;

        interestStrategy = new SimpleInterestStrategy();
        observers = new ArrayList<>();
    }

    // Login Methods
    public void loginAsAdmin() {
        currentUserRole = "ADMIN";
        System.out.println("Logged in as ADMIN");
    }

    public void loginAsUser() {
        currentUserRole = "USER";
        System.out.println("Logged in as USER");
    }

    // Security check
    private void checkAccess(String requiredRole) {
        if (!requiredRole.equals(currentUserRole)) {
            throw new SecurityException(
                "Access denied: " + requiredRole + " role required."
            );
        }
    }

    // Account Operations
    public void openAccount(Account account) {
        checkAccess("ADMIN");
        accountService.create(account);
        notifyObservers("Account opened: " + account.getAccountId());
    }

    public double getAccountBalance(String accountId) {
        // Allow USER or ADMIN
        if (!currentUserRole.equals("USER") && !currentUserRole.equals("ADMIN")) {
            throw new SecurityException("Access denied: USER or ADMIN role required.");
        }
        return accountService.getBalance(accountId);
    }

    // Transaction Operations
    public void deposit(String accountId, double amount) {
        checkAccess("USER");
        transactionService.deposit(accountId, amount);
        notifyObservers("Deposit of " + amount + " to account " + accountId);
    }

    public void withdraw(String accountId, double amount) {
        checkAccess("USER");
        transactionService.withdraw(accountId, amount);
        notifyObservers("Withdrawal of " + amount + " from account " + accountId);
    }

    public void processTransaction(Transaction transaction) {
        checkAccess("ADMIN");
        approvalChain.approve(transaction);
        notifyObservers("Transaction processed: " + transaction.getTransactionId());
    }

    // Composite
    public void addAccountToGroup(AccountGroup group, Account account) {
        checkAccess("ADMIN");
        group.add(account);
    }

    public double getGroupBalance(AccountGroup group) {
        // Allow USER or ADMIN
        if (!currentUserRole.equals("USER") && !currentUserRole.equals("ADMIN")) {
            throw new SecurityException("Access denied: USER or ADMIN role required.");
        }
        return group.getBalance();
    }

    // Interest (Strategy)
    public void setInterestStrategy(InterestStrategy strategy) {
        checkAccess("ADMIN");
        this.interestStrategy = strategy;
    }

    public double calculateInterest(String accountId) {
        // Allow USER or ADMIN
        if (!currentUserRole.equals("USER") && !currentUserRole.equals("ADMIN")) {
            throw new SecurityException("Access denied: USER or ADMIN role required.");
        }
        double balance = accountService.getBalance(accountId);
        return interestStrategy.calculate(balance);
    }

    // Observer
    public void addObserver(NotificationObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers(String message) {
        for (NotificationObserver observer : observers) {
            observer.update(message);
        }
    }
}
