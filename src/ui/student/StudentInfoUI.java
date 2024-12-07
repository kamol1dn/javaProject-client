package ui.student;

import utils.ScreenUtils;
import network.ClientConnection;
import models.User;

public class StudentInfoUI {
    public static void show() {
        ScreenUtils.clearScreen();
        ScreenUtils.printHeader("Student Information", ScreenUtils.BLUE);

        String request = "STUDENTDETAIL|"+ User.getInstance().getUserId(); // Command to fetch student information
        String response = ClientConnection.sendRequest(request);

        if (response.startsWith("STUDENTDETAIL|true")) {
            ScreenUtils.printMessage("Student Details:", ScreenUtils.GREEN, true);
            String[] userDetails = response.split("\\|");
            String userId = userDetails[2];
            String userPassword = userDetails[3];
            String userName = userDetails[4];

            ScreenUtils.printDivider();

            ScreenUtils.printMessage("Student ID: " + userId, ScreenUtils.BLUE, true);
            ScreenUtils.printMessage("Student Name: " + userName, ScreenUtils.BLUE, true);
            ScreenUtils.printMessage("Student Password: " + userPassword, ScreenUtils.BLUE, true);
        }
    }
}
