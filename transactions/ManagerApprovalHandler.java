package transactions;

public class ManagerApprovalHandler extends ApprovalHandler {

    @Override
    public void approve(Transaction transaction) {
        if (transaction.getAmount() <= 10000) {
            System.out.println("Transaction " + transaction.getTransactionId() +
                    " approved by Manager.");
        } else {
            System.out.println("Transaction " + transaction.getTransactionId() +
                    " requires higher approval!");
        }
    }
}
