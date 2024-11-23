package ui.settings;

import utils.InputUtils;
import utils.ScreenUtils;
import network.ClientConnection;

public class EditPasswordUI {
    public static void show() {
        ScreenUtils.clearScreen();
        ScreenUtils.printHeader("Edit Password");

        String newPassword = InputUtils.getNonEmptyString("Enter your new password: ");

        String request = "SETTINGS_EDIT_PASSWORD " + newPassword; // Command to update the password
        String response = ClientConnection.sendRequest(request);

        System.out.println("Server Response: " + response); // Response indicates success or failure

        ScreenUtils.promptEnterKey(); // Wait for the user to press Enter
    }
}
