package ui.settings;

import utils.InputUtils;
import utils.ScreenUtils;
import network.ClientConnection;


public class EditNameUI {
    public static void show() {
        ScreenUtils.clearScreen();
        ScreenUtils.printHeader("Edit Name", ScreenUtils.GREEN);

        String newName = InputUtils.getNonEmptyString("Enter your new name: ");

        String request = "USEREDITNAME|true|" + models.User.getInstance().getUserId()+"|" + newName;
        String response = ClientConnection.sendRequest(request);

        if (response.startsWith("USEREDITNAME|true")) {
            ScreenUtils.printMessage("Successfully edited name!", ScreenUtils.GREEN, true);
        } else {
            ScreenUtils.printMessage("Failed to edit name!", ScreenUtils.RED, false);
        }


        System.out.println("Server Response: " + response); // Response indicates success or failure

        ScreenUtils.promptEnterKey(); // Wait for the user to press Enter
    }
}
