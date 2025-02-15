import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ReportGenerator {
    private static int reportType;
    private static List<Reservation> reservations;

    public static void generateReservationReport() {
        reservations = ReservationSystem.getReservationList();
        System.out.println("Generating Reservation Report...");

        List<Reservation> sortedReservations = mergeSort(reservations);

        Map<LocalDate, Integer> dailyReservations = new HashMap<>();

        Map<Month, Integer> monthlyReservations = new HashMap<>();

        for (Reservation reservation : sortedReservations) {
            LocalDate date = reservation.getDate();
            dailyReservations.put(date, dailyReservations.getOrDefault(date, 0) + 1);

            Month month = date.getMonth();
            monthlyReservations.put(month, monthlyReservations.getOrDefault(month, 0) + 1);
        }

        System.out.println("\nDaily Reservations:");
        for (Map.Entry<LocalDate, Integer> entry : dailyReservations.entrySet()) {
            System.out.println("Date: " + entry.getKey() + " - Reservations: " + entry.getValue());
        }

        System.out.println("\nMonthly Reservations:");
        for (Map.Entry<Month, Integer> entry : monthlyReservations.entrySet()) {
            System.out.println("Month: " + entry.getKey() + " - Reservations: " + entry.getValue());
        }
        System.out.println();
    }

    public static void generatePeakTimeReport() {
        reservations = ReservationSystem.getReservationList();
        List<Reservation> sortedReservations = mergeSort(reservations);

        System.out.println("Generating Peak Time Report...");

        int[] hourlyReservations = new int[24];

        for (Reservation reservation : sortedReservations) {
            int hour = reservation.getTime().getHour();
            hourlyReservations[hour]++;
        }

        System.out.println("Peak Times:");
        System.out.println();
        for (int hour = 0; hour < 24; hour++) {
            if (hourlyReservations[hour] > 0) {
                System.out.println("Hour " + hour + ": " + hourlyReservations[hour] + " reservations");
            }
        }
        System.out.println();
    }

    public static void generateCustomerAnalytics() {
        reservations = ReservationSystem.getReservationList();
        List<Reservation> sortedReservations = mergeSort(reservations);

        System.out.println("Generating Customer Analytics...");

        int totalReservations = sortedReservations.size();
        int totalCustomers = 0;

        for (Reservation reservation : sortedReservations) {
            totalCustomers += reservation.getPartySize();
        }

        System.out.println("\nTotal Reservations: " + totalReservations+"\n");
        System.out.println("Total Customers: " + totalCustomers+"\n");
    }

    private static List<Reservation> mergeSort(List<Reservation> data) {
        if (data.size() <= 1) {
            return data;
        }

        int mid = data.size() / 2;
        List<Reservation> left = mergeSort(data.subList(0, mid));
        List<Reservation> right = mergeSort(data.subList(mid, data.size()));

        return merge(left, right);
    }

    private static List<Reservation> merge(List<Reservation> left, List<Reservation> right) {
        List<Reservation> merged = new ArrayList<>();
        int i = 0, j = 0;

        while (i < left.size() && j < right.size()) {
            if (left.get(i).getDate().isBefore(right.get(j).getDate()) ||
                    (left.get(i).getDate().isEqual(right.get(j).getDate()) &&
                            left.get(i).getTime().isBefore(right.get(j).getTime()))) {
                merged.add(left.get(i++));
            } else {
                merged.add(right.get(j++));
            }
        }

        while (i < left.size()) {
            merged.add(left.get(i++));
        }

        while (j < right.size()) {
            merged.add(right.get(j++));
        }

        return merged;
    }

    public static int getReportType() {
        return reportType;
    }

    public static void setReportType(int reportType) {
        ReportGenerator.reportType = reportType;
    }
}