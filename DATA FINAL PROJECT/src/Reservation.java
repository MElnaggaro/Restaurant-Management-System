import java.time.LocalTime;
import java.time.LocalDate;
import java.util.Scanner;

public class Reservation {
    private int reservationID;
    private String customerName;
    private String contactInfo;
    private LocalDate date;
    private LocalTime time;
    private int partySize;
    private String status;
    private boolean isVip = false;
    private int vip_ID;
    private static int nextvip_ID = 1;
    private int normal_ID;
    private static int nextnormal_ID = 10000001;
    private static ReservationBST rTree = new ReservationBST();

    private Table table;


    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    private static WaitingList waitingList = new WaitingList(10000);
    Scanner scanner = new Scanner(System.in);

    public Reservation() {
        this.status = "Confirmed";
    }

    public void createReservation(String customerName, String contactInfo, String date, String time, int partySize, int tableID, boolean isVip) {
        this.customerName = customerName;
        this.contactInfo = contactInfo;
        this.partySize = partySize;
        this.isVip = isVip;

        if (isVip) {
            this.vip_ID = nextvip_ID++;//1 2 3
            this.reservationID = vip_ID;
        } else {
            this.normal_ID = nextnormal_ID++;//100001 100002
            this.reservationID = normal_ID;
        }

        Table theTable = Table.getTable(tableID);

        if (theTable == null) {
            System.out.print("Enter Valid Table id :");
            tableID = scanner.nextInt();
            theTable = Table.getTable(tableID);
        }
        if (partySize > theTable.getCapacity()) {
            System.out.println("The table's capacity is not enough ");
            System.out.print("Choose a table suitable for party size: ");
            tableID = scanner.nextInt();
            theTable = Table.getTable(tableID);
        }

        if (!theTable.isAvailable()) {
            boolean foundAvailableTable = false;


            for (Table table : ReservationSystem.getTablesList()) {
                if (table.isAvailable() && table.getCapacity() >= partySize) {

                    tableID = table.getTableID();
                    theTable = table;
                    System.out.println("The table is unavailable but there is another available table, it's ID: " + tableID);
                    foundAvailableTable = true;
                    break;
                }
            }

            if (!foundAvailableTable) {

                waitingList.addToWaitList(this);
                this.status = "In Waining List";
                System.out.println("No available table for your party size. You have been added to the waiting list.\n");
                return;
            }
        }


        theTable.assignTable(this);

        this.table = theTable;
        this.time = LocalTime.parse(time);
        this.date = LocalDate.parse(date);
        rTree.insert(this);


    }


    public void cancelReservation() {
        Reservation reservationDeleted = rTree.search(getReservationID());
        if (reservationDeleted != null) {
            rTree.delete(getReservationID());

            if (reservationDeleted.table != null) {
                reservationDeleted.table.releaseTable(table.getTableID());
            }
            reservationDeleted.status = "Canceled";
        } else {
            System.out.println("Reservation not found.");
        }
    }


    public void updateReservation(int IDofTheR, String newContantInfo, String newDate, String newTime,int newPartySize, int newTableId) {
        Reservation reservationUpdated = rTree.search(IDofTheR);
        if (reservationUpdated != null) {
            reservationUpdated.customerName = reservationUpdated.getCustomerName();
            reservationUpdated.contactInfo = newContantInfo;
            reservationUpdated.date = LocalDate.parse(newDate);
            reservationUpdated.time = LocalTime.parse(newTime);
            reservationUpdated.partySize = newPartySize;
            Table theTable = Table.getTable(newTableId);

            if (theTable == null) {
                System.out.print("Enter Valid Table id :");
                newTableId = scanner.nextInt();
            }
            theTable = Table.getTable(newTableId);

            if (partySize > theTable.getCapacity()) {
                System.out.println(" The table's capacity is not enough ");
                System.out.print("Choose a table suitable for party size: ");
                newTableId = scanner.nextInt();
            }
            if (reservationUpdated.table.getTableID() == newTableId) {
                reservationUpdated.table.setAvailable(true);
            }
            if (!theTable.isAvailable()) {
                System.out.println("Error: Table " + newTableId + " is unavailable.");
                waitingList.addToWaitList(this);
                this.status = "Waiting";
                System.out.println("You are now in the waiting list ,wait for updates");
            }
            if (reservationUpdated.table.getTableID() != newTableId) {
                reservationUpdated.table.setAvailable(true);
            }
            reservationUpdated.table = theTable;
            reservationUpdated.status = "Updated";

        }
        System.out.println(reservationUpdated.toString());
    }


    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public int getPartySize() {
        return partySize;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationID +
                " | Customer: " + customerName +
                " | Info: " + contactInfo +
                " | Status: " + status +
                " | Party Size: " + partySize +
                " | Date: " + date +
                " | Time: " + time +
                " | Table: " + table +
                " | VIP: " + isVip +
                "\n";
    }

    public static WaitingList getWaitingList() {
        return waitingList;
    }
}