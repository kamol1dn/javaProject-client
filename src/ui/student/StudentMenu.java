package ui.student;
import ui.student.Timetable.TimetableUI;
import utils.ScreenUtils;
import utils.InputUtils;

public class StudentMenu {
    public static void show() {
        while (true) {
            ScreenUtils.clearScreen();
            ScreenUtils.printHeader("Student Menu", ScreenUtils.CYAN);
            ScreenUtils.printMessage("1. View Student Information", ScreenUtils.GREEN, false);
            ScreenUtils.printMessage("2. View Timetable", ScreenUtils.BLUE, false);
            ScreenUtils.printMessage("0. Go Back", ScreenUtils.WHITE, false);

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
