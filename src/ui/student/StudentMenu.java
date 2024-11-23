package ui.student;

import utils.ScreenUtils;
import utils.InputUtils;

public class StudentMenu {
    public static void show() {
        while (true) {
            ScreenUtils.clearScreen();
            ScreenUtils.printHeader("Student Menu");
            System.out.println("1. View Student Information");
            System.out.println("2. View Timetable");
            System.out.println("0. Go Back");
            ScreenUtils.printDivider();

            int choice = InputUtils.getIntInRange("Enter your choice: ", 0, 2);

            switch (choice) {
                case 1 -> StudentInfoUI.show();
                case 2 -> TimetableUI.show();
                case 0 -> {
                    return; // Exit the Student Menu
                }
                default -> System.out.println("Invalid choice. Try again.");
            }

            ScreenUtils.promptEnterKey(); // Pause before going back to the menu
        }
    }
}
