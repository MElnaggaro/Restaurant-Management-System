import java.util.Hashtable;


public class Table {
    private int tableID;
    private int capacity;
    private boolean isAvailable;
    private Reservation currentReservation;
    private static Hashtable<Integer, Table> tables = new Hashtable<>();

    public Table(int tableID, int capacity) {
        this.tableID = tableID;
        this.capacity = capacity;
        this.isAvailable = true;
        tables.put(tableID, this);
    }

    public Reservation getCurrentReservation() {
        return currentReservation;
    }


    public void setCurrentReservation(Reservation reservation) {
        this.currentReservation = reservation;
    }


    public int getTableID() {
        return tableID;
    }

    public void setTableID(int tableID) {
        this.tableID = tableID;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }


    public static Table getTable(int tableID) {
        return tables.get(tableID);
    }

    public boolean assignTable(Reservation reservation) {
        if (isAvailable && reservation.getPartySize() <= capacity) {
            isAvailable = false;
            currentReservation = reservation;
            return true;
        }
        return false;
    }

    public void releaseTable(int tableID) {

        Table table = getTable(tableID);
        table.setAvailable(true);

    }

    @Override
    public String toString() {
        return "Table ID: " + tableID;
    }
}