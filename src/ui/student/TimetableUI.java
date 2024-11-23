package ui.student;

import utils.ScreenUtils;
import network.ClientConnection;


public class TimetableUI {
    public static void show() {
        ScreenUtils.clearScreen();
        ScreenUtils.printHeader("Student Timetable");

        String request = "STUDENT_TIMETABLE"; // Command to fetch timetable
        String response = ClientConnection.sendRequest(request);

        System.out.println("Timetable:");
        System.out.println(response); // Response should contain timetable details

        ScreenUtils.promptEnterKey(); // Wait for the user to press Enter
    }
}
