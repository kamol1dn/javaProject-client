package ui.library;

import utils.InputUtils;
import utils.ScreenUtils;
import network.ClientConnection;


public class BookReturnUI {
    public static void show() {
        ScreenUtils.clearScreen();
        ScreenUtils.printHeader("Return a Book");

        String bookId = InputUtils.getNonEmptyString("Enter the ID of the book you want to return: ");

        String request = "LIBRARY_RETURN " + bookId; // Command to return a book
        String response = ClientConnection.sendRequest(request);

        System.out.println("Server Response: " + response); // Response indicates success or failure

        ScreenUtils.promptEnterKey(); // Wait for the user to press Enter
    }
}
