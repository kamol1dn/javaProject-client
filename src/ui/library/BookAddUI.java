package ui.library;

import utils.InputUtils;
import utils.ScreenUtils;
import network.ClientConnection;


public class BookAddUI {
    public static void show() {
        ScreenUtils.clearScreen();
        ScreenUtils.printHeader("Add a New Book");

        String bookName = InputUtils.getNonEmptyString("Enter the name of the new book: ");
        String bookAuthor = InputUtils.getNonEmptyString("Enter the author of the book: ");

        String request = "LIBRARY_ADD " + bookName + " " + bookAuthor; // Command to add a book
        String response = ClientConnection.sendRequest(request);

        System.out.println("Server Response: " + response); // Response indicates success or failure

        ScreenUtils.promptEnterKey(); // Wait for the user to press Enter
    }
}
