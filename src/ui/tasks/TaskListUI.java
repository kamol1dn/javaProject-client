package ui.tasks;

import utils.ScreenUtils;
import network.ClientConnection;


public class TaskListUI {
    public static void show() {
        ScreenUtils.clearScreen();
        ScreenUtils.printHeader("Task List");

        String request = "TASKS_LIST"; // Command to fetch the list of tasks
        String response = ClientConnection.sendRequest(request);

        System.out.println("Tasks:");
        System.out.println(response); // Response should contain the list of tasks

        ScreenUtils.promptEnterKey(); // Wait for the user to press Enter
    }
}
