import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReservationSystem {

    private static ArrayList<Reservation> reservationList;
    private static ArrayList<Table> tablesList;
    private static WaitingList waitingList;
    private NotificationSystem notifications;
    private static ReservationBST rTree = new ReservationBST();
    Scanner scanner = new Scanner(System.in);

    public static ArrayList<Table> getTablesList() {
        return tablesList;
    }


    public ReservationSystem() {
        this.reservationList = new ArrayList<>();
        this.tablesList = new ArrayList<>();
        this.waitingList = Reservation.getWaitingList();
        this.notifications = new NotificationSystem();


        tablesList.add(new Table(1, 1));
        tablesList.add(new Table(2, 1));
        tablesList.add(new Table(3, 1));
        tablesList.add(new Table(4, 2));
        tablesList.add(new Table(5, 2));
        tablesList.add(new Table(6, 2));
        tablesList.add(new Table(7, 3));
        tablesList.add(new Table(8, 3));
        tablesList.add(new Table(9, 3));
        tablesList.add(new Table(10, 4));
        tablesList.add(new Table(11, 4));
        tablesList.add(new Table(12, 4));
        tablesList.add(new Table(13, 5));
        tablesList.add(new Table(14, 5));
        tablesList.add(new Table(15, 5));
        tablesList.add(new Table(16, 6));
        tablesList.add(new Table(17, 6));
        tablesList.add(new Table(18, 6));
        tablesList.add(new Table(19, 7));
        tablesList.add(new Table(20, 7));
        tablesList.add(new Table(21, 7));
        tablesList.add(new Table(22, 8));
        tablesList.add(new Table(23, 8));
        tablesList.add(new Table(24, 8));
        tablesList.add(new Table(25, 9));
        tablesList.add(new Table(26, 9));
        tablesList.add(new Table(27, 9));
        tablesList.add(new Table(28, 10));
        tablesList.add(new Table(29, 10));
        tablesList.add(new Table(30, 10));
    }

    public static WaitingList getWaitingList() {
        return waitingList;
    }

    public void makeReservation() {
        System.out.print("Enter customer name: ");
        scanner.nextLine();
        String customerName = scanner.nextLine();
        System.out.print("Enter contact information (phone/email): ");
        String contactInfo = scanner.nextLine();

        String dateInput = null;
        LocalDate date = null;


        while (date == null) {
            try {
                System.out.print("Enter reservation date (YYYY-MM-DD): ");
                dateInput = scanner.nextLine();
                date = LocalDate.parse(dateInput);


                if (date.isBefore(LocalDate.now())) {
                    System.out.println("Error: Reservation date must be in the future.");
                    date = null;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
            }
        }

        String timeInput = null;
        LocalTime time = null;


        while (time == null) {
            try {
                System.out.print("Enter reservation time (HH:mm): ");
                timeInput = scanner.nextLine();
                time = LocalTime.parse(timeInput);


                if (date.equals(LocalDate.now()) && time.isBefore(LocalTime.now())) {
                    System.out.println("Error: Reservation time must be in the future.");
                    time = null;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid time format. Please enter the time in HH:mm format.");
            }
        }

        int partySize;
        do {
            System.out.print("Enter party size between 1-10: ");
            partySize = scanner.nextInt();
            scanner.nextLine(); // Consume the leftover newline character

            if (partySize < 1 || partySize > 10) {
                System.out.println("Error: Party size must be between 1 and 10. Please enter a valid number.");
            }
        } while (partySize < 1 || partySize > 10);

        System.out.print(

                "\nTables are grouped as follows:\n" +
                        "+----------------+-------+\n" +
                        "|    Table ID    | Range |\n" +
                        "+----------------+-------+\n" +
                        "|     1 - 3      |   1   |\n" +
                        "|     4 - 6      |   2   |\n" +
                        "|     7 - 9      |   3   |\n" +
                        "|    10 - 12     |   4   |\n" +
                        "|    13 - 15     |   5   |\n" +
                        "|    16 - 18     |   6   |\n" +
                        "|    19 - 21     |   7   |\n" +
                        "|    22 - 24     |   8   |\n" +
                        "|    25 - 27     |   9   |\n" +
                        "|    28 - 30     |   10  |\n" +
                        "+----------------+-------+\n" +
                        "Please enter the corresponding Table ID based on capacity: ");
        int tableID = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Is this a VIP reservation? (true/false): ");
        boolean isVip = scanner.nextBoolean();
        scanner.nextLine();

        System.out.println();

        Reservation newReservation = new Reservation();
        newReservation.createReservation(customerName, contactInfo, dateInput, timeInput, partySize, tableID, isVip);

        if (newReservation.getStatus().equalsIgnoreCase("Confirmed")) {
            reservationList.add(newReservation);
            System.out.println(newReservation.toString());
            System.out.println();
            notifications.sendConfirmation(newReservation.getCustomerName(), newReservation.getReservationID());
            rTree.insert(newReservation);
            System.out.println("Reservation made successfully!");
            System.out.println();
        }
    }


    public void cancelReservation() {
        System.out.print("Enter reservation ID to cancel: ");
        int reservationID = scanner.nextInt();
        scanner.nextLine();
        System.out.println();

        Reservation canceledR = rTree.search(reservationID);

        if (canceledR != null) {
            Reservation next = waitingList.removeFromWaitingList(canceledR.getTable().getCapacity());
            if (next != null) {
                next.setTable(canceledR.getTable());
            }
            canceledR.cancelReservation();
            rTree.delete(canceledR.getReservationID());
            System.out.println("Reservation canceled successfully.");

            if (next != null) {
                System.out.println("Moving the reservation from the waiting list...");
                notifications.notifyAvailability(next.getCustomerName(), next.getTable().getCapacity());

                boolean validInput = false;

                while (!validInput) {
                    System.out.print("\nEnter the new date (YYYY-MM-DD): ");
                    String newDate = scanner.nextLine();
                    try {
                        next.setDate(LocalDate.parse(newDate));  // Update the date
                        validInput = true;  // Exit loop if input is valid
                    } catch (Exception e) {
                        System.out.println("Invalid date format. Please try again.");
                    }
                }

                validInput = false;

                while (!validInput) {
                    System.out.print("Enter the new time (HH:mm): ");
                    String newTime = scanner.nextLine();
                    try {
                        next.setTime(LocalTime.parse(newTime));
                        validInput = true;
                    } catch (Exception e) {
                        System.out.println("Invalid time format. Please try again.");
                    }
                }

                next.setStatus("Confirmed");
                next.getTable().setTableID(canceledR.getTable().getTableID());

                try {
                    rTree.insert(next);
                    System.out.println("\nReservation successfully moved from waiting list and confirmed.\n");
                } catch (Exception e) {
                    System.out.println("Error inserting reservation into rTree. Reservation not confirmed.");
                }
            }
        } else {
            System.out.println("Reservation not found.");
        }
    }


    public void assignTable(Table table, Reservation reservation) {
        table.setAvailable(false);
        table.setCurrentReservation(reservation);
        reservation.setStatus("Confirmed");
        System.out.println("Assigned Table " + table.getTableID() + " to " + reservation.getCustomerName());
    }

    public void checkout(int tableID) {
        Table checkedout = Table.getTable(tableID);
        for (Table table : tablesList) {
            if (table.getTableID() == checkedout.getTableID() && !checkedout.isAvailable()) {
                System.out.println("Checking out Table " + tableID + " for Reservation: " + checkedout.getCurrentReservation().getCustomerName());
                Reservation nextReservation = waitingList.removeFromWaitingList(checkedout.getCapacity());
                if (nextReservation != null) {
                    nextReservation.setTable(checkedout);
                    checkedout.setAvailable(true);
                    checkedout.getCurrentReservation().setStatus("CheckedOut");
                    notifications.notifyAvailability(nextReservation.getCustomerName(), nextReservation.getTable().getCapacity());

                    System.out.println("Moving the reservation from the waiting list...");

                    boolean validInput = false;

                    while (!validInput) {
                        System.out.print("\nEnter the new date (YYYY-MM-DD): ");
                        scanner.nextLine();
                        String newDate = scanner.nextLine();
                        try {
                            nextReservation.setDate(LocalDate.parse(newDate));
                            validInput = true;
                        } catch (Exception e) {
                            System.out.println("Invalid date format. Please try again.");
                        }
                    }

                    validInput = false;

                    while (!validInput) {
                        System.out.print("Enter the new time (HH:mm): ");
                        String newTime = scanner.nextLine();
                        try {
                            nextReservation.setTime(LocalTime.parse(newTime));
                            validInput = true;
                        } catch (Exception e) {
                            System.out.println("Invalid time format. Please try again.");
                        }
                    }

                    nextReservation.setStatus("Confirmed");

                    try {
                        rTree.insert(nextReservation);
                        System.out.println("Reservation successfully moved from waiting list and confirmed.");
                    } catch (Exception e) {
                        System.out.println("Error inserting reservation into rTree. Reservation not confirmed.");
                    }
                } else {
                    System.out.println("No reservation available in the waiting list for this table.");
                }
                return;
            }
            checkedout.getCurrentReservation().setStatus("CheckedOut");
        }
        System.out.println("Table " + tableID + " is either not found or already available.");
    }


    public static ReservationBST getrTree() {
        return rTree;
    }

    public void UpdateR(int IDofTheR) {
        Reservation reservationUpdated = rTree.search(IDofTheR);
        System.out.println(reservationUpdated.toString());
        System.out.print("\nEnter contact information (phone/email): ");
        scanner.nextLine();
        String contactInfo = scanner.nextLine();
        System.out.print("Enter reservation date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Enter reservation time (HH:mm): ");
        String time = scanner.nextLine();
        int partySize;
        do {
            System.out.print("Enter party size between 1-10: ");
            partySize = scanner.nextInt();
            scanner.nextLine(); // Consume the leftover newline character

            if (partySize < 1 || partySize > 10) {
                System.out.println("Error: Party size must be between 1 and 10. Please enter a valid number.");
            }
        } while (partySize < 1 || partySize > 10);
        System.out.println(

                "\nTables are grouped as follows:\n" +
                        "+----------------+-----+\n" +
                        "|  Table Range   | ID  |\n" +
                        "+----------------+-----+\n" +
                        "|    1 - 3       |  1  |\n" +
                        "|    4 - 6       |  2  |\n" +
                        "|    7 - 9       |  3  |\n" +
                        "|   10 - 12      |  4  |\n" +
                        "|   13 - 15      |  5  |\n" +
                        "|   16 - 18      |  6  |\n" +
                        "|   19 - 21      |  7  |\n" +
                        "|   22 - 24      |  8  |\n" +
                        "|   25 - 27      |  9  |\n" +
                        "|   28 - 30      | 10  |\n" +
                        "+----------------+-----+\n" +
                        "Please enter the corresponding Table ID: "
        );
        int tableID = scanner.nextInt();
        scanner.nextLine();
        System.out.println();
        reservationUpdated.updateReservation(IDofTheR, contactInfo, date, time, partySize, tableID);


    }

    public void generateReports(int choice) {
        switch (choice) {
            case 1:
                ReportGenerator.generateReservationReport();
                break;
            case 2:
                ReportGenerator.generatePeakTimeReport();
                break;
            case 3:
                ReportGenerator.generateCustomerAnalytics();
                break;
        }
    }

    public void startProgram() {
        boolean flag = true;
        while (flag) {
            try {
                System.out.println("1: Make Reservation\n2: Update Reservation\n3: Cancel Reservation\n4: CheckOut the Reservation \n5: View Reservations\n6: View Waiting List\n7: Generate Report\n8: View Notifications\n9: Exit\n");
                System.out.print("Choose the service you need: ");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        System.out.println("---Make Reservation---\n");
                        makeReservation();
                        break;
                    case 2:
                        System.out.println("---Update Reservation---\n");
                        System.out.print("Enter Your Reservation ID to update: ");
                        int rId = scanner.nextInt();
                        System.out.println();
                        UpdateR(rId);
                        break;
                    case 3:
                        System.out.println("---Cancel Reservation---\n");
                        cancelReservation();
                        break;
                    case 4:
                        System.out.println("---Checkout Reservation---\n");
                        System.out.print("Enter the table ID to check out: ");
                        int cId = scanner.nextInt();
                        checkout(cId);
                        break;
                    case 5:
                        System.out.println("---View Reservations---\n");
                        ReservationSystem.getrTree().printPreOrder();
                        break;
                    case 6:
                        System.out.println("---View Waiting List---\n");
                        ReservationSystem.getWaitingList().printWaitingList();
                        break;
                    case 7:
                        System.out.println("---Generate Report---\n");
                        System.out.println("1: Generate Reservation Report\n2: Generate Peak TimeReport\n3: Generate Customer Analytics\n");
                        System.out.print("Enter what you want: ");
                        ReportGenerator.setReportType(scanner.nextInt());
                        generateReports(ReportGenerator.getReportType());
                        break;
                    case 8:
                        System.out.println("---View Notifications---\n");
                        notifications.printNotification();
                        break;
                    case 9:
                        System.out.println("Exit Program successfully\n");
                        flag = false;
                        break;

                    default:
                        System.out.println("Invalid choice, please enter a valid option.\n");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number corresponding to the menu options.\n");
                scanner.nextLine();
            }
        }
    }

    public static ArrayList<Reservation> getReservationList() {
        return reservationList;
    }
}