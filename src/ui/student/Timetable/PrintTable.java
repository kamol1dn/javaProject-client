package ui.student.Timetable;

import utils.ScreenUtils;

public class PrintTable {
    protected static void printTimetable(String[][] timetable) {
        String[] days = {"1. Monday", "2. Tuesday", "3. Wednesday", "4. Thursday", "5. Friday", "6. Saturday"};
        int columnWidth = 25; // Fixed width for all columns

        System.out.print(ScreenUtils.GREEN); //color all

        // Header row treated like the rest of the table
        String[] headers = {"Day", "Slot 1", "Slot 2", "Slot 3", "Slot 4"};
        printRow(headers, columnWidth);
        printDivider(columnWidth, headers.length);

        // Print each day's row
        for (int day = 0; day < 6; day++) {
            String[] row = new String[5];
            row[0] = days[day];
            for (int slot = 0; slot < 4; slot++) {
                String slotData = timetable[day][slot];
                if (!slotData.equals(" ")) {
                    String[] slotParts = slotData.split(";");
                    String subject = truncate(slotParts[0], columnWidth - 8);
                    String time = slotParts[1];
                    row[slot + 1] =  String.format("%-" + (columnWidth - 8) + "s %5s", subject, time);

                } else  {
                    row[slot + 1] =  String.format("%-" + (columnWidth - 8) + "s %5s", "N/A", "00:00");
                }
            }
            printRow(row, columnWidth);

            //wait a little
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }
        System.out.print(ScreenUtils.RESET);
    }

    // Helper method to print a row
    private static void printRow(String[] row, int columnWidth) {
        for (String cell : row) {
            System.out.printf("| %-" + (columnWidth - 2) + "s", cell);
        }
        System.out.println("|");
    }

    // Helper method to print a divider
    private static void printDivider(int columnWidth, int columnCount) {
        for (int i = 0; i < columnCount; i++) {
            System.out.print("+" + "-".repeat(columnWidth - 1));
        }
        System.out.println("+");
    }

    // Helper method to truncate long strings
    private static String truncate(String text, int maxLength) {
        if (text.length() > maxLength) {
            return text.substring(0, maxLength - 3) + "...";
        }
        return text;
    }



    // table print when choosing

    protected static void printTimetableRow(String[][] timetable, int rowN) {
        String[] days = {"1. Monday", "2. Tuesday", "3. Wednesday", "4. Thursday", "5. Friday", "6. Saturday"};
        int columnWidth = 25; // Fixed width for all columns

        // ANSI escape codes for colors
        final String RESET = ScreenUtils.RESET;
        final String GREEN = ScreenUtils.GREEN;
        final String RED = ScreenUtils.RED;

        // Header row treated like the rest of the table
        String[] headers = {"Day", "Slot 1", "Slot 2", "Slot 3", "Slot 4"};
        System.out.print(GREEN);
        printRow(headers, columnWidth);
        printDivider(columnWidth, headers.length);

        // Print each day's row
        for (int day = 0; day < 6; day++) {
            String color = (day == rowN) ? RED : GREEN; // Red for selected row
            String[] row = new String[5];
            row[0] = days[day];
            for (int slot = 0; slot < 4; slot++) {
                String slotData = timetable[day][slot];
                if (slotData != null) {
                    String[] slotParts = slotData.split(";");
                    String subject = truncate(slotParts[0], columnWidth - 8);
                    String time = slotParts[1];
                    row[slot + 1] = String.format("%-" + (columnWidth - 8) + "s %5s", subject, time);
                } else {
                    row[slot + 1] = String.format("%-" + (columnWidth - 8) + "s %5s", "N/A", "00:00");
                }
            }

            System.out.print(color); // Apply color to the row
            printRow(row, columnWidth);
        }

        System.out.print(RESET); // Reset color
    }

    protected static void printTimetableSlot(String[][] timetable, int rowN, int slotN) {
        String[] days = {"1. Monday", "2. Tuesday", "3. Wednesday", "4. Thursday", "5. Friday", "6. Saturday"};
        int columnWidth = 25; // Fixed width for all columns

        // ANSI escape codes for colors
        final String RESET = ScreenUtils.RESET;
        final String GREEN = ScreenUtils.GREEN;
        final String RED = ScreenUtils.RED;

        // Header row treated like the rest of the table
        String[] headers = {"Day", "Slot 1", "Slot 2", "Slot 3", "Slot 4"};
        System.out.print(GREEN);
        printRow(headers, columnWidth);
        printDivider(columnWidth, headers.length);

        // Print each day's row
        for (int day = 0; day < 6; day++) {
            String[] row = new String[5];
            System.out.print(GREEN);
            row[0] =days[day];
            for (int slot = 0; slot < 4; slot++) {
                String slotData = timetable[day][slot];
                String color = (day == rowN && slot == slotN) ? RED : GREEN; // Red for selected slot
                if (slotData != null) {
                    String[] slotParts = slotData.split(";");
                    String subject = truncate(slotParts[0], columnWidth - 8);
                    String time = slotParts[1];
                    row[slot + 1] = color + String.format("%-" + (columnWidth - 8) + "s %5s", subject, time) ;
                } else {
                    row[slot + 1] = color + String.format("%-" + (columnWidth - 8) + "s %5s", "N/A", "00:00") ;
                }
            }

            printRow(row, columnWidth);
        }

        System.out.print(RESET); // Reset color
    }

}
