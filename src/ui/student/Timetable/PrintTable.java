package ui.student.Timetable;

import utils.ScreenUtils;

public class PrintTable {
    protected static void printTimetable(String[][] timetable) {
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        int columnWidth = 19; // Fixed width for each column

        // Print header row for slots
        System.out.printf("%-" + columnWidth + "s| %-" + columnWidth + "s| %-" + columnWidth + "s| %-" + columnWidth + "s| %-" + columnWidth + "s%n",
                "Day", "Slot 1", "Slot 2", "Slot 3", "Slot 4");
        System.out.println("-".repeat(columnWidth ) + "|" + "-".repeat(columnWidth ) + "|" + "-".repeat(columnWidth) + "|" + "-".repeat(columnWidth) + "|" + "-".repeat(columnWidth));

        // Print each day's row
        for (int day = 0; day < 6; day++) {
            System.out.printf("%-" + columnWidth + "s", days[day]); // Print day name
            for (int slot = 0; slot < 4; slot++) {
                String slotData = timetable[day][slot];
                if (slotData != null) {
                    String[] slotParts = slotData.split(";");
                    String subject = truncate(slotParts[0], columnWidth - 7); // Adjust for padding
                    String time = slotParts[1];
                    System.out.printf("| %-" + (columnWidth - 2) + "s", subject + " " + time);
                } else {
                    System.out.printf("| %-" + (columnWidth - 2) + "s", "N/A 00:00");
                }
            }
            System.out.println();
            ScreenUtils.showLoading(0.2);
        }
    }

    // Helper method to truncate long strings
    private static String truncate(String text, int maxLength) {
        if (text.length() > maxLength) {
            return text.substring(0, maxLength - 3) + "..."; // Add ellipsis if truncated
        }
        return text;
    }
}
