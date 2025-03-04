package ui.tasks;

import utils.ScreenUtils;
import utils.InputUtils;

import static utils.ScreenUtils.printHeader;
import static utils.ScreenUtils.printMessage;

public class TasksMenu {
    public static void show() {
        while (true) {
            ScreenUtils.clearScreen();
            printHeader("Tasks Menu", "\u001B[32m");
            printMessage("1. View Tasks", ScreenUtils.YELLOW, false);

            printMessage("2. Create a Task", ScreenUtils.CYAN, false);

            printMessage("3. Delete all tasks", ScreenUtils.RED, true);
            printMessage("0. Go back", ScreenUtils.WHITE, false);
            ScreenUtils.printDivider();

            int choice = InputUtils.getIntInRange("Enter your choice: ", 0, 3);

            switch (choice) {
                case 1 -> TaskListUI.show();
                case 2 -> TaskCreateUI.show();

                case 3 -> TaskDeleteUI.show();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }

        }
    }
}
