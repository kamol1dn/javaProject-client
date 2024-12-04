package ui.library;

import utils.InputUtils;
import utils.ScreenUtils;
import network.ClientConnection;
import models.User;

public class BookBorrowFromListUI {
    public static void show() {
        ScreenUtils.clearScreen();
        ScreenUtils.printHeader("Borrow a Book", ScreenUtils.CYAN);

        // Send request to fetch the list of all books
        String request = "VIEWALLBOOKS|true";
        String response = ClientConnection.sendRequest(request);

        // Parse the response
        if (response.startsWith("VIEWALLBOOKS|true|")) {
            String booksData = response.substring("VIEWALLBOOKS|true|".length());

            if (booksData.isEmpty()) {
                ScreenUtils.printMessage("No books available at the moment.", ScreenUtils.YELLOW, true);
                ScreenUtils.promptEnterKey();
                return;
            }

            // Split the books into individual entries
            String[] books = booksData.split("\\|");

            // Print the table header
            ScreenUtils.printMessage("=================================================", ScreenUtils.CYAN, true);
            ScreenUtils.printMessage("|   #   |         Book Title        |    Author    |", ScreenUtils.CYAN, true);
            ScreenUtils.printMessage("-------------------------------------------------", ScreenUtils.CYAN, true);

            // Loop through the books and display them in a table format
            for (int i = 0; i < books.length; i++) {
                String[] bookDetails = books[i].split(",");
                String bookTitle = bookDetails[0];
                String bookAuthor = bookDetails[1];
                /// change names if it exeedes the amount that it breaks the table
                int maxTitleLength = 25;
                int maxAuthorLength = 12;
                if (bookTitle.length() > maxTitleLength) {
                    bookTitle = bookTitle.substring(0, maxTitleLength - 2) + "_.";
                }
                if (bookAuthor.length() > maxAuthorLength) {
                    bookAuthor = bookAuthor.substring(0, maxAuthorLength - 2) + "_.";
                }


                String row = String.format("| %-5d | %-25s | %-12s |", (i + 1), bookTitle, bookAuthor);
                ScreenUtils.printMessage(row, ScreenUtils.WHITE, false);

                ScreenUtils.showLoading(1.0);
            }

            // Print the table footer
            ScreenUtils.printMessage("=================================================", ScreenUtils.CYAN, true);


            ScreenUtils.printMessage("Choose a book to get");
            int choice = InputUtils.getInt();

            if (choice == 0) {

            } else if (choice > 0 && choice <= books.length) {

                String[] selectedBook = books[choice - 1].split(",");
                String bookName = selectedBook[0];

                // Send request to assign the book to the user
                String assignRequest = "ASSIGNBOOK|" + User.getInstance().getUserId() + "|" + bookName;
                String assignResponse = ClientConnection.sendRequest(assignRequest);

                ScreenUtils.showLoading("Brorrowing book...",2);

                if (assignResponse.startsWith("ASSIGNBOOK|true")) {
                    ScreenUtils.printMessage("Book borrowed successfully: " + bookName, ScreenUtils.GREEN, true);
                } else {
                    ScreenUtils.printMessage("Failed to borrow the book. It might already be assigned to you.", ScreenUtils.RED, true);
                }
            } else {
                ScreenUtils.printMessage("Invalid option. Please try again.", ScreenUtils.RED, true);
            }
        } else {

            ScreenUtils.printMessage("Failed to fetch books. Please try again later.", ScreenUtils.RED, true);
        }


    }
}
