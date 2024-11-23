package ui.tasks;

import utils.ScreenUtils;
import utils.InputUtils;

public class TasksMenu {
    public static void show() {
        while (true) {
            ScreenUtils.clearScreen();
            ScreenUtils.printHeader("Tasks Menu", ScreenUtils.BLUE);
            ScreenUtils.printMessage("1. View Tasks", ScreenUtils.YELLOW, false);
            System.out.println();
            System.out.println("2. Create a Task");
            System.out.println("3. Edit a Task");
            System.out.println("4. Delete All Tasks");
            System.out.println("0. Go Back");
            ScreenUtils.printDivider();

            int choice = InputUtils.getIntInRange("Enter your choice: ", 0, 4);

            switch (choice) {
                case 1 -> TaskListUI.show();
                case 2 -> TaskCreateUI.show();
                case 3 -> TaskEditUI.show();
                case 4 -> TaskDeleteUI.show();
                case 0 -> {
                    return; // Exit the Tasks Menu
                }
                default -> System.out.println("Invalid choice. Try again.");
            }

            ScreenUtils.promptEnterKey(); // Pause before going back to the menu
        }
    }
}
