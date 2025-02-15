public class NotificationSystem {
    private NotificationQueue notificationsQueue;

    public NotificationSystem() {
        notificationsQueue = new NotificationQueue();
    }

    public void sendConfirmation(String customerName, int reservationID) {
        String notification = "Confirmation: Dear " + customerName +
                ", your reservation with ID " + reservationID + " has been confirmed.\n";
        notificationsQueue.enqueue(notification);
        System.out.println(notification);
    }

    public void notifyAvailability(String customerName, int tableCapacity) {
        String notification = "Notification: Dear " + customerName +
                ", a table for " + tableCapacity +" capacity is available now. We are waiting you to update some data\n";
        notificationsQueue.enqueue(notification);
        System.out.println(notification);
    }

    public void printNotification(){
        notificationsQueue.viewNotifications();
    }
}