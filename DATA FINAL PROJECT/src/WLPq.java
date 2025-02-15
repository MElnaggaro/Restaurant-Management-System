public class WLPq {
    private Reservation waitingLists[];
    private int size;
    private int capacity;

    public WLPq(int capacity) {
        this.capacity = capacity;
        this.size = 1;
        waitingLists = new Reservation[capacity + 1];
    }

    private int parent(int i) {
        return i / 2;
    }

    private int leftChild(int i) {
        return 2 * i;
    }

    private int rightChild(int i) {
        return 2 * i + 1;
    }

    private boolean isFull() {
        return size == capacity + 1;
    }

    public void insert(Reservation reservation) {
        if (isFull()) {
            System.out.println("Waiting list is Full");
            return;
        }

        waitingLists[size] = reservation;
        int current = size;

        while (current > 1 && waitingLists[current].getReservationID() < waitingLists[parent(current)].getReservationID()) {
            swap(current, parent(current));
            current = parent(current);
        }

        size++;
    }

    private void swap(int indexOne, int indexTwo) {
        Reservation temp = waitingLists[indexOne];
        waitingLists[indexOne] = waitingLists[indexTwo];
        waitingLists[indexTwo] = temp;
    }

    public Reservation delete() {
        if (size == 1) {
            System.out.println("Waiting list is Empty");
            return null;
        }

        Reservation root = waitingLists[1];
        waitingLists[1] = waitingLists[size - 1];
        size--;
        heapify(1);

        return root;
    }

    private void heapify(int i) {
        int smallest = i;
        int left = leftChild(i);
        int right = rightChild(i);

        if (left < size && waitingLists[left].getReservationID() < waitingLists[smallest].getReservationID()) {
            smallest = left;
        }

        if (right < size && waitingLists[right].getReservationID() < waitingLists[smallest].getReservationID()) {
            smallest = right;
        }

        if (smallest != i) {
            swap(i, smallest);
            heapify(smallest);
        }
    }

    public void printWLPq() {
        for (int i = 1; i < size; i++) {
            System.out.println(waitingLists[i].toString());
        }
    }
    public boolean isEmpty() {
        return size == 1;
    }

}
