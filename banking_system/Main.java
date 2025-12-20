package banking_system;

import accounts.*;
import notifications.*;
import transactions.*;
import interest.*;

public class Main {

    public static void main(String[] args) {

        BankFacade bank = new BankFacade();

        // ADMIN: Open accounts
        bank.loginAsAdmin();
        Account savings = new SavingsAccount("S001", 1000);
        Account checking = new CheckingAccount("C001", 500);
        bank.openAccount(savings);
        bank.openAccount(checking);

        // OBSERVER
        bank.addObserver(new EmailNotifier());
        bank.addObserver(new SMSNotifier());

        // USER: Transactions
        bank.loginAsUser();
        bank.deposit("S001", 200);
        bank.withdraw("C001", 100);

        System.out.println("Balance S001 = " + bank.getAccountBalance("S001"));
        System.out.println("Balance C001 = " + bank.getAccountBalance("C001"));

        // COMPOSITE
        bank.loginAsAdmin();
        AccountGroup group = new AccountGroup("GROUP-1");
        bank.addAccountToGroup(group, savings);
        bank.addAccountToGroup(group, checking);

        System.out.println("Group total balance = " + bank.getGroupBalance(group));
        group.displayInfo();

        // CHAIN OF RESPONSIBILITY
        Transaction t1 = new Transaction("TX-01", 500);
        Transaction t2 = new Transaction("TX-02", 5000);
        bank.processTransaction(t1);
        bank.processTransaction(t2);

        // STRATEGY
        double interest = bank.calculateInterest("S001");
        System.out.println("Calculated Interest for S001 = " + interest);

        bank.setInterestStrategy(new CompoundInterestStrategy());
        double compoundInterest = bank.calculateInterest("S001");
        System.out.println("Calculated Compound Interest for S001 = " + compoundInterest);

        // DECORATOR
        System.out.println("\n--- Decorator Pattern Demo ---");
        Account acc = new SavingsAccount("S100", 500);
        Account feeAcc = new FeeAccountDecorator(acc, 5);
        feeAcc.withdraw(100);
        System.out.println("Final balance after fee = " + feeAcc.getBalance());
    }
}
