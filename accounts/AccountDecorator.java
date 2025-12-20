package accounts;

/**
 * Abstract Decorator for Account.
 * Allows adding new behaviors to accounts dynamically
 * without modifying existing account classes.
 */
public abstract class AccountDecorator extends Account {

    protected Account wrappedAccount;

    public AccountDecorator(Account account) {
        super(account.getAccountId(), account.getBalance());
        this.wrappedAccount = account;
    }

    @Override
    public void deposit(double amount) {
        wrappedAccount.deposit(amount);
    }

    @Override
    public void withdraw(double amount) {
        wrappedAccount.withdraw(amount);
    }

    @Override
    public double getBalance() {
        return wrappedAccount.getBalance();
    }

    @Override
    public void displayInfo() {
        wrappedAccount.displayInfo();
    }
}
