package ui.library;

import utils.ScreenUtils;
import network.ClientConnection;
import models.User;

public class BookListUI {
    public static void show() {
        ScreenUtils.clearScreen();
        ScreenUtils.printHeader("Book List", ScreenUtils.CYAN);

        String request = "LIBRARYLIST|"+ User.getInstance().getUserId(); // Command to fetch book list
        String response = ClientConnection.sendRequest(request);

        System.out.println("Books Available:");
        System.out.println(response); // Response should contain the list of books

        ScreenUtils.promptEnterKey(); // Wait for the user to press Enter
    }
}
