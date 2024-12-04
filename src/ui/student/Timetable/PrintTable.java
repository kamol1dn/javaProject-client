package ui.student.Timetable;

public class PrintTable {
    protected static void printTimetable(String[][] timetable) {
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

        // Print header row for slots
        System.out.printf("%-10s | %-18s | %-18s | %-18s | %-18s%n", "Day", "Slot 1", "Slot 2", "Slot 3", "Slot 4");
        System.out.println("-----------|--------------------|--------------------|--------------------|--------------------");

        // Print each day's row
        for (int day = 0; day < 6; day++) {
            System.out.printf("%-10s", days[day]); // Print day name
            for (int slot = 0; slot < 4; slot++) {
                String slotData = timetable[day][slot];
                if (slotData != null) {
                    String[] slotParts = slotData.split(";");
                    String subject = String.format("%-15s", slotParts[0]); // Align subject names
                    String time = slotParts[1];
                    System.out.printf("| %-15s %s", subject, time);
                } else {
                    System.out.printf("| %-15s %s", "subject", "00:00");
                }
            }
            System.out.println();
        }
    }

}
