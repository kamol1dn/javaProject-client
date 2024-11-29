package ui.tasks;

import utils.InputUtils;
import utils.ScreenUtils;
import network.ClientConnection;


public class TaskCreateUI {
    public static void show() {
        ScreenUtils.clearScreen();
        ScreenUtils.printHeader("Create a Task", ScreenUtils.YELLOW);

        String description = InputUtils.getNonEmptyString(ScreenUtils.GREEN + "Enter task description: " + ScreenUtils.RESET);
        String deadline = InputUtils.getDate(ScreenUtils.GREEN+ "Enter task deadline (dd.mm.yyyy): "  + ScreenUtils.RESET);
//
        String request = "TASKSADDNEW|" + models.User.getInstance().getUserId() +"|" +description +"|" + deadline; // Command to create a task
        String response = ClientConnection.sendRequest(request);

        System.out.println("Server Response : " + response); // Response indicates success or failure

    }
}
