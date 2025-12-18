package notifications;

public class SMSNotifier implements NotificationObserver {

    @Override
    public void update(String message) {
        System.out.println("SMS Notification: " + message);
    }
}
