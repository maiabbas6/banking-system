package accounts;

/**
 * Concrete Decorator that adds transaction fees to an account.
 */
public class FeeAccountDecorator extends AccountDecorator {

    private double fee;

    public FeeAccountDecorator(Account account, double fee) {
        super(account);
        this.fee = fee;
    }

    @Override
    public void withdraw(double amount) {
        double totalAmount = amount + fee;
        System.out.println("Applying fee: " + fee);
        wrappedAccount.withdraw(totalAmount);
    }

    @Override
    public void displayInfo() {
        wrappedAccount.displayInfo();
        System.out.println("This account has a transaction fee of: " + fee);
    }
}
