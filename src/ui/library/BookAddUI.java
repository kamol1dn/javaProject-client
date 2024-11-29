package ui.library;

import utils.InputUtils;
import utils.ScreenUtils;
import network.ClientConnection;

public class BookAddUI {
    public static void show() {
        ScreenUtils.clearScreen();
        ScreenUtils.printHeader("Add a New Book", ScreenUtils.CYAN);

        // Get input for book name and author
        String bookName = InputUtils.getNonEmptyString(ScreenUtils.BLUE+"Enter the name of the new book: " + ScreenUtils.RESET);
        String bookAuthor = InputUtils.getNonEmptyString(ScreenUtils.BLUE+"Enter the author of the new book: " + ScreenUtils.RESET);

        // Format the request for adding a new book
        String request = "ADDNEWBOOK|true|" + bookName + "|" + bookAuthor;
        String response = ClientConnection.sendRequest(request);

        // Parse the server response
        if ("ADDNEWBOOK|true".equals(response)) {
            ScreenUtils.printMessage("The book \"" + bookName + "\" by " + bookAuthor + " has been added successfully.", ScreenUtils.GREEN, true);
        } else {
            ScreenUtils.printMessage("Failed to add the book. Please try again.", ScreenUtils.RED, true);
        }


    }
}
