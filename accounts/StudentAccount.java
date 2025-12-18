package accounts;

public class StudentAccount extends Account {

    public StudentAccount(String accountId, double balance) {
        super(accountId, balance);
    }

    @Override
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid deposit amount");
        }
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
        System.out.println(
            "Student Account | ID: " + accountId + " | Balance: " + balance
        );
    }
}
