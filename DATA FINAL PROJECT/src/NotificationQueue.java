public class NotificationQueue {

    private class Node {
        private String notification;
        private Node next;

        public Node(String notification) {
            this.notification = notification;
            this.next = null;
        }
    }

    private Node front;
    private Node rear;

    public NotificationQueue() {
        this.front = null;
        this.rear = null;
    }

    public void enqueue(String notification) {
        Node newNode = new Node(notification);
        if (front == null && rear == null) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
    }

    public boolean isEmpty() {
        return front == null;
    }

    public String dequeue() {
        if (isEmpty()) {
            System.out.println("Queue is Empty");
        }
        String notification = front.notification;
        front = front.next;
        if (front == null) {
            rear = null;
        }
        return notification;
    }

    public String peek() {
        if (isEmpty()) {
            System.out.println("Queue is Empty");
        }
        return front.notification;
    }
    public void viewNotifications() {
        if (isEmpty()) {
            System.out.println("No notifications.");
            return;
        }

        System.out.println("Notifications:");
        Node current = front;
        while (current != null) {
            System.out.println("- " + current.notification);
            current = current.next;
        }
    }
}