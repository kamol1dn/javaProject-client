package ui.library;

import utils.ScreenUtils;
import utils.InputUtils;

public class LibraryMenu {
    public static void show() {
        while (true) {
            ScreenUtils.clearScreen();
            ScreenUtils.printHeader("Library Menu");
            System.out.println("1. View Book List");
            System.out.println("2. Borrow a Book");
            System.out.println("3. Return a Book");
            System.out.println("4. Add a Book (Admin)");
            System.out.println("0. Go Back");
            ScreenUtils.printDivider();

            int choice = InputUtils.getIntInRange("Enter your choice: ", 0, 4);

            switch (choice) {
                case 1 -> BookListUI.show();
                case 2 -> BookBorrowUI.show();
                case 3 -> BookReturnUI.show();
                case 4 -> BookAddUI.show();
                case 0 -> {
                    return; // Exit the Library Menu
                }
                default -> System.out.println("Invalid choice. Try again.");
            }

            ScreenUtils.promptEnterKey(); // Pause before going back to the menu
        }
    }
}
