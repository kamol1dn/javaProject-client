package ui.tasks;

import utils.InputUtils;
import utils.ScreenUtils;
import network.ClientConnection;


public class TaskEditUI {
    public static void show() {
        ScreenUtils.clearScreen();
        ScreenUtils.printHeader("Edit a Task");

        String taskId = InputUtils.getNonEmptyString("Enter the ID of the task to edit: ");
        String newDescription = InputUtils.getNonEmptyString("Enter new task description: ");
        String newDeadline = InputUtils.getDate("Enter new task deadline (dd.mm.yyyy): ");

        String request = "TASKS_EDIT " + taskId + " " + newDescription + " " + newDeadline; // Command to edit a task
        String response = ClientConnection.sendRequest(request);

        System.out.println("Server Response: " + response); // Response indicates success or failure

        ScreenUtils.promptEnterKey(); // Wait for the user to press Enter
    }
}
