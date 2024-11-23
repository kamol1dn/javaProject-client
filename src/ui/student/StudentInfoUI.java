package ui.student;

import utils.ScreenUtils;
import network.ClientConnection;


public class StudentInfoUI {
    public static void show() {
        ScreenUtils.clearScreen();
        ScreenUtils.printHeader("Student Information");

        String request = "STUDENT_INFO"; // Command to fetch student information
        String response = ClientConnection.sendRequest(request);

        System.out.println("Student Details:");
        System.out.println(response); // Response should contain student details

        ScreenUtils.promptEnterKey(); // Wait for the user to press Enter
    }
}
