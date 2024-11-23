package ui.settings;

import utils.InputUtils;
import utils.ScreenUtils;
import network.ClientConnection;


public class EditNameUI {
    public static void show() {
        ScreenUtils.clearScreen();
        ScreenUtils.printHeader("Edit Name");

        String newName = InputUtils.getNonEmptyString("Enter your new name: ");

        String request = "SETTINGS_EDIT_NAME " + newName; // Command to update the name
        String response = ClientConnection.sendRequest(request);

        System.out.println("Server Response: " + response); // Response indicates success or failure

        ScreenUtils.promptEnterKey(); // Wait for the user to press Enter
    }
}
