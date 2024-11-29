package ui.student;

import models.User;
import utils.ScreenUtils;
import network.ClientConnection;

import java.util.Scanner;

public class TimetableUI {
    public static void show() {
        ScreenUtils.clearScreen();
        ScreenUtils.printHeader("Student Timetable", ScreenUtils.BLUE);

        // Send the request to fetch the timetable
        String request = "VIEWTIMETABLE|" + User.getInstance().getUserId();
        String response = ClientConnection.sendRequest(request);

        if (response.startsWith("TIMETABLE|true")) {
            // Parse timetable data
            String[] parts = response.split("\\|");

            // Display timetable with a "No" column
            System.out.println("No | Subject            | Time");
            System.out.println("---|-------------------|--------");

            for (int i = 2; i < parts.length; i++) {
                String[] slot = parts[i].split(";");
                String subject = String.format("%-18s", slot[0]); // Align subject names
                String time = slot[1];
                System.out.printf("%-3d| %s | %s%n", i - 1, subject, time);
            }

            // User interaction for editing
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("Enter No to edit (0 to go back): ");
                int choice;
                try {
                    choice = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number between 0-4.");
                    continue;
                }

                if (choice == 0) {
                    // Go back to the previous menu
                    return;
                } else if (choice >= 1 && choice <= 4) {
                    // Edit selected timetable slot
                    System.out.print("Enter new subject: ");
                    String newSubject = scanner.nextLine().trim();
                    System.out.print("Enter new time (hh:mm): ");
                    String newTime = scanner.nextLine().trim();

                    // Validate inputs
                    if (newSubject.isEmpty() || newTime.isEmpty()) {
                        System.out.println("Subject and time cannot be empty. Try again.");
                        continue;
                    }

                    // Send edit request
                    String editRequest = String.format("TIMETABLEEDIT|%s|slot_%d|%s;%s",
                            User.getInstance().getUserId(), choice, newSubject, newTime);
                    String editResponse = ClientConnection.sendRequest(editRequest);

                    if (editResponse.equals("TIMETABLEEDIT|true")) {
                        System.out.println("Timetable updated successfully.");
                        break; // Reload the timetable
                    } else {
                        System.out.println("Failed to update timetable. Please try again.");
                    }
                } else {
                    System.out.println("Invalid choice. Please select a number between 0-4.");
                }
            }
        } else {
            // No timetable or invalid user
            System.out.println("No timetable available or invalid user.");
        }

        // Wait for the user to press Enter
        ScreenUtils.promptEnterKey();
    }
}
