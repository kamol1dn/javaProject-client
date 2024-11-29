package ui.settings;

import utils.ScreenUtils;
import utils.InputUtils;

public class SettingsMenu {
    public static void show() {
        while (true) {
            ScreenUtils.clearScreen();
            ScreenUtils.printHeader("Settings Menu", ScreenUtils.PURPLE);
            ScreenUtils.printMessage("1. Edit Name", ScreenUtils.GREEN, false);
            ScreenUtils.printMessage("2. Edit Password", ScreenUtils.CYAN, false);
            System.out.println("0. Go Back");
            ScreenUtils.printDivider();

            int choice = InputUtils.getIntInRange("Enter your choice: ", 0, 2);

            switch (choice) {
                case 1 -> EditNameUI.show();
                case 2 -> EditPasswordUI.show();

                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }

            ScreenUtils.promptEnterKey();
        }
    }
}
