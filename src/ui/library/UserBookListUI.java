package ui.library;

import utils.InputUtils;
import utils.ScreenUtils;
import network.ClientConnection;
import models.User;

public class UserBookListUI {
    public static void show() {
        while (true) {
            ScreenUtils.clearScreen();
            ScreenUtils.printHeader("Book Management", ScreenUtils.CYAN);

            // Send request for the book list for the logged-in user
            String request = "USERBOOKS|" + User.getInstance().getUserId();
            String response = ClientConnection.sendRequest(request);

            // Parse the response
            if (response.startsWith("USERBOOKS|true|")) {
                String booksData = response.substring("USERBOOKS|true|".length());

                if (booksData.isEmpty()) {
                    // No books found for the user
                    ScreenUtils.printMessage("No books found for user: " + User.getInstance().getUserId(), ScreenUtils.YELLOW, true);
                } else {
                    // Split the books data into individual book entries
                    String[] books = booksData.split("\\|");

                    // Print the table header
                    ScreenUtils.printMessage("=================================================", ScreenUtils.CYAN, true);
                    ScreenUtils.printMessage("|   #   |           Book Title      |   Author      |", ScreenUtils.CYAN, true);
                    ScreenUtils.printMessage("-------------------------------------------------", ScreenUtils.CYAN, true);

                    // Loop through the books and display them in a table format
                    for (int i = 0; i < books.length; i++) {
                        // Each book is in the format "title,author"
                        String[] bookDetails = books[i].split(",");
                        String bookTitle = bookDetails[0];
                        String bookAuthor = bookDetails[1];
///bfusybdcjhbsd
                        // Handle long book titles
                        final int maxTitleLength = 25; // Maximum width for the book title column
                        if (bookTitle.length() > maxTitleLength) {
                            // Wrap long book titles into multiple lines
                            String[] wrappedLines = bookTitle.split("(?<=\\G.{" + maxTitleLength + "})");

                            // Print the first line with book number and author
                            String firstRow = String.format("| %-5d | %-25s | %-12s |", (i + 1), wrappedLines[0], bookAuthor);
                            ScreenUtils.printMessage(firstRow, ScreenUtils.WHITE, false);

                            // Print remaining lines for the wrapped title
                            for (int j = 1; j < wrappedLines.length; j++) {
                                String wrappedRow = String.format("|       | %-25s |              |", wrappedLines[j]);
                                ScreenUtils.printMessage(wrappedRow, ScreenUtils.WHITE, false);
                            }
                        } else {
                            // Print the book in a single line
                            String row = String.format("| %-5d | %-25s | %-12s |", (i + 1), bookTitle, bookAuthor);
                            ScreenUtils.printMessage(row, ScreenUtils.WHITE, false);
                        }

                    }

                    // Print the table footer
                    ScreenUtils.printMessage("=================================================", ScreenUtils.CYAN, true);

                    // Prompt the user to select a book or exit
                    ScreenUtils.printMessage("Enter the number of the book to return, or 0 to exit.", ScreenUtils.YELLOW, true);
                    int choice = InputUtils.getInt();

                    if (choice == 0) {

                        return;
                    } else if (choice > 0 && choice <= books.length) {

                        String[] selectedBook = books[choice - 1].split(",");
                        String bookName = selectedBook[0];

                        String returnRequest = "BOOKRETURN|" + User.getInstance().getUserId() + "|" + bookName;
                        String returnResponse = ClientConnection.sendRequest(returnRequest);

                        if (returnResponse.startsWith("BOOKRETURN|true")) {
                            ScreenUtils.showLoading("Returning book: "+bookName, 4);
                            ScreenUtils.printMessage("Book returned successfully: " + bookName, ScreenUtils.GREEN, true);
                        } else if (returnResponse == "BOOKRETURN|false"){
                            ScreenUtils.showLoading("Returning book: "+bookName, 4);
                            ScreenUtils.printMessage("Failed: " + bookName, ScreenUtils.GREEN, false);
                        } else
                        {
                            ScreenUtils.showLoading("Returning book: "+bookName, 4);
                            ScreenUtils.printMessage("Failed to return the book: " + bookName, ScreenUtils.RED, true);
                        }


                        // Wait for user input before showing the menu again
                        ScreenUtils.promptEnterKey();
                    } else {
                        ScreenUtils.printMessage("Invalid option. Please try again.", ScreenUtils.RED, true);
                    }
                }
            } else {
                // Handle failure to fetch books
                ScreenUtils.printMessage("Failed to fetch books, or user does not have books yet.\nPlease try again later.", ScreenUtils.RED, true);
                ScreenUtils.promptEnterKey();
                return;
            }
        }
    }
}
