package ui.student.Timetable;

import models.User;
import utils.InputUtils;
import utils.ScreenUtils;
import network.ClientConnection;

import java.util.Scanner;

import static ui.student.Timetable.PrintTable.printTimetable;

public class TimetableUI {

    public static void show() {
        ScreenUtils.clearScreen();
        ScreenUtils.printHeader("Student Timetable", ScreenUtils.BLUE);

        // Send the request to fetch the timetable
        String request = "VIEWTIMETABLE|" + User.getInstance().getUserId();
        String response = ClientConnection.sendRequest(request);




        if (response.startsWith("VIEWTIMETABLE|true")) {
            // Parse timetable data
            String[] parts = response.split("\\|");

            // Organize timetable data
            String[][] timetable = new String[6][4]; // 6 days, 4 slots per day
            int index = 2; // Start after the "VIEWTIMETABLE|true" part
            for (int day = 0; day < 6; day++) {
                for (int slot = 0; slot < 4; slot++) {
                    if (index < parts.length) {
                        timetable[day][slot] = parts[index];
                        index++;
                    }
                }
            }

            // Display the entire timetable
            printTimetable(timetable);

            // Ask the user to choose a day for editing
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the day (1-6) to edit timetable: ");
            int day = InputUtils.getIntInRange("Enter day to edit: (Monday: 1...)", 1, 6);

            TimetableEdit.timetableEdit(day, timetable);


        } else {
            // No timetable or invalid user
            System.out.println("No timetable available or invalid user.");
        }

        // Wait for the user to press Enter
        ScreenUtils.promptEnterKey();
    }


}
