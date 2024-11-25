package ui.tasks;

import utils.ScreenUtils;
import utils.Session;
import network.ClientConnection;


public class TaskListUI {
    public static void show() {
        ScreenUtils.clearScreen();
        ScreenUtils.printHeader("Task List", ScreenUtils.CYAN);

        // Send request for task list with the userId
        String request = "TASKLIST|" + Session.getInstance().getUserId(); // Command to fetch the list of tasks
        String response = ClientConnection.sendRequest(request);

        // Parse the response

        if (response.startsWith("TASKLIST|true|")) {
            String tasksData = response.substring("TASKLIST|true|".length());
            if (tasksData.isEmpty()) {
                ScreenUtils.printMessage("No tasks found for user: " + Session.getInstance().getUserId(), ScreenUtils.YELLOW, true);
            } else {
                String[] tasks = tasksData.split(", "); // Split tasks by the delimiter
                ScreenUtils.printMessage("Tasks for user: " + Session.getInstance().getUserId(), ScreenUtils.GREEN, true);
                for (int i = 0; i < tasks.length; i++) {
                    ScreenUtils.printMessage((i + 1) + ". " + tasks[i], ScreenUtils.WHITE, false);
                }
            }
        } else {
            ScreenUtils.printMessage("Failed to fetch tasks. Please try again later.", ScreenUtils.RED, true);
        }

        ScreenUtils.promptEnterKey(); // Wait for the user to press Enter
    }
}
