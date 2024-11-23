package ui.library;

import utils.InputUtils;
import utils.ScreenUtils;
import network.ClientConnection;


public class BookBorrowUI {
    public static void show() {
        ScreenUtils.clearScreen();
        ScreenUtils.printHeader("Borrow a Book");

        String bookName = InputUtils.getNonEmptyString("Enter the name of the book you want to borrow: ");

        String request = "LIBRARY_BORROW " + bookName; // Command to borrow a book
        String response = ClientConnection.sendRequest(request);

        System.out.println("Server Response: " + response); // Response indicates success or failure

        ScreenUtils.promptEnterKey(); // Wait for the user to press Enter
    }
}
