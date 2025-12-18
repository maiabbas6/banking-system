package banking_system;

import accounts.*;
import notifications.*;
import transactions.*;
import interest.*;

public class Main {

    public static void main(String[] args) {

        BankFacade bank = new BankFacade();

        // -------------------
        // 1️⃣ فتح الحسابات (Admin)
        // -------------------
       

        bank.setUserRole("ADMIN");  // فتح الحسابات يحتاج ADMIN

        Account savings = new SavingsAccount("S001", 1000);
        Account checking = new CheckingAccount("C001", 500);

        bank.openAccount(savings);
        bank.openAccount(checking);

        // -------------------
        // 2️⃣ إضافة Notifications (لا يهم الدور)
        // -------------------
        bank.addObserver(new EmailNotifier());
        bank.addObserver(new SMSNotifier());

        // -------------------
        // 3️⃣ العمليات اليومية (User)
        // -------------------
        bank.setUserRole("USER");  // Deposit & Withdraw يحتاج USER

        bank.deposit("S001", 200);   // Balance = 1200
        bank.withdraw("C001", 100);  // Balance = 400

        System.out.println("Balance S001 = " + bank.getAccountBalance("S001"));
        System.out.println("Balance C001 = " + bank.getAccountBalance("C001"));

        // -------------------
        // 4️⃣ Composite Pattern
        // -------------------
        bank.setUserRole("ADMIN"); // إضافة حسابات للمجموعة يحتاج ADMIN
        AccountGroup group = new AccountGroup("GROUP-1");
        bank.addAccountToGroup(group, savings);
        bank.addAccountToGroup(group, checking);

        System.out.println("Group total balance = " + bank.getGroupBalance(group));
        group.displayInfo();

        // -------------------
        // 5️⃣ Chain of Responsibility (Transactions)
        // -------------------
        bank.setUserRole("ADMIN"); // Approval يحتاج ADMIN
        Transaction t1 = new Transaction("TX-01", 500);
        Transaction t2 = new Transaction("TX-02", 5000);

        bank.processTransaction(t1);
        bank.processTransaction(t2);

        // -------------------
        // 6️⃣ Strategy Pattern (Interest)
        // -------------------
        bank.setUserRole("ADMIN"); // تعديل الاستراتيجية يحتاج ADMIN
        bank.setInterestStrategy(new CompoundInterestStrategy());
        double interest = bank.calculateInterest("S001");
        System.out.println("Calculated Interest for S001 = " + interest);

        
    }
}
