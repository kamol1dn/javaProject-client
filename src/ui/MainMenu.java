package ui;
import java.util.Scanner;
import ui.library.LibraryMenu;
import ui.settings.SettingsMenu;
import ui.student.StudentMenu;
import ui.tasks.TasksMenu;
import utils.InputUtils;
import utils.ScreenUtils;


public class MainMenu {
    public static void show() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            ScreenUtils.clearScreen();
            ScreenUtils.printHeader("Main Menu", "YELLOW");
            ScreenUtils.printMessage("1. Tasks", "GREEN", false);
            ScreenUtils.printMessage("2. Library Manager", "BLUE", false);
            ScreenUtils.printMessage("3. Student Details & Timetable", "CYAN", false);
            ScreenUtils.printMessage("4. Settings", "PURPLE", false);
            ScreenUtils.printMessage("5. Exit", "RED", false);

            ScreenUtils.printDivider();

            int choice = InputUtils.getIntInRange("Enter your choice: ", 0, 4);

            switch (choice) {
                case 1 -> TasksMenu.show();
                case 2 -> LibraryMenu.show();
                case 3 -> StudentMenu.show();
                case 4 -> SettingsMenu.show();
                case 0 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
            ScreenUtils.promptEnterKey();
        }
    }
}
