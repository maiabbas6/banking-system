package transactions;

public abstract class ApprovalHandler {

    protected ApprovalHandler nextHandler;

    public void setNext(ApprovalHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public abstract void approve(Transaction transaction);
}
