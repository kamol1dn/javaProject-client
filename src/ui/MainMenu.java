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
            ScreenUtils.printHeader("Main Menu" "Yellow");
            System.out.println("1. Tasks");
            System.out.println("2. Library Manager");
            System.out.println("3. Student Details & Timetable");
            System.out.println("4. Settings");
            System.out.println("0. Exit");
            ScreenUtils.printDivider();

            int choice = InputUtils.getIntInRange("Enter your choice: ", 0, 4);

            switch (choice) {
                case 1 -> TasksMenu.show();
                case 2 -> LibraryMenu.show();
                case 3 -> StudentMenu.show();
                case 4 -> SettingsMenu.show();
                case 0 -> {
                    System.out.println("Exiting...");
                    return; // Exit the main menu loop
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }

            ScreenUtils.promptEnterKey(); // Wait for the user to press Enter before returning
        }
    }
}
