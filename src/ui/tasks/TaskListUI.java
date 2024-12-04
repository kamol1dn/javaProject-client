package ui.tasks;

import utils.InputUtils;
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

        if (response.startsWith("TASKLIST|true|")) {
            String tasksData = response.substring("TASKLIST|true|".length());

            if (tasksData.length()==1) {
                ScreenUtils.printMessage("No tasks found for user: " + Session.getInstance().getUserId(), ScreenUtils.YELLOW, true);
            } else {
                String[] tasks = tasksData.split(", "); // Split tasks by the delimiter
                ScreenUtils.printMessage("Tasks for user: " + Session.getInstance().getUserId(), ScreenUtils.GREEN, true);

                // Print top border
                ScreenUtils.printMessage("=================================================", ScreenUtils.CYAN, true);
                ScreenUtils.printMessage("|   #   |         Task Name         | Due Date     |", ScreenUtils.CYAN, true);
                ScreenUtils.printMessage("-------------------------------------------------", ScreenUtils.CYAN, true);

                for (int i = 0; i < tasks.length; i++) {
                    // Parse each task to extract name and due date
                    String task = tasks[i];
                    String taskName = task.substring(0, task.lastIndexOf('(')).trim(); // Extract task name
                    String dueDate = task.substring(task.lastIndexOf('(') + 1, task.lastIndexOf(')')); // Extract due date

                    // Handle long task names
                    final int maxTaskNameLength = 25; // Maximum width for the task name column
                    if (taskName.length() > maxTaskNameLength) {
                        // Split task name into multiple lines
                        String[] wrappedLines = taskName.split("(?<=\\G.{" + maxTaskNameLength + "})");

                        // Display the first line with task number and due date
                        String firstRow = String.format("| %-5d | %-25s | %-12s |", (i + 1), wrappedLines[0], dueDate);
                        ScreenUtils.printMessage(firstRow, ScreenUtils.WHITE, false);

                        // Display the remaining lines without task number and due date
                        for (int j = 1; j < wrappedLines.length; j++) {
                            String wrappedRow = String.format("|       | %-25s |              |", wrappedLines[j]);
                            ScreenUtils.printMessage(wrappedRow, ScreenUtils.WHITE, false);
                        }
                    } else {
                        // Display the task in a single line
                        String row = String.format("| %-5d | %-25s | %-12s |", (i + 1), taskName, dueDate);
                        ScreenUtils.printMessage(row, ScreenUtils.WHITE, false);
                    }
                }

                // Print bottom border
                ScreenUtils.printMessage("=================================================", ScreenUtils.CYAN, true);

                // Allow user to select a task to edit or go back
                handleTaskSelection(tasks);
            }
        } else {
            ScreenUtils.printMessage("Failed to fetch tasks. Please try again later.", ScreenUtils.RED, true);
        }

        ScreenUtils.promptEnterKey();
    }

    private static void handleTaskSelection(String[] tasks) {
        while (true) {
            ScreenUtils.printMessage("\nEnter the task number to edit or 0 to go back:", ScreenUtils.YELLOW, false);
            int choice = InputUtils.getInt();

            if (choice == 0) {
                return; // Go back to the previous menu
            } else if (choice > 0 && choice <= tasks.length) {
                // Extract task details for the selected task
                String selectedTask = tasks[choice - 1];
                String taskName = selectedTask.substring(0, selectedTask.lastIndexOf('(')).trim(); // Extract task name
                String dueDate = selectedTask.substring(selectedTask.lastIndexOf('(') + 1, selectedTask.lastIndexOf(')')); // Extract due date

                // Edit the selected task
                editTask( taskName, dueDate);
                break;
            } else {
                ScreenUtils.printMessage("Invalid task number. Please try again.", ScreenUtils.RED, false);
            }
        }
    }

    private static void editTask(String taskName, String dueDate) {
        ScreenUtils.printHeader("Edit Task", ScreenUtils.CYAN);

        ScreenUtils.printMessage("Current Task: " + taskName, ScreenUtils.WHITE, false);
        ScreenUtils.printMessage("Current Due Date: " + dueDate, ScreenUtils.WHITE, false);

        String newDescription = InputUtils.getNonEmptyString("Enter new task description: ");
        String newDeadline = InputUtils.getDate("Enter new task deadline (dd.mm.yyyy): ");

        // Send the edit request to the server
        String request = "TASKEDIT|" + taskName + "|" + newDescription + "|" + newDeadline;
        String response = ClientConnection.sendRequest(request);

        if ("TASKEDIT|true".equals(response)) {
            ScreenUtils.printMessage("Task successfully updated!", ScreenUtils.GREEN, true);
        } else {
            ScreenUtils.printMessage("Failed to update the task. Please try again.", ScreenUtils.RED, true);
        }
    }
}
