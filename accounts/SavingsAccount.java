package accounts;

public class SavingsAccount extends Account {

    public SavingsAccount(String accountId, double balance) {
        super(accountId, balance);
    }

    @Override
    public void deposit(double amount) {
       
        balance += amount;
    }

    @Override
    public void withdraw(double amount) {
        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        balance -= amount;
    }

    @Override
    public void displayInfo() {
        System.out.println("Savings Account - ID: " + accountId + " | Balance: " + balance);
    }
}
