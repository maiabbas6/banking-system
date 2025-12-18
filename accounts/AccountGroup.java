package accounts;

import java.util.ArrayList;
import java.util.List;

public class AccountGroup extends Account {

    private List<Account> accounts = new ArrayList<>();

    public AccountGroup(String groupId) {
        super(groupId, 0.0);
    }

    public void add(Account account) {
        accounts.add(account);
    }

    public void remove(Account account) {
        accounts.remove(account);
    }

    //Composite balance
    @Override
    public double getBalance() {
        double total = 0.0;
        for (Account acc : accounts) {
            total += acc.getBalance();
        }
        return total;
    }

    @Override
    public void deposit(double amount) {
        for (Account acc : accounts) {
            acc.deposit(amount);
        }
    } 

    
    @Override
    public void withdraw(double amount) {
        for (Account acc : accounts) {
            acc.withdraw(amount);
        }
    }

    @Override
    public void displayInfo() {
        System.out.println("\n[Account Group] ID: " + accountId);
        for (Account acc : accounts) {
            acc.displayInfo();
        }
    }
}
