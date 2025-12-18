package services;

import accounts.Account;
import java.util.HashMap;
import java.util.Map;
/**
 * Manages account creation and retrieval.
 */
public class AccountService {

    private Map<String, Account> accounts = new HashMap<>();
/**
     * Creates a new account.
     *
     * @param account the account to be created
     */
    public void create(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }
        accounts.put(account.getAccountId(), account);
    }

    /**
     * Retrieves an account by its ID.
     *
     * @param accountId the account ID
     * @return the account instance
     */
    public Account get(String accountId) {
        Account acc = accounts.get(accountId);
        if (acc == null) {
            throw new IllegalArgumentException("Account not found: " + accountId);
        }
        return acc;
    }

    /**
     * Returns the balance of a given account.
     *
     * @param accountId the account ID
     * @return account balance
     */
    public double getBalance(String accountId) {
        return get(accountId).getBalance();
    }
}