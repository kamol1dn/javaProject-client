package ui.settings;

import utils.InputUtils;
import utils.ScreenUtils;
import network.ClientConnection;


public class EditBirthdayUI {
    public static void show() {
        ScreenUtils.clearScreen();
        ScreenUtils.printHeader("Edit Birthday");

        String newBirthday = InputUtils.getDate("Enter your new birthday (dd.mm.yyyy): ");

        String request = "SETTINGS_EDIT_BIRTHDAY " + newBirthday; // Command to update the birthday
        String response = ClientConnection.sendRequest(request);

        System.out.println("Server Response: " + response); // Response indicates success or failure

        ScreenUtils.promptEnterKey(); // Wait for the user to press Enter
    }
}
