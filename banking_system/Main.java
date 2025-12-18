package banking_system;

import accounts.*;
import notifications.*;
import transactions.*;
import interest.*;

public class Main {

    public static void main(String[] args) {

        BankFacade bank = new BankFacade();


        bank.setUserRole("ADMIN");  

        Account savings = new SavingsAccount("S001", 1000);
        Account checking = new CheckingAccount("C001", 500);

        bank.openAccount(savings);
        bank.openAccount(checking);


        bank.addObserver(new EmailNotifier());
        bank.addObserver(new SMSNotifier());

     
        bank.setUserRole("USER");  

        bank.deposit("S001", 200);   // Balance = 1200
        bank.withdraw("C001", 100);  // Balance = 400

        System.out.println("Balance S001 = " + bank.getAccountBalance("S001"));
        System.out.println("Balance C001 = " + bank.getAccountBalance("C001"));

        bank.setUserRole("ADMIN"); 
        AccountGroup group = new AccountGroup("GROUP-1");
        bank.addAccountToGroup(group, savings);
        bank.addAccountToGroup(group, checking);

        System.out.println("Group total balance = " + bank.getGroupBalance(group));
        group.displayInfo();

       
        bank.setUserRole("ADMIN");
        Transaction t1 = new Transaction("TX-01", 500);
        Transaction t2 = new Transaction("TX-02", 5000);

        bank.processTransaction(t1);
        bank.processTransaction(t2);

        
        bank.setUserRole("ADMIN"); 
        bank.setInterestStrategy(new CompoundInterestStrategy());
        double interest = bank.calculateInterest("S001");
        System.out.println("Calculated Interest for S001 = " + interest);

        
    }
}
