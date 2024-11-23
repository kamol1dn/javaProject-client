package ui.tasks;
import utils.InputUtils;
import utils.ScreenUtils;
import network.ClientConnection;


public class TaskDeleteUI {
    public static void show() {
        ScreenUtils.clearScreen();
        ScreenUtils.printHeader("Delete All Tasks");

        System.out.println("Are you sure you want to delete all tasks? This action cannot be undone.");
        String confirmation = InputUtils.getNonEmptyString("Type 'YES' to confirm: ");
        if (!"YES".equalsIgnoreCase(confirmation)) {
            System.out.println("Task deletion canceled.");
            ScreenUtils.promptEnterKey();
            return;
        }

        String request = "TASKS_DELETE_ALL"; // Command to delete all tasks
        String response = ClientConnection.sendRequest(request);

        System.out.println("Server Response: " + response); // Response indicates success or failure

        ScreenUtils.promptEnterKey(); // Wait for the user to press Enter
    }
}
