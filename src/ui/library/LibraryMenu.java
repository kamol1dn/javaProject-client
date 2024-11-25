package ui.library;

import utils.ScreenUtils;
import utils.InputUtils;

import static utils.ScreenUtils.printHeader;
import static utils.ScreenUtils.printMessage;

public class LibraryMenu {
    public static void show() {
        while (true) {
            ScreenUtils.clearScreen();
            printHeader("Library Menu", ScreenUtils.BLUE);

            printMessage("1. View Book List", ScreenUtils.CYAN, false);
            printMessage("2. Borrow a Book", ScreenUtils.GREEN, false);
            printMessage("3. Return a Book", ScreenUtils.PURPLE, false);
            printMessage("4. Add a Book (for admins only)",ScreenUtils.RED, false );
            printMessage("0. Go Back");
            ScreenUtils.printDivider();

            int choice = InputUtils.getIntInRange("Enter your choice: ", 0, 4);

            switch (choice) {
                case 1 -> BookListUI.show();
                case 2 -> BookBorrowUI.show();
                case 3 -> BookReturnUI.show();
                case 4 -> BookAddUI.show();
                case 0 ->  {return;}
                default -> printMessage("Invalid choice. Try again.", ScreenUtils.RED, false);
            }

            ScreenUtils.promptEnterKey(); // Pause before going back to the menu
        }
    }
}
