package ui.settings;

import utils.ScreenUtils;
import utils.InputUtils;

public class SettingsMenu {
    public static void show() {
        while (true) {
            ScreenUtils.clearScreen();
            ScreenUtils.printHeader("Settings Menu");
            System.out.println("1. Edit Name");
            System.out.println("2. Edit Password");
            System.out.println("3. Edit Birthday");
            System.out.println("0. Go Back");
            ScreenUtils.printDivider();

            int choice = InputUtils.getIntInRange("Enter your choice: ", 0, 4);

            switch (choice) {
                case 1 -> EditNameUI.show();
                case 2 -> EditPasswordUI.show();
                case 3 -> EditBirthdayUI.show();
                case 0 -> {
                    return; // Exit the Settings Menu
                }
                default -> System.out.println("Invalid choice. Try again.");
            }

            ScreenUtils.promptEnterKey(); // Pause before going back to the menu
        }
    }
}
