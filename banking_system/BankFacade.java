package banking_system;

import accounts.*;
import services.*;
import transactions.*;
import notifications.*;
import interest.*;

import java.util.ArrayList;
import java.util.List;

/**
 * BankFacade provides a unified interface to the banking system.
 * It coordinates multiple services and design patterns including:
 * Facade, Composite, Strategy, Chain of Responsibility, and Observer.
 *
 * Security checks are enforced at this level to control access
 * to sensitive banking operations.
 */
public class BankFacade {

     // Security Role
    private String currentUserRole = "USER"; // Default role for demo/testing

    /**
     * Sets the role of the current user session.
     * @param role "USER" or "ADMIN"
     */
    public void setUserRole(String role) {
        this.currentUserRole = role;
    }

     // Services
    private AccountService accountService;
    private AccountTransactionService transactionService;

     // Design Patterns
    private ApprovalHandler approvalChain;
    private InterestStrategy interestStrategy;
    private List<NotificationObserver> observers;

    /**
     * Initializes the banking system facade.
     * Sets up shared services, approval chain, default interest strategy,
     * notification observers, and default security role.
     */
    public BankFacade() {

        // Shared services
        accountService = new AccountService();
        transactionService = new AccountTransactionService(accountService);

        // Chain of Responsibility
        ApprovalHandler auto = new AutoApprovalHandler();
        ApprovalHandler manager = new ManagerApprovalHandler();
        auto.setNext(manager);
        approvalChain = auto;

        // Default interest strategy
        interestStrategy = new SimpleInterestStrategy();

        // Observer list
        observers = new ArrayList<>();

        // Default role already set above
    }

     // Security Methods
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

     // Composite (Account Group)
    public void addAccountToGroup(AccountGroup group, Account account) {
        checkAccess("ADMIN");
        group.add(account);
    }

    public double getGroupBalance(AccountGroup group) {
        return group.getBalance();
    }

     // Interest (Strategy)
    public void setInterestStrategy(InterestStrategy strategy) {
        checkAccess("ADMIN");
        this.interestStrategy = strategy;
    }

    public double calculateInterest(String accountId) {
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
