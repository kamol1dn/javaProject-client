package ui.library;

import utils.ScreenUtils;
import network.ClientConnection;


public class BookListUI {
    public static void show() {
        ScreenUtils.clearScreen();
        ScreenUtils.printHeader("Book List");

        String request = "LIBRARY_LIST"; // Command to fetch book list
        String response = ClientConnection.sendRequest(request);

        System.out.println("Books Available:");
        System.out.println(response); // Response should contain the list of books

        ScreenUtils.promptEnterKey(); // Wait for the user to press Enter
    }
}
