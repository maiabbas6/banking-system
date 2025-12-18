package services;

import accounts.Account;

/**
 * Handles deposit and withdrawal transactions on accounts.
 */

public class AccountTransactionService {

    private AccountService accountService;

    
    public AccountTransactionService(AccountService accountService) {
        this.accountService = accountService;
    }


    /**
     * Deposits a specified amount into an account.
     *
     * @param accountId the target account ID
     * @param amount the amount to deposit
     */

     public synchronized void deposit(String accountId, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }

        try {
            Account acc = accountService.get(accountId);
            acc.deposit(amount);
        } catch (Exception e) {
            System.err.println("Deposit failed: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Withdraws a specified amount from an account.
     *
     * @param accountId the target account ID
     * @param amount the amount to withdraw
     */
    public synchronized void withdraw(String accountId, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdraw amount must be positive");
        }

        try {
            Account acc = accountService.get(accountId);
            acc.withdraw(amount);
        } catch (Exception e) {
            System.err.println("Withdrawal failed: " + e.getMessage());
            throw e;
        }
    }
}