package transactions;

public class AutoApprovalHandler extends ApprovalHandler {

    @Override
    public void approve(Transaction transaction) {
        if (transaction.getAmount() <= 1000) {
            System.out.println("Transaction " + transaction.getTransactionId() +
                    " auto-approved.");
        } else if (nextHandler != null) {
            nextHandler.approve(transaction);
        }
    }
}
