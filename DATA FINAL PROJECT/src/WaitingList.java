import java.util.ArrayList;

public class WaitingList {
    private WLPq waitingQueue;

    public WaitingList(int capacity) {
        this.waitingQueue = new WLPq(capacity);
    }

    public void addToWaitList(Reservation reservation) {
        waitingQueue.insert(reservation);
    }

    public Reservation removeFromWaitingList(int tableCapacity) {
        ArrayList<Reservation> tempArrayList = new ArrayList<>();
        Reservation suitableReservation = null;

        while (!waitingQueue.isEmpty()) {
            Reservation currentReservation = waitingQueue.delete();

            if (currentReservation.getPartySize() <= tableCapacity) {
                suitableReservation = currentReservation;
                break;
            } else {
                tempArrayList.add(currentReservation);
            }
        }


        for (Reservation reservation : tempArrayList) {
            waitingQueue.insert(reservation);
        }

        return suitableReservation;
    }

    public void printWaitingList() {
        waitingQueue.printWLPq();
    }
}