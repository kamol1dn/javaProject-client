package ui.settings;

import utils.InputUtils;
import utils.ScreenUtils;
import network.ClientConnection;

public class EditPasswordUI {
    public static void show() {
        ScreenUtils.clearScreen();
        ScreenUtils.printHeader("Edit Password");

        String newPassword = InputUtils.getNonEmptyString("Enter your new password: ");

        String request = "USEREDITPASSWORD|" +models.User.getInstance().getUserId()+ "|"+ newPassword;
        String response = ClientConnection.sendRequest(request);

        if (response.startsWith("USEREDITPASSWORD|true")) {
        ScreenUtils.printMessage("Successfully edited password!", ScreenUtils.GREEN, true);
        } else {
        ScreenUtils.printMessage("Failed to edit password!", ScreenUtils.RED, false);
        }

        System.out.println("Server Response: " + response);

        ScreenUtils.promptEnterKey();
    }
}
