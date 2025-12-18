package notifications;

public class EmailNotifier implements NotificationObserver {

    @Override
    public void update(String message) {
        System.out.println("Email Notification: " + message);
    }
}
