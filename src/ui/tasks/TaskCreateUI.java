package ui.tasks;

import utils.InputUtils;
import utils.ScreenUtils;
import network.ClientConnection;


public class TaskCreateUI {
    public static void show() {
        ScreenUtils.clearScreen();
        ScreenUtils.printHeader("Create a Task");

        String description = InputUtils.getNonEmptyString("Enter task description: ");
        String deadline = InputUtils.getDate("Enter task deadline (dd.mm.yyyy): ");
//
        String request = "TASKS_CREATE " + description + " " + deadline; // Command to create a task
        String response = ClientConnection.sendRequest(request);

        System.out.println("Server Response : " + response); // Response indicates success or failure

        ScreenUtils.promptEnterKey(); // Wait for the user to press Enter
    }
}
